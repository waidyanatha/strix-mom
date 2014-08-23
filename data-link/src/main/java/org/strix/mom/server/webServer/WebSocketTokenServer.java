package org.strix.mom.server.webServer;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.apache.log4j.Logger;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.config.JWebSocketConfig;
import org.jwebsocket.config.JWebSocketServerConstants;
import org.jwebsocket.factory.JWebSocketFactory;
import org.jwebsocket.kit.RawPacket;
import org.jwebsocket.kit.WebSocketServerEvent;
import org.jwebsocket.listener.WebSocketServerTokenEvent;
import org.jwebsocket.listener.WebSocketServerTokenListener;
import org.jwebsocket.server.TokenServer;
import org.jwebsocket.token.Token;
import org.strix.mom.rest.client.AddResourceMessage;
import org.strix.mom.rest.client.ResourceMessage;
import org.strix.mom.server.client.ApplicationClient;
import org.strix.mom.server.client.ApplicationClient.EngineType;
import org.strix.mom.server.message.MessageProcessor;
import org.strix.mom.server.message.ServerMessage;
import org.strix.mom.server.message.api.Message;
import org.strix.mom.server.message.file.FileHandler;
import org.strix.mom.server.message.file.FileHandlerUtils;
import org.strix.mom.server.message.file.FileListener;
import org.strix.mom.server.timer.FileReadTimer;
import org.strix.mom.server.communication.impl.UdpServer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.DatagramPacket;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.security.crypto.codec.Base64;


/**
 * Author: Tharindu Jayasuriya
 */
public class WebSocketTokenServer implements WebSocketServerTokenListener, UdpServer.Listener, PropertyChangeListener,FileListener {
	Logger log;
	private String resourcePath;
    private TokenServer tokenServer;
    private FileHandler fileHandler;
    private FileReadTimer fileReadTimer;
    private ApplicationClientManager applicationClientManager;
    private MessageProcessor messageProcessor;

    public TokenServer getTokenServer() {
    	
        return tokenServer;
    }

    public void init() {
        try {
//            System.setProperty(JWebSocketServerConstants.JWEBSOCKET_HOME,
//                    System.getenv(JWebSocketServerConstants.JWEBSOCKET_HOME));

            System.setProperty(JWebSocketServerConstants.JWEBSOCKET_HOME,
                    resourcePath);
            System.out.println("Reosurce Path"+resourcePath);
            
            log = org.apache.log4j.Logger.getLogger(WebSocketTokenServer.class);

            //JWebSocketFactory.printCopyrightToConsole();
            // the following line must not be removed due to GNU LGPL 3.0 license!
// check if home, config or bootstrap path are passed by command line
            JWebSocketConfig.initForConsoleApp(null);
//start the jWebSocket Server
            JWebSocketFactory.start();
            tokenServer = (TokenServer) JWebSocketFactory.getServer("ts0");
            if (tokenServer != null) {
                System.out.println("Web Socket server was created");
                tokenServer.addListener(this);
            } else {
                System.out.println("server was NOT found");
            }
            fileReadTimer.init(this);
        } catch (Exception lEx) {
            lEx.printStackTrace();
        }
    }

    public void processToken(WebSocketServerTokenEvent aEvent, Token aToken) {
    	log.debug("WebSocketTokenServer.processToken"+aToken);
    }

    public void processClosed(WebSocketServerEvent event) {
    	log.debug("SockServer.processClosed" + event);
        ApplicationClient applicationClient = new ApplicationClient();
        applicationClient.setId(event.getConnector().getId());
        applicationClientManager.removeApplicationClient(applicationClient.getId());
    }

    public void processOpened(WebSocketServerEvent event) {
    	log.debug("***********org.strix.mom.server.client.ApplicationClient '" + event.getSessionId()
                + "' connected.*********");
        String engineId = event.getConnector().getEngine().getId();
        ApplicationClient applicationClient = new ApplicationClient();
        applicationClient.setUid(event.getConnector().generateUID());
        applicationClient.setId(event.getConnector().getId());
//        applicationClient.setStatus(event.getConnector().getStatus().getStatus());
        applicationClient.setAlive(event.getConnector().getEngine().isAlive());
        applicationClient.setUserName(event.getConnector().getUsername());
        applicationClient.setRemoteHostName(event.getConnector().getRemoteHost().toString());
        applicationClient.setRemoteHostPort(event.getConnector().getRemotePort());
        applicationClient.setWebSocketConnector(event.getConnector());
        applicationClient.setEngineId(engineId);
        applicationClient.setEngineType(EngineType.valueOf(engineId));
        applicationClientManager.addApplicationClient(applicationClient);
    }

