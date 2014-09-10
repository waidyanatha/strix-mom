package org.strix.mom.server.message.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class FileHandlerUtils {
	public static String STREAM_BUFFER = "../ts/streambuffer.ts";
	public static String BASE64_ENCODED = "../ts/base64encoded.ts";
	public static String UDP_LISTENER = "../ts/udpListener.ts";
	public static String WEB_SOCKET = "../ts/websocket.ts";
	
	
	public static void appendToFile(final String fileName,final byte[] data,final boolean writeToFiles){
		System.out.println(fileName+":"+writeToFiles+"data"+data.length);
		if(writeToFiles){
			FileOutputStream output=null;
        	try {
        	    output = new FileOutputStream(fileName, writeToFiles);
				output.write(data);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
			   try {
				output.close();
			   } catch (IOException e) {
				e.printStackTrace();
			   }
			}
        	
			/*try {
			    final int numberOfThread = 20;
			    ExecutorService pool = Executors.newFixedThreadPool(numberOfThread);
			     byte[] yourText = data;
			        pool.submit(new Runnable() {
			            @Override
			            public void run() {
			            	FileOutputStream output=null;
			            	try {
			            	    output = new FileOutputStream(fileName, writeToFiles);
			    				output.write(data);
			    			} catch (FileNotFoundException e) {
			    				e.printStackTrace();
			    			} catch (IOException e) {
			    				e.printStackTrace();
			    			}finally {
			    			   try {
			    				output.close();
			    			   } catch (IOException e) {
			    				e.printStackTrace();
			    			   }
			    			}
			            }
			        });
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
	}
	
	public static void writeToFile(String fileName,byte[] data,boolean writeToFiles){
		System.out.println(fileName+":"+writeToFiles+"data"+data.length);
		if(writeToFiles){
			
			FileOutputStream output=null;
			try {
				output = new FileOutputStream(fileName, false);
				output.write(data);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
			   try {
				output.close();
			   } catch (IOException e) {
				e.printStackTrace();
			   }
			}
		}
	}
	
	public static void deleteFiles()
    {	
		if (!new File("../ts/README.txt").exists()) {
			new File("../ts/").mkdirs();
		}
		deleteFile(STREAM_BUFFER);
		deleteFile(BASE64_ENCODED);
		deleteFile(UDP_LISTENER);
		deleteFile(WEB_SOCKET);
 
    }
	
	public static void deleteFile(String fileName)
    {	
    	try{
    		File file = new File(fileName);
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
 
    	}
 
    }

}
