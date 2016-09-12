/**
 * 
 */
package com.gemmystar.api.common.push;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

/**
 * @author Administrator
 *
 */
@Component
public class AndroidFcmSender implements InitializingBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidFcmSender.class);
	
	private HttpRequestFactory requestFactory;
	
	private GenericUrl url = new GenericUrl("https://fcm.googleapis.com/fcm/send");

	public AndroidFcmSender() {
	}
	
	
	public void sendPushMessage(String message, String clientFcmToken) throws IOException {
		String requestBody = "{\"data\": {\"message\":\""+message+"\"},\"to\": \""+clientFcmToken+"\"}";

		HttpRequest request = requestFactory.buildPostRequest(url, ByteArrayContent.fromString(null, requestBody));
		request.getHeaders().setContentType("application/json");
		// Google servers will fail to process a POST/PUT/PATCH unless the Content-Length
		// header >= 1
		//request.setAllowEmptyContent(false);
		HttpResponse response = request.execute();
		LOGGER.info("send ok.");
		
		
		LOGGER.info("-------- response ------");
		LOGGER.info(response.parseAsString());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		requestFactory = new NetHttpTransport().createRequestFactory(new HttpRequestInitializer() { 
            @Override 
            public void initialize(HttpRequest request) { 

                //request.getHeaders().setAccept("application/xml"); 
            	request.getHeaders().setAuthorization("key=AIzaSyDUficwhs4UPxwM1LMZ9enEsxwNCkBS8zU");
            } 
        });
		
	}

}