    public void processPacket(WebSocketServerEvent event, WebSocketPacket packet) {
        System.out.println("packet received " + packet.getString());
        ApplicationClient client = applicationClientManager.getApplicationClient(event.getConnector().getId());
        client.setLastMessageReceived(new Date(System.currentTimeMillis()));
        ServerMessage replyMessage = messageProcessor.processMessage(packet.getString());
        if (replyMessage.isSendToSenderOnly() && replyMessage.isSentReply()) {
            WebSocketPacket wsPacket = new RawPacket(replyMessage.getResponseData());
            log.debug("WebSocketTokenServer.isSendToSenderOnly" + replyMessage.getResponseData());
            log.debug("SENDING PACKET ONLY TO LOGIN CLIENT CONNECTED"+replyMessage.getResponseData());
            if(event.getServer().getId().equals(EngineType.ALL)||event.getServer().getId().equals(EngineType.BROADCAST)){
            	event.sendPacket(wsPacket);
            }
        }else if (replyMessage.isSentReply()) {
            WebSocketPacket wsPacket = new RawPacket(replyMessage.getResponseData());
            Map lConnectorMap = getTokenServer().getAllConnectors();
            Collection<WebSocketConnector> lConnectors = lConnectorMap.values();
            for (WebSocketConnector wsc : lConnectors) {
            	if(wsc.getEngine().getId().equals(EngineType.ALL.toString())||wsc.getEngine().getId().equals(EngineType.BROADCAST.toString())){
            		getTokenServer().sendPacket(wsc, wsPacket); 
                    log.debug("SENDING PACKET TO ALL CLIENTS CONNECTED"+replyMessage.getResponseData());
                }
                
            }
        }
    }
    
    public void sendPacket(String messageData) {
        Map lConnectorMap = getTokenServer().getAllConnectors();
        Collection<WebSocketConnector> lConnectors = lConnectorMap.values();
        for (WebSocketConnector wsc : lConnectors) {
            WebSocketPacket wsPacket = new RawPacket(messageData);
            getTokenServer().sendPacket(wsc, wsPacket); 
            log.debug("SENDING INFO TO CLIENT RECEIVED"+messageData);
        }
    }
    
    public void sendPacket(byte[] messageData,boolean writeToFiles) {
    	
        Map lConnectorMap = getTokenServer().getAllConnectors();
        Collection<WebSocketConnector> lConnectors = lConnectorMap.values();
        for (WebSocketConnector wsc : lConnectors) {
            WebSocketPacket wsPacket = new RawPacket(messageData);
            getTokenServer().sendPacket(wsc, wsPacket); 
            log.debug("SENDING INFO TO CLIENT RECEIVED"+messageData);
        }
        FileHandlerUtils.appendToFile(FileHandlerUtils.WEB_SOCKET, messageData,writeToFiles);
    }


    @Override
    public void packetReceived(UdpServer.Event evt) {
        DatagramPacket packet = evt.getUdpServer().getPacket(); // Not actually using this here.
        final String message = evt.getPacketAsString();
        log.info(evt.getUdpServer().getType() + " UdpServer.Event ");

        switch (evt.getUdpServer().getType()){
            case FILE:
                fileHandler.processFrame(evt);
                break;
            case STREAM:
            	streamRecevied("streamReceived","stream",evt.getPacketAsBytes(),evt.getUdpServer().isWriteToFiles());
                break;
            case TEXT:
                break;
            case COMMANDS:
                break;
            default:
                break;
        }
    }
    
    @Override
	public void fileRecevied(String type,String filename,String messageData) {
    	log.info("FILE RECEIVED"+filename);
		Message message = messageProcessor.getMessageHandler().getEmptyMessage();
		AddResourceMessage resourceMessage = (AddResourceMessage)messageProcessor.getMessageHandler().getResourceMessage(ResourceMessage.TYPE_ADDMINI_RESOURCE);
		if(type.equalsIgnoreCase("fileReceived")){
			//"ns":"org.jwebsocket.plugins.system","type":"broadcast","utid":"3","pool":"","data":"Your Message"
			message.setNs("org.jwebsocket.plugins.system");
            message.setAction("fileReceived");
            message.setType("broadcast");
            message.setData(filename);
            message.setResourcesActive("1");
            message.setResourcesName(filename);
            message.setResourcesFilename(filename);
            message.setResourcesPath(messageData);
            
            resourceMessage.setResourcesActive("1");
            resourceMessage.setResourcesName(filename);
            resourceMessage.setResourcesFilename(filename);
            resourceMessage.setResourcesPath(messageData);
            
            if(filename.endsWith("meta")){
            	String fileNameWithoutExtention = filename.substring(0,filename.length()-5);
            	message.setData("b--"+fileNameWithoutExtention);
            	
            	Map lConnectorMap = getTokenServer().getAllConnectors();
                Collection<WebSocketConnector> lConnectors = lConnectorMap.values();
                for (WebSocketConnector wsc : lConnectors) {
                	if(wsc.getEngine().getId().equals(EngineType.ALL.toString())||wsc.getEngine().getId().equals(EngineType.BROADCAST.toString())){
                		 WebSocketPacket wsPacket = new RawPacket(message.getData());
                         getTokenServer().sendPacket(wsc, wsPacket); 
                         log.debug("SENDING FILE INFO TO CLIENT RECEIVED"+message.getData());
                    }
                }
            }
        }		
	}
    
