package org.strix.mom.server.message.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.strix.mom.rest.client.AddResourceMessage;
import org.strix.mom.rest.client.ResourceMessage;
import org.strix.mom.server.message.api.Message;
import org.strix.mom.server.message.api.MessageHandler;

/**
 * Created by IntelliJ IDEA.
 * User: SSC1
 * Date: 6/21/13
 * Time: 8:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonMessageHandler implements MessageHandler {
    private static Gson gson = new Gson();
    private RestClient restClient = null;

    /**
     * Parse messages from the client
     * @param string
     */
    @Override
    public Message parseMessage(String string) {
        JsonMessage request = null;
        try {
            request = gson.fromJson(string, JsonMessage.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return request;
    }

    /**
     * Get Json messages from the object
     * @param message
     */
    @Override
    public String getMessage(Message message) {
        String json = gson.toJson(message);
        return json;
    }

	@Override
	public Message getEmptyMessage() {
		// TODO Auto-generated method stub
		return new JsonMessage();
	}

	@Override
	public String sendRestMessage(ResourceMessage resourceMessage,int type) {
		String response = null;
		if(type==ResourceMessage.TYPE_ADDMINI_RESOURCE){
			AddResourceMessage addResourceMessage = (AddResourceMessage) resourceMessage;
			String messageString = addResourceMessage.toString();
			UrlGenerator urlGenerator  = new UrlGenerator();
			
			//http://202.69.197.115:9763/library/api/api/resources.jag?
			//action=addresources& resourcesName={resourcesName}& resourcesGrade={resourcesGrade}
			//& resourcesCategory={resourcesCategory}& resourcesActive=1& resourcesPath={resourcesPath}
			//& resourcesFilename={resourcesFilename}& resourcesAuthor={resourcesAuthor}
			//& resourcesSize={resourcesSize}& resourcesExtra={resourcesExtra}& 
			//resourcesDetails={resourcesDetails}& resourcesContent={resourcesContent}&
			urlGenerator.setParameter("action", addResourceMessage.getAction());
			urlGenerator.setParameter("resourcesName", addResourceMessage.getResourcesName());
			urlGenerator.setParameter("resourcesActive", addResourceMessage.getResourcesActive());
			urlGenerator.setParameter("resourcesFilename", addResourceMessage.getResourcesFilename());
			urlGenerator.setParameter("resourcesPath", addResourceMessage.getResourcesPath());
	        response = restClient.sendMessage(urlGenerator.getUrl());
		}
        return response;
	}

	@Override
	public ResourceMessage getResourceMessage(int type) {
		ResourceMessage resourceMessage = null;
		if(type==ResourceMessage.TYPE_ADDMINI_RESOURCE){
			AddResourceMessage addResourceMessage = new AddResourceMessage();
			addResourceMessage.setAction("addminiResource");
			return addResourceMessage;
		}
		return resourceMessage;
	}

	public RestClient getRestClient() {
		return restClient;
	}

	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}
	
	
	
	
	
	
}
