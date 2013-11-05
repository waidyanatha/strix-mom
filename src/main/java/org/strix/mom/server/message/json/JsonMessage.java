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

    @Override
    public String getNs() {
        return ns;
    }

    @Override
    public void setNs(String ns) {
        this.ns = ns;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getClientType() {
        return clientType;
    }

    @Override
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String getClientVersion() {
        return clientVersion;
    }

    @Override
    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    @Override
    public String getClientInfo() {
        return clientInfo;
    }

    @Override
    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    @Override
    public String getJwsType() {
        return jwsType;
    }

    @Override
    public void setJwsType(String jwsType) {
        this.jwsType = jwsType;
    }

    @Override
    public String getJwsVersion() {
        return jwsVersion;
    }

    @Override
    public void setJwsVersion(String jwsVersion) {
        this.jwsVersion = jwsVersion;
    }

    @Override
    public String getJwsInfo() {
        return jwsInfo;
    }

    @Override
    public void setJwsInfo(String jwsInfo) {
        this.jwsInfo = jwsInfo;
    }

    /*@Override
    public String getEncodingFormats() {
        return encodingFormats;
    }

    @Override
    public void setEncodingFormats(String encodingFormats) {
        this.encodingFormats = encodingFormats;
    }*/

    @Override
    public String getUtid() {
        return utid;
    }

    @Override
    public void setUtid(String utid) {
        this.utid = utid;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEncoding() {
        return encoding;
    }

    @Override
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String getPool() {
        return pool;
    }

    @Override
    public void setPool(String pool) {
        this.pool = pool;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String getAccessKey() {
        return accessKey;
    }

    @Override
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    @Override
    public String getSecretKey() {
        return secretKey;
    }

    @Override
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getAction() {
        return action;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }
    
    @Override
	public byte[] getDataStream() {
		return dataStream;
	}

    @Override
    public void setDataStream(byte[] dataStream) {
		this.dataStream = dataStream;
	}
    
    @Override
	public String getResourcesName() {
		return resourcesName;
	}

    @Override
	public void setResourcesName(String resourcesName) {
		this.resourcesName = resourcesName;
	}
    
    @Override
	public String getResourcesGrade() {
		return resourcesGrade;
	}
    
    @Override
	public void setResourcesGrade(String resourcesGrade) {
		this.resourcesGrade = resourcesGrade;
	}
    
    @Override
	public String getResourcesCategory() {
		return resourcesCategory;
	}

    @Override
    public void setResourcesCategory(String resourcesCategory) {
		this.resourcesCategory = resourcesCategory;
	}

    @Override
    public String getResourcesActive() {
		return resourcesActive;
	}

    @Override
    public void setResourcesActive(String resourcesActive) {
		this.resourcesActive = resourcesActive;
	}

    @Override
    public String getResourcesPath() {
		return resourcesPath;
	}

    @Override
    public void setResourcesPath(String resourcesPath) {
		this.resourcesPath = resourcesPath;
	}

    @Override
    public String getResourcesFilename() {
		return resourcesFilename;
	}

    @Override
    public void setResourcesFilename(String resourcesFilename) {
		this.resourcesFilename = resourcesFilename;
	}

    @Override
    public String getResourcesAuthor() {
		return resourcesAuthor;
	}

    @Override
    public void setResourcesAuthor(String resourcesAuthor) {
		this.resourcesAuthor = resourcesAuthor;
	}

    @Override
    public String getResourcesSize() {
		return resourcesSize;
	}

    @Override
    public void setResourcesSize(String resourcesSize) {
		this.resourcesSize = resourcesSize;
	}

    @Override
    public String getResourcesExtra() {
		return resourcesExtra;
	}

    @Override
    public void setResourcesExtra(String resourcesExtra) {
		this.resourcesExtra = resourcesExtra;
	}

    @Override
    public String getResourcesDetails() {
		return resourcesDetails;
	}

    @Override
    public void setResourcesDetails(String resourcesDetails) {
		this.resourcesDetails = resourcesDetails;
	}

    @Override
    public String getResourcesContent() {
		return resourcesContent;
	}

    @Override
    public void setResourcesContent(String resourcesContent) {
		this.resourcesContent = resourcesContent;
	}
    
    
    
    
}