	public void streamRecevied(String type,String filename,byte[] messageData,boolean writeToFiles) {
		//FileHandlerUtils.appendToFile(FileHandlerUtils.STREAM_BUFFER, messageData);
		log.debug("STREAM RECEIVED"+type);
		Message message = messageProcessor.getMessageHandler().getEmptyMessage();
		
		if(type.equalsIgnoreCase("streamReceived")){
			//"ns":"org.jwebsocket.plugins.system","type":"broadcast","utid":"3","pool":"","data":"Your Message"
			message.setNs("org.jwebsocket.plugins.system");
            message.setAction("streamReceived");
            message.setType("broadcast");
            byte[]  base64Encoded = Base64.encode(messageData);
            byte[] base64Decoded = Base64.decode(base64Encoded);
            message.setDataStream(messageData);
            message.setDataStream(base64Encoded);
            //FileHandlerUtils.appendToFile(FileHandlerUtils.BASE64_ENCODED, base64Encoded);
            //System.out.println("STREAM ENCODED"+messageData);
            sendPacket(messageProcessor.getMessageHandler().getMessage(message));
            //sendPacket(messageData,writeToFiles);
        }
		
		
		
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final String prop = evt.getPropertyName();
        final Object oldVal = evt.getOldValue();
        final Object newVal = evt.getNewValue();
//        System.out.println("Property: " + prop + ", Old: " + oldVal + ", New: " + newVal );

        if (UdpServer.STATE_PROP.equals(prop)) {
            final UdpServer.State state = (UdpServer.State) newVal;
            switch (state) {
                case STARTING:
//                                    stateLabel.setText("Starting");
//                                    startStopButton.setEnabled(false);
                    break;
                case STARTED:
//                                    stateLabel.setText("Started");
//                                    startStopButton.setText("Stop");
//                                    startStopButton.setEnabled(true);
                    break;
                case STOPPING:
//                                    stateLabel.setText("Stopping");
//                                    startStopButton.setEnabled(false);
                    break;
                case STOPPED:
//                                    stateLabel.setText("Stopped");
//                                    startStopButton.setText("Start");
//                                    startStopButton.setEnabled(true);
                    break;
                default:
                    assert false : state;
                    break;
            }   // end switch
        }

        if (UdpServer.PORT_PROP.equals(evt.getPropertyName())) {


        } else if (UdpServer.GROUPS_PROP.equals(evt.getPropertyName())) {

        }
    }

    /*public static void main(String[] args) {

        WebSocketTokenServer jc = new WebSocketTokenServer();
        jc.init();
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(3000);
                Object c = jc.getTokenServer().getAllConnectors();
//                System.out.println("C "+c);

                Map lConnectorMap = jc.getTokenServer().getAllConnectors();
                List<Map> lResultList = new ArrayList<Map>();
                Collection<WebSocketConnector> lConnectors = lConnectorMap.values();
                for (WebSocketConnector lConnector : lConnectors) {
                    Map lResultItem = new HashMap<String, Object>();
                    lResultItem.put("port", lConnector.getRemotePort());
                    lResultItem.put("unid", lConnector.getNodeId());
                    lResultItem.put("username", lConnector.getUsername());
                    lResultItem.put("isToken", true);
                    lResultList.add(lResultItem);
                }
                for (Map m : lResultList) {
//                    System.out.println("m "+m);
                }

                jc.sendPacket(i % 5 + 1);
            } catch (InterruptedException ex) {
                Logger.getLogger(WebSocketTokenServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }*/

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public ApplicationClientManager getApplicationClientManager() {
        return applicationClientManager;
    }

    public void setApplicationClientManager(ApplicationClientManager applicationClientManager) {
        this.applicationClientManager = applicationClientManager;
    }

    public MessageProcessor getMessageProcessor() {
        return messageProcessor;
    }

    public void setMessageProcessor(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

	public FileReadTimer getFileReadTimer() {
		return fileReadTimer;
	}

	public void setFileReadTimer(FileReadTimer fileReadTimer) {
		this.fileReadTimer = fileReadTimer;
	}
	
}
