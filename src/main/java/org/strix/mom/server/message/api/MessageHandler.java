package org.strix.mom.server.message.api;

import org.strix.mom.rest.client.ResourceMessage;
import org.strix.mom.server.message.json.RestClient;

/**
 * Author: Tharindu Jayasuriya
 */
public interface MessageHandler {
    
	Message parseMessage(String string);

    String getMessage(Message message);
    
    Message getEmptyMessage();
    
    ResourceMessage getResourceMessage(int type);
    
    String sendRestMessage(ResourceMessage resourceMessage,int type); 
    
    RestClient getRestClient() ;
    
	void setRestClient(RestClient restClient);
    
}
