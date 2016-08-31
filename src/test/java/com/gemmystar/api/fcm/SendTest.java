package com.gemmystar.api.fcm;

import java.io.ByteArrayInputStream;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

/**
 * https://firebase.google.com/docs/cloud-messaging/send-message#http_post_request
 * 
 * @author Administrator
 *
 */
public class SendTest {

	public static void main(String[] args) throws Exception{
		HttpTransport HTTP_TRANSPORT = new NetHttpTransport(); 
		 
		String PostUrl = "https://fcm.googleapis.com/fcm/send";
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() { 
            @Override 
            public void initialize(HttpRequest request) { 

                //request.getHeaders().setAccept("application/xml"); 
            	request.getHeaders().setAuthorization("key=AIzaSyDUficwhs4UPxwM1LMZ9enEsxwNCkBS8zU");
            } 
        });

		//generate the REST based URL
		GenericUrl url = new GenericUrl(PostUrl.replaceAll(" ", "%20"));
		//make POST request

		String requestBody = "{\"data\": {\"message\":\"gemmystar test message\"},\"to\": \"1234\"}";

		HttpRequest request = requestFactory.buildPostRequest(url, ByteArrayContent.fromString(null, requestBody));
		request.getHeaders().setContentType("application/json");
		// Google servers will fail to process a POST/PUT/PATCH unless the Content-Length
		// header >= 1
		//request.setAllowEmptyContent(false);
		System.out.println("HttpRequest request" + request);
		HttpResponse response = request.execute();
		
		System.out.println("-------- response ------");
		
		
		
		System.out.println(response.parseAsString());

	}

}
