package org.strix.mom.server.message.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Tharindu Jayasuriya
 */
public class UrlGenerator {
	
	private static String DEFAULT_ENCODING_FOR_URL = "UTF-8";

    //private String address;
    private Map<String, String> map = new HashMap<String, String>();

    public UrlGenerator() {
        //this.address = "";
    }


    public String getParameter(String param) {
        return map.get(param);
    }

    public void setParameter(String param, Object value) {
        map.put(param, value.toString());
    }

    public void unsetParameter(String param) {
        map.remove(param);
    }

    public String getUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        List<String> listOfParams = new ArrayList<String>();
        for (String param : map.keySet()) {
            listOfParams.add(param + "=" + encodeString(map.get(param))+"&");
            String query= param + "=" + encodeString(map.get(param))+"&";
            sb.append(query);
        }

//        if (!listOfParams.isEmpty()) {
//            String query = StringUtils.join(listOfParams, "&");
//            sb.append("?");
//            sb.append(query);
//        }

        return sb.toString();
    }

    public static String encodeString(String name) throws NullPointerException {
        String tmp = null;

        if (name == null)
            return null;

        try {
            tmp = java.net.URLEncoder.encode(name, DEFAULT_ENCODING_FOR_URL);
        } catch (Exception e) {}

        if (tmp == null)
            throw new NullPointerException();

        return tmp;
    }

}
