package org.strix.mom.server.message.file;

public interface FileListener {
	
	public void fileRecevied(String type,String filename,String messageData);

}
