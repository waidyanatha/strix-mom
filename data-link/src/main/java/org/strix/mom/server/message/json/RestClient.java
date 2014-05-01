package org.strix.mom.server.message.json;

//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Author: Tharindu Jayasuriya
 */
public class RestClient {
	private String resUrl = null;

	// public String sendRestMessage(String meesage){
	// System.out.println("Input to Server .... "+meesage);
	// Client client = Client.create();
	//
	// WebResource webResource = client
	// .resource(resUrl);
	//
	// String input = meesage;
	//
	// ClientResponse response = webResource.type("application/json")
	// .post(ClientResponse.class, input);
	//
	// if (response.getStatus() != 201) {
	// throw new RuntimeException("Failed : HTTP error code : "
	// + response.getStatus());
	// }
	//
	// System.out.println("Output from Server .... ");
	// String output = response.getEntity(String.class);
	// System.out.println(output);
	// return output;
	// }

	public String sendMessage(String message) {

		String url = resUrl+message;
		String output = null;
		System.out.println("Input message to the Server .... " + url);
		try {

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			// con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			output = response.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// print result
		System.out.println("Output from Server .... ");
		//System.out.println(output);
		return output;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

}
