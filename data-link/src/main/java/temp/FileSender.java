package temp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.strix.mom.server.message.file.FileEvent;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class FileSender {
    private DatagramSocket socket = null;
    private FileEvent event = null;
    private String sourceFilePath = null;//"G:\\Strix\\MyjWebSocketJavaClient\\WebSocketServer\\testData\\in\\1.avi";
    private String destinationPath = null;//"C:/Downloads/udp/";
    private String hostName = null;//"localHost";
    private int port;

    public FileSender() {

    }

    public void createConnection(String fileName) {
        try {
            socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(hostName);
            byte[] incomingData = new byte[1024*64];
            event = getFileEvent(fileName);
            byte[] fileData = event.getFileData();
            System.out.println("FileSender.createConnection"+fileData.length);
            event.setFileData(null);
            int noPacketsSend = 0;
            for (int i = 0; i < event.getFileSize(); ) {
                byte[] buffer = new byte[1024*60];
                event.setStart(i);
                event.setBufferSize(buffer.length);
                if(i+buffer.length>event.getFileSize()){
                    System.arraycopy(fileData,i,buffer,0,fileData.length-i);
                    event.setLast(true);
                    event.setEnd(fileData.length);
                }else{
                    System.arraycopy(fileData,i,buffer,0,buffer.length);
                    event.setEnd(i+buffer.length);
                    if(i+buffer.length==event.getFileSize()){
                        event.setLast(true);
                    }
                }
                i=i+buffer.length;
                event.setFileData(buffer);
               
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(outputStream);
                os.writeObject(event);
                byte[] data = outputStream.toByteArray();
                //byte[] data = event.getFileData();
                System.out.println("$$$$$$$$$$$$$$$$$"+event.getStart()+"from to"+event.getEnd());
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
                socket.send(sendPacket);
                noPacketsSend++;
                Thread.sleep(1000);
            }
            System.out.println("File sent from client with "+noPacketsSend);
            Thread.sleep(80000);
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            socket.receive(incomingPacket);
            String response = new String(incomingPacket.getData());
            System.out.println("Response from server:" + response);

            //System.exit(0);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public FileEvent getFileEvent(String sourceFileName) {
        FileEvent fileEvent = new FileEvent();
        String fileName = sourceFileName.substring(sourceFileName.lastIndexOf("/") + 1, sourceFileName.length());
        String path = sourceFileName.substring(0, sourceFileName.lastIndexOf("/") + 1);
        fileEvent.setDestinationDirectory(destinationPath);
        fileEvent.setFilename(fileName);
        fileEvent.setSourceDirectory(sourceFileName);
        File file = new File(sourceFileName);
        System.out.println("sourceFileName"+sourceFileName);
        System.out.println("destinationPath"+destinationPath);
        if (file.isFile()) {
            try {
                fileEvent.setFilename(file.getName());
                DataInputStream diStream = new DataInputStream(new FileInputStream(file));
                long len = (int) file.length();
                byte[] fileBytes = new byte[(int) len];
                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
                        fileBytes.length - read)) >= 0) {
                    read = read + numRead;
                }
                fileEvent.setFileSize(len);
                fileEvent.setFileData(fileBytes);
                fileEvent.setStatus("Success");
            } catch (Exception e) {
                e.printStackTrace();
                fileEvent.setStatus("Error");
            }
        } else {
            System.out.println("path specified is not pointing to a file");
            fileEvent.setStatus("Error");
        }
        return fileEvent;
    }
    
    public static ArrayList<String> listFilesForFolder(final File folder) {
    	ArrayList<String> filePaths = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
            	filePaths.add(fileEntry.getAbsolutePath());
                System.out.println("fileEntry"+fileEntry.getAbsolutePath());
            }
        }
        return filePaths;
    }

    


    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring-processor.xml"});
        FileSender fileSender = (FileSender) context.getBean("fileSender");
        System.out.println("fileSender.getSourceFilePath()"+fileSender.getSourceFilePath());
        final File folder = new File(fileSender.getSourceFilePath());
        ArrayList<String> filePaths = listFilesForFolder(folder);
        for(String filePath:filePaths){
        	fileSender.createConnection(filePath);
        }
        
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

	public String getDestinationPath() {
		return destinationPath;
	}

	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
    
    
}
