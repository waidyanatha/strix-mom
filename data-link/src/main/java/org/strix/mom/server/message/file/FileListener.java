package org.strix.mom.server.message.file;

/**
 * Author: Tharindu Jayasuriya
 */
public interface FileListener {
	
	public void fileRecevied(String type,String filename,String messageData);

}
