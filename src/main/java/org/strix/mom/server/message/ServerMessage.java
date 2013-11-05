package org.strix.mom.server.message;

/**
 * Author: Tharindu Jayasuriya
 */
public class ServerMessage {
    private String requestData;
    private boolean sentReply;
    private String channel;
    private String responseData;
    private boolean sendToSenderOnly;

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public boolean isSentReply() {
        return sentReply;
    }

    public void setSentReply(boolean sentReply) {
        this.sentReply = sentReply;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

	public boolean isSendToSenderOnly() {
		return sendToSenderOnly;
	}

	public void setSendToSenderOnly(boolean sendToSenderOnly) {
		this.sendToSenderOnly = sendToSenderOnly;
	}
    
}
