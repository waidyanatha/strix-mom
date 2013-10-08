package org.strix.mom.server.message.api;

/**
 * Created by IntelliJ IDEA.
 * User: SSC1
 * Date: 6/21/13
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Message {
    String getNs();

    void setNs(String ns);

    String getType();

    void setType(String type);

    String getClientType();

    void setClientType(String clientType);

    String getClientName();

    void setClientName(String clientName);

    String getClientVersion();

    void setClientVersion(String clientVersion);

    String getClientInfo();

    void setClientInfo(String clientInfo);

    String getJwsType();

    void setJwsType(String jwsType);

    String getJwsVersion();

    void setJwsVersion(String jwsVersion);

    String getJwsInfo();

    void setJwsInfo(String jwsInfo);

//    String getEncodingFormats();

//    void setEncodingFormats(String encodingFormats);

    String getUtid();

    void setUtid(String utid);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    String getEncoding();

    void setEncoding(String encoding);

    String getPool();

    void setPool(String pool);

    String getChannel();

    void setChannel(String channel);

    String getAccessKey();

    void setAccessKey(String accessKey);

    String getSecretKey();

    void setSecretKey(String secretKey);

    String getData();

    void setData(String data);

    String getAction();
    
    void setAction(String action);
    
    byte[] getDataStream();

    void setDataStream(byte[] dataStream);

	String getResourcesName();

	void setResourcesName(String resourcesName);
    
	String getResourcesGrade() ;
    
	void setResourcesGrade(String resourcesGrade) ;
    
	String getResourcesCategory() ;

    void setResourcesCategory(String resourcesCategory) ;

    String getResourcesActive() ;

    void setResourcesActive(String resourcesActive) ;

    String getResourcesPath() ;

    void setResourcesPath(String resourcesPath) ;

    String getResourcesFilename() ;

    void setResourcesFilename(String resourcesFilename) ;

    String getResourcesAuthor() ;

    void setResourcesAuthor(String resourcesAuthor) ;

    String getResourcesSize() ;

    void setResourcesSize(String resourcesSize) ;

    String getResourcesExtra() ;

    void setResourcesExtra(String resourcesExtra) ;

    String getResourcesDetails() ;

    void setResourcesDetails(String resourcesDetails) ;

    String getResourcesContent() ;

    void setResourcesContent(String resourcesContent) ;
    
    
}
