package temp;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.strix.mom.server.message.file.FileEvent;
import org.strix.mom.server.message.file.FileHandler;
import org.strix.mom.server.message.file.FileHandlerUtils;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
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
            System.out.println("FileSender.createConnection"+event.getFileSize());
            event.setFileData(null);
            int noPacketsSend = 0;
            for (int i = 0; i < event.getFileSize(); ) {
                byte[] buffer = new byte[60000];//new byte[1024*60];
                event.setStart(i);
                event.setBufferSize(buffer.length);
                if(i+buffer.length>event.getFileSize()){
                	int length = (int)(event.getFileSize()-i);
                	FileEvent chunkFileData = getFileChunk(fileName,i,length);
                	System.arraycopy(chunkFileData.getData(),0,buffer,0,length);
                    //System.arraycopy(fileData,i,buffer,0,fileData.length-i);
                    event.setLast(true);
                    event.setEnd(event.getFileSize());
                }else{
                	int length = buffer.length;
                	FileEvent chunkFileData = getFileChunk(fileName,i,length);
                	System.arraycopy(chunkFileData.getData(),0,buffer,0,length);
                    //System.arraycopy(fileData,i,buffer,0,buffer.length);
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
                noPacketsSend++;
                System.out.println("$$$$$$$$$$$$$$$$$"+event.getStart()+"from to"+event.getEnd()+" noPacketsSend++"+noPacketsSend);
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
                socket.send(sendPacket);
                
                Thread.sleep(4000);
            }
            System.out.println("File sent from client with "+noPacketsSend);
            Thread.sleep(1000);
            //DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            //socket.receive(incomingPacket);
            //String response = new String(incomingPacket.getData());
            //System.out.println("Response from server:" + response);

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
                long len = (int) file.length();
                fileEvent.setFileSize(len);
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
    
    public FileEvent getFileChunk(String sourceFileName,int srcPosition,int length) {
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
                int len = (int) file.length();
                System.out.println("sourceFileName len"+len);
                fileEvent.setFileSize(len);
                fileEvent.setStatus("Success");
                
                
                RandomAccessFile raf = new RandomAccessFile(sourceFileName, "r");
                raf.seek(srcPosition);
                // Read in the next byte array.
                byte[] fileBytes = new byte[length];
                raf.read(fileBytes, 0, length);
                raf.close();
                
                //String tmpOutputFile = "../tmp1" + File.separator
        		//		+ fileEvent.getFilename();
                //FileHandlerUtils.appendToFile(tmpOutputFile,fileBytes,true);
                
                /*// get an input stream for this file.
                FileInputStream in = new FileInputStream(file);
                // get the fileChannel for this input stream
                FileChannel fileChannel = in.getChannel();
                fileChannel.position(srcPosition);
                // get the position
                System.out.println(fileChannel.position());//print 0
                 
                // create a char buffer
                ByteBuffer buffer = ByteBuffer.allocate(length);
                // read the fist line - 10 characters into the byte buffer
                fileChannel.read(buffer);
                // get the position of the file
                //System.out.println(fileChannel.position());// prints 11
                // we have read the first 10 chars into the buffer now. find out the
                buffer.flip();
                //System.out.print(Charset.defaultCharset().decode(buffer)); // prints "Second Line"
                fileChannel.close();
                in.close(); */                
                fileEvent.setData(fileBytes);
                
                
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
    	ArrayList<String> matafilePaths = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
            	if(fileEntry.getName().endsWith(".meta")){
            		matafilePaths.add(fileEntry.getAbsolutePath());
            	}else{
            		filePaths.add(fileEntry.getAbsolutePath());
            	}
                System.out.println("fileEntry"+fileEntry.getAbsolutePath());
            }
        }
        filePaths.addAll(matafilePaths);
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
