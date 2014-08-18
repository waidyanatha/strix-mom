package org.strix.mom.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.strix.mom.server.communication.impl.UdpServer;
import org.strix.mom.server.message.file.FileHandlerUtils;
import org.strix.mom.server.webServer.WebSocketTokenServer;

import java.util.List;
import java.util.logging.Level;

/**
 * Author: Tharindu Jayasuriya
 */
public class MomServer {
    private WebSocketTokenServer webSocketTokenServer;
    private List<UdpServer> udpServerList = null;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring-processor.xml"});
        MomServer momServer = (MomServer) context.getBean("momServer");
        momServer.initComponents();

    }

    private void initComponents() {
    	System.out.println("INIT MOM SERVER19");
    	FileHandlerUtils.deleteFiles();
        webSocketTokenServer.init();
        for (UdpServer udpServer : udpServerList) {
            udpServer.setLoggingLevel(Level.OFF);
            udpServer.start();
            udpServer.addPropertyChangeListener(webSocketTokenServer);
            udpServer.fireProperties();
            udpServer.addUdpServerListener(webSocketTokenServer);
        }
    }

    
    
    public WebSocketTokenServer getWebSocketTokenServer() {
        return webSocketTokenServer;
    }

    public void setWebSocketTokenServer(WebSocketTokenServer webSocketTokenServer) {
        this.webSocketTokenServer = webSocketTokenServer;
    }

    public List<UdpServer> getUdpServerList() {
        return udpServerList;
    }

    public void setUdpServerList(List<UdpServer> udpServerList) {
        this.udpServerList = udpServerList;
    }
}
