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

                //request.getHeaders().setAccept("application/xml"); AIzaSyDUficwhs4UPxwM1LMZ9enEsxwNCkBS8zU
            	request.getHeaders().setAuthorization("key=AIzaSyDUficwhs4UPxwM1LMZ9enEsxwNCkBS8zU");
            } 
        });

		//generate the REST based URL
		GenericUrl url = new GenericUrl(PostUrl.replaceAll(" ", "%20"));
		//make POST request
		
		//String requestBody = "{\"data\": {\"message\":\"gemmystar test message\"},\"to\": \"cQV38jVuaFs:APA91bE1vNswl7qqZqeEYjCaYuIRzbcoWG-JMQCijfrIrhslGAFFpUJiW7lwjjNIIopyCkYZEJZO6V1gqzKcSJRm27TtCmGM7HojQOfu2PLnVbRn0W8MbeidOvicFlA-uLtBiKIeTIdH\"}";

		//String requestBody = "{\"data\": {\"message\":\"gemmystar test message\"},\"to\": \"eSFNyZDy4io:APA91bGyQ4ArV-qYRewwxuaOXsPAja8NWoXDFn2EKF25ytvg3SeI4xh0sPEuQxsDPFCf76duRF3TZ423BBAM0DRPnFkB37BRIwB_pDkSI75RpW29orUftfYPV60QYAhTYZxo9ZEdHYKJ\"}";

		//String requestBody = "{\"notification\": {\"title\":\"gemmystar title\", \"text\":\"gemmystar test message\"},\"to\": \"cQV38jVuaFs:APA91bE1vNswl7qqZqeEYjCaYuIRzbcoWG-JMQCijfrIrhslGAFFpUJiW7lwjjNIIopyCkYZEJZO6V1gqzKcSJRm27TtCmGM7HojQOfu2PLnVbRn0W8MbeidOvicFlA-uLtBiKIeTIdH\"}";

		String requestBody = "{\"notification\": {\"title\":\"gemmystar title111\", \"text\":\"gemmystar test message\"},\"to\": \"fPm9YPCJxcQ:APA91bHB06OsfUckEb06YZmERT-wDbIVBHxvi_OVy4V3D0L4KvfNFWMXFw4GRQo_TqJ4SqY7FbBv3qn1wUh7V-ZXPMjT8ZLoHKQGF1QY2jnOLC-jAj92dk-F1HoF5mKXiPDjWcAd-ghm\"}";

		
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
