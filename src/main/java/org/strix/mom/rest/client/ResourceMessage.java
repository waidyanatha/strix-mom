package org.strix.mom.rest.client;

/**
 * Author: Tharindu Jayasuriya
 */
public class ResourceMessage {
	
	public static final int TYPE_ADD_RESOURCE=1;
	public static final int TYPE_ADDMINI_RESOURCE=2;
	
	private String action=null;
	private String resourcesId=null; 
    
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getResourcesId() {
		return resourcesId;
	}
	public void setResourcesId(String resourcesId) {
		this.resourcesId = resourcesId;
	}

}
