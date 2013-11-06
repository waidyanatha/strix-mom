package org.strix.mom.server.message.json;

import org.strix.mom.server.message.api.Message;

/**
 * Author: Tharindu Jayasuriya
 */
public class JsonMessage implements Message {

    private String ns = null;//"ns":"org.jwebsocket.plugins.system",
    private String type = null;//"type":"header",
    private String clientType = null;//"clientType":"browser",
    private String clientName = null;//"clientName":"Firefox",
    private String clientVersion = null;//"clientVersion":"12.0",
    private String clientInfo = null;//"clientInfo":"Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20100101 Firefox/12.0",
    private String jwsType = null;//"jwsType":"javascript",
    private String jwsVersion = null;//"jwsVersion":"1.0 RC1 (build 30518)",
    private String jwsInfo = null;//"jwsInfo":"native",
    //private String encodingFormats = null;//"encodingFormats":["base64","zipBase64"],
    private String utid = null;//"utid":1
    private String username = null;//"username":"root"
    private String password = null;//"password":"root"
    private String encoding = null;//"encoding":null
    private String channel = null;//"channel":"jWebSocketSlideShowDemo"
    private String accessKey = null;//"accessKey":"5l1d35h0w"
    private String secretKey = null;//"secretKey":"5l1d35h0w53cr3t!"
    private String pool = null;//"pool":null
    private String data = null;//"data":"test"
    private String action = null;//"action":"test"
    private byte[] dataStream = null;
    
    private String resourcesName=null; 
    private String resourcesGrade=null;
    private String resourcesCategory=null; 
    private String resourcesActive=null;
    private String resourcesPath=null;
    private String resourcesFilename =null;
    private String resourcesAuthor=null; 
    private String resourcesSize=null; 
    private String resourcesExtra=null;
    private String resourcesDetails=null;
    private String resourcesContent=null;

    
    public String getNs() {
        return ns;
    }

    
    public void setNs(String ns) {
        this.ns = ns;
    }

    
    public String getType() {
        return type;
    }

    
    public void setType(String type) {
        this.type = type;
    }

    
    public String getClientType() {
        return clientType;
    }

    
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    
    public String getClientName() {
        return clientName;
    }

    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    
    public String getClientVersion() {
        return clientVersion;
    }

    
    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    
    public String getClientInfo() {
        return clientInfo;
    }

    
    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    
    public String getJwsType() {
        return jwsType;
    }

    
    public void setJwsType(String jwsType) {
        this.jwsType = jwsType;
    }

    
    public String getJwsVersion() {
        return jwsVersion;
    }

    
    public void setJwsVersion(String jwsVersion) {
        this.jwsVersion = jwsVersion;
    }

    
    public String getJwsInfo() {
        return jwsInfo;
    }

    
    public void setJwsInfo(String jwsInfo) {
        this.jwsInfo = jwsInfo;
    }

    /*
    public String getEncodingFormats() {
        return encodingFormats;
    }

    
    public void setEncodingFormats(String encodingFormats) {
        this.encodingFormats = encodingFormats;
    }*/

    
    public String getUtid() {
        return utid;
    }

    
    public void setUtid(String utid) {
        this.utid = utid;
    }

    
    public String getUsername() {
        return username;
    }

    
    public void setUsername(String username) {
        this.username = username;
    }

    
    public String getPassword() {
        return password;
    }

    
    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getEncoding() {
        return encoding;
    }

    
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    
    public String getPool() {
        return pool;
    }

    
    public void setPool(String pool) {
        this.pool = pool;
    }

    
    public String getChannel() {
        return channel;
    }

    
    public void setChannel(String channel) {
        this.channel = channel;
    }

    
    public String getAccessKey() {
        return accessKey;
    }

    
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    
    public String getSecretKey() {
        return secretKey;
    }

    
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    
    public String getData() {
        return data;
    }

    
    public void setData(String data) {
        this.data = data;
    }

    
    public String getAction() {
        return action;  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public void setAction(String action) {
        this.action = action;
    }
    
    
	public byte[] getDataStream() {
		return dataStream;
	}

    
    public void setDataStream(byte[] dataStream) {
		this.dataStream = dataStream;
	}
    
    
	public String getResourcesName() {
		return resourcesName;
	}

    
	public void setResourcesName(String resourcesName) {
		this.resourcesName = resourcesName;
	}
    
    
	public String getResourcesGrade() {
		return resourcesGrade;
	}
    
    
	public void setResourcesGrade(String resourcesGrade) {
		this.resourcesGrade = resourcesGrade;
	}
    
    
	public String getResourcesCategory() {
		return resourcesCategory;
	}

    
    public void setResourcesCategory(String resourcesCategory) {
		this.resourcesCategory = resourcesCategory;
	}

    
    public String getResourcesActive() {
		return resourcesActive;
	}

    
    public void setResourcesActive(String resourcesActive) {
		this.resourcesActive = resourcesActive;
	}

    
    public String getResourcesPath() {
		return resourcesPath;
	}

    
    public void setResourcesPath(String resourcesPath) {
		this.resourcesPath = resourcesPath;
	}

    
    public String getResourcesFilename() {
		return resourcesFilename;
	}

    
    public void setResourcesFilename(String resourcesFilename) {
		this.resourcesFilename = resourcesFilename;
	}

    
    public String getResourcesAuthor() {
		return resourcesAuthor;
	}

    
    public void setResourcesAuthor(String resourcesAuthor) {
		this.resourcesAuthor = resourcesAuthor;
	}

    
    public String getResourcesSize() {
		return resourcesSize;
	}

    
    public void setResourcesSize(String resourcesSize) {
		this.resourcesSize = resourcesSize;
	}

    
    public String getResourcesExtra() {
		return resourcesExtra;
	}

    
    public void setResourcesExtra(String resourcesExtra) {
		this.resourcesExtra = resourcesExtra;
	}

    
    public String getResourcesDetails() {
		return resourcesDetails;
	}

    
    public void setResourcesDetails(String resourcesDetails) {
		this.resourcesDetails = resourcesDetails;
	}

    
    public String getResourcesContent() {
		return resourcesContent;
	}

    
    public void setResourcesContent(String resourcesContent) {
		this.resourcesContent = resourcesContent;
	}
    
    
    
    
}
