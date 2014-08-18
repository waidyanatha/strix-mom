package org.strix.mom.server.message.file;

import org.strix.mom.server.communication.impl.UdpServer;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Tharindu Jayasuriya
 */
public class FileHandler {
	private HashMap<String, FileEvent> fileEventHashMap = new HashMap<String, FileEvent>();
	private String outputLocation;
	private String tmpLocation = "../tmp";
	private String inputLocation;
	private String mode;
	private String filemode;

	public byte[] processFrame(UdpServer.Event evt) {
		DatagramPacket packet = evt.getUdpServer().getPacket();
		byte[] data = evt.getPacketAsBytes();
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		System.out.println("filemode "+filemode);
		if (filemode.equals("dev")) {
			
			ObjectInputStream is = null;
			try {
				is = new ObjectInputStream(in);

				FileEvent fileEvent = (FileEvent) is.readObject();
				if (fileEvent.getStatus().equalsIgnoreCase("Error")) {
					System.out
							.println("Some issue happened while packing the data @ client side");
					System.exit(0);
				}
				createAndWriteFile(fileEvent); // writing the file to hard disk
				if (fileEvent.isLast()) {
					InetAddress IPAddress = packet.getAddress();
					int port = packet.getPort();
					String reply = "Thank you for the message";
					byte[] replyBytea = reply.getBytes();
					DatagramPacket replyPacket = new DatagramPacket(replyBytea,
							replyBytea.length, IPAddress, port);
					evt.getUdpServer().send(replyPacket);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			/*DataInputStream dis = null;
			try {
				dis = new DataInputStream(in);
				int length = dis.available();
				// create buffer
				byte[] buf = new byte[length];
				// read the full data into the buffer
				dis.readFully(buf);
			} catch (Exception e) {
				// if any error occurs
				e.printStackTrace();
			} finally {
				// releases all system resources from the streams
				if (dis != null)
					try {
						dis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}*/
		}
		return data;
	}
	
	private void createAndWriteFile(FileEvent fileEvent) {
		String outputFile = outputLocation + File.separator
				+ fileEvent.getFilename();
		String tmpOutputFile = tmpLocation + File.separator
				+ fileEvent.getFilename();
		System.out.println("outputFile" + outputFile +" tmpOutputFile"+tmpOutputFile);
		
		
		if (fileEvent.getStart() == 0) {
			fileEventHashMap.put(outputFile, fileEvent);
			if (!new File(tmpLocation).exists()) {
				new File(tmpLocation).mkdirs();
			}
			if (!new File(outputLocation).exists()) {
				new File(outputLocation).mkdirs();
			}
		}
		try {
			/*
			 * if (!dstFile.exists()) { dstFile.createNewFile(); }
			 */
			FileEvent cachedFileEvent = fileEventHashMap.get(outputFile);
			cachedFileEvent
					.setMessageCount(cachedFileEvent.getMessageCount() + 1);
			System.out.println("+++++++++++" + fileEvent.getStart()
					+ ":::::::::::::::" + fileEvent.getEnd()
					+ "cachedFileEvent.getMessageCount():::::::"
					+ cachedFileEvent.getMessageCount());
			synchronized (FileHandlerUtils.class) {
				FileHandlerUtils.appendToFile(tmpOutputFile,fileEvent.getFileData(),true);
			}
			if (fileEvent.isLast()) {
				fileEventHashMap.remove(outputFile);
				File dstFile = new File(outputFile);
				File srcFile = new File(tmpOutputFile);
				copyFile(srcFile,dstFile);
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void copyFile(File sourceFile, File destFile) throws IOException {
		if (destFile.exists()) {
			destFile.delete();
		}
		if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}

	private void createAndWriteFile1(FileEvent fileEvent) {
		String outputFile = outputLocation + File.separator
				+ fileEvent.getFilename();
		System.out.println("outputFile" + outputFile);
		if (!new File(outputLocation).exists()) {
			new File(outputLocation).mkdirs();
		}
		File dstFile = new File(outputFile);
		if (fileEvent.getStart() == 0) {
			byte[] totalFileData = new byte[(int) fileEvent.getFileSize()];
			if (dstFile.exists()) {
				dstFile.delete();
			}
			fileEvent.setData(totalFileData);
			fileEventHashMap.put(outputFile, fileEvent);
		}
		try {
			/*
			 * if (!dstFile.exists()) { dstFile.createNewFile(); }
			 */
			FileEvent cachedFileEvent = fileEventHashMap.get(outputFile);
			cachedFileEvent
					.setMessageCount(cachedFileEvent.getMessageCount() + 1);
			System.out.println("+++++++++++" + fileEvent.getStart()
					+ ":::::::::::::::" + fileEvent.getEnd()
					+ "cachedFileEvent.getMessageCount():::::::"
					+ cachedFileEvent.getMessageCount());
			byte[] totalFileDate = cachedFileEvent.getData();
			/*
			 * for (int i = (int) fileEvent.getStart(); i < fileEvent.getEnd();
			 * i++) { // System.out.println("i: "+i
			 * +"   i-(int) fileEvent.getStart():   "+(i-(int)
			 * fileEvent.getStart()));
			 * //System.out.print((char)fileEvent.getFileData()[i-(int)
			 * fileEvent.getStart()]); totalFileDate[i] =
			 * fileEvent.getFileData()[i-(int) fileEvent.getStart()]; }
			 */
			System.arraycopy(fileEvent.getFileData(), 0, totalFileDate,
					(int) fileEvent.getStart(),
					(int) (fileEvent.getEnd() - fileEvent.getStart()));
			if (fileEvent.isLast()) {
				FileOutputStream fileOuputStream = new FileOutputStream(dstFile);
				fileOuputStream.write(totalFileDate);
				fileOuputStream.close();
				fileEventHashMap.remove(outputFile);
			}
			//System.out.println("dstFile" +dstFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getOutputLocation() {
		return outputLocation;
	}

	public void setOutputLocation(String outputLocation) {
		this.outputLocation = outputLocation;
	}

	public String getInputLocation() {
		return inputLocation;
	}

	public void setInputLocation(String inputLocation) {
		this.inputLocation = inputLocation;
	}

	public String getDirectoryListing() {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbTable = new StringBuilder();
		List<String> files = doDir(inputLocation);

		if (mode.equals("dev")) {
			for (String file : files) {
				/*
				 * if (file.endsWith((".mp4"))) { String videoHtml =
				 * "<video width=\"320\" height=\"240\" controls>\n" +
				 * "  <source src=\"file:///" + outputLocation + "/" + file +
				 * "\" type=\"video/mp4\">\n" +
				 * "Your browser does not support the video tag.\n" +
				 * "</video>"; sb.append("  <tr>\n" + "    <td>" + videoHtml +
				 * "</td>\n" + "  </tr>\n");
				 * 
				 * } else if (file.endsWith((".mp3"))) { String audioHtml =
				 * "<audio width=\"320\" height=\"240\" controls>\n" +
				 * "  <source src=\"file:///" + outputLocation + "/" + file +
				 * "\" type=\"audio/mp3\">\n" +
				 * "Your browser does not support the video tag.\n" +
				 * "</audio>"; sb.append("  <tr>\n" + "    <td>" + audioHtml +
				 * "</td>\n" + "  </tr>\n");
				 * 
				 * }
				 *//*
					 * else { String linkHtml = "<a href=\"file:///" +
					 * outputLocation + "/" + file + "\">" + file + "</a>";
					 * sb.append("  <tr>\n" + "    <td>" + linkHtml + "</td>\n"
					 * + "  </tr>\n"); }
					 */// else {
				String linkHtml = "<a href=\"" + "/demo/out" + "/" + file
						+ "\">" + file + "</a>";
				sb.append("  <tr>\n" + "    <td>" + linkHtml + "</td>\n"
						+ "  </tr>\n");
				// }
			}
		} else {
			for (String file : files) {
				sb.append(file + "\n");
			}
		}

		sbTable.append("<table border=\"0\">\n" + sb.toString() + "</table>");
		return sbTable.toString();
	}

	private List<String> doDir(String directory) {
		List<String> list = new ArrayList<String>();
		File dir = new File(directory);
		for (File file : dir.listFiles()) {
			// if (file.getName().endsWith((".txt"))) {
			list.add(file.getName());
			// }
		}
		return list;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getFilemode() {
		return filemode;
	}

	public void setFilemode(String filemode) {
		this.filemode = filemode;
	}
	
	

}
