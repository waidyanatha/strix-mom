package org.strix.mom.server.webServer;

import org.strix.mom.server.client.ApplicationClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Tharindu Jayasuriya
 */
public class ApplicationClientManager {

    private static ApplicationClientManager ourInstance = new ApplicationClientManager();
    private Map<String,ApplicationClient> applicationClients;

    public static ApplicationClientManager getInstance() {
        return ourInstance;
    }

    private ApplicationClientManager() {
        applicationClients = new HashMap<String,ApplicationClient>();
    }

    public void addApplicationClient(ApplicationClient applicationClient){
        applicationClients.put(applicationClient.getId(), applicationClient);
    }

    public void removeApplicationClient(ApplicationClient applicationClient){
        applicationClients.remove(applicationClient.getId());
    }

    public void removeApplicationClient(String applicationClientId){
        applicationClients.remove(applicationClientId);
    }

    public ApplicationClient getApplicationClient(String applicationClientId){
        return applicationClients.get(applicationClientId);
    }

    public Map<String, ApplicationClient> getApplicationClients() {
        return applicationClients;
    }


}
