/* 
 * Copyright (C) 2012-2015 Open Source Consulting, Inc. All rights reserved by Open Source Consulting, Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * Revision History
 * Author			Date				Description
 * ---------------	----------------	------------
 * BongJin Kwon		2016. 8. 3.		First Draft.
 */
package com.gemmystar.api.youtube;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.gemmystar.api.contents.domain.GstarContents;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.google.common.collect.Lists;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author Bongjin Kwon
 * @version 1.0
 */
@Service
public class YoutubeService implements InitializingBean{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YoutubeService.class);

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	/** Global instance of Youtube object to make all API requests. */
	private static YouTube youtube;

	/** Global instance of the format used for the video being uploaded (MIME type). */
	private static String VIDEO_FILE_FORMAT = "video/*";
	
	private static final String API_JSON_FILE_NAME = "youtube-api-uploadvideo.json";

	private AuthorizationCodeInstalledApp authCodeInstalledApp;
	private Object syncObj = new Object();
	
	private File apiJsonFile = new File(System.getProperty("user.home"), ".credentials/" + API_JSON_FILE_NAME);
	
	private MediaHttpUploaderProgressListener progressListener = new UploadProgressListener();

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public YoutubeService() {

	}

	/**
	 * Authorizes the installed application to access user's protected data.
	 *
	 * @param scopes
	 *            list of scopes needed to run youtube upload.
	 */
	private Credential authorize(List<String> scopes) throws Exception {
		
		if (apiJsonFile.exists() == false) {
			throw new FileNotFoundException("file not found. " + apiJsonFile.getAbsolutePath());
		}

		if (authCodeInstalledApp == null) {
			
			synchronized (syncObj) {
				
				if (authCodeInstalledApp == null) {

					// Load client secrets.
					GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
							JSON_FACTORY, YoutubeService.class.getResourceAsStream("/client_secrets.json"));
		
					// Checks that the defaults have been replaced (Default =
					// "Enter X here").
					if (clientSecrets.getDetails().getClientId().startsWith("Enter")
							|| clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
						System.out.println("Enter Client ID and Secret from https://code.google.com/apis/console/?api=youtube"
										+ "into youtube-cmdline-uploadvideo-sample/src/main/resources/client_secrets.json");
						System.exit(1);
					}
		
					// Set up file credential store.
					FileCredentialStore credentialStore = new FileCredentialStore(
							//new File(System.getProperty("user.home"), ".credentials/youtube-api-uploadvideo.json"),
							apiJsonFile,
							JSON_FACTORY);
		
					// Set up authorization code flow.
					GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
							HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialStore(credentialStore).build();
		
					// Build the local server and bind it to port 9099
					LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(9099).build();
		
					authCodeInstalledApp = new AuthorizationCodeInstalledApp(flow, localReceiver);
				}
			}

		}

		// Authorize.
		return authCodeInstalledApp.authorize("user");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		if (apiJsonFile.exists() == false) {
			FileUtils.copyInputStreamToFile(YoutubeService.class.getResourceAsStream("/" + API_JSON_FILE_NAME), apiJsonFile);
			LOGGER.info("copy {}", apiJsonFile.getAbsoluteFile());
		}
		
	}
	
	public String uploadVideo(InputStream inputStream, long fileSize, GstarContents contents) {
		
		String videoID = null;
		// Scope required to upload to YouTube.
	    List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");

	    try {
	    	
	        // Authorization.
	        Credential credential = authorize(scopes);

	        // YouTube object used to make all API requests.
	        youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(
	          "youtube-cmdline-uploadvideo-sample").build();


	        // Add extra information to the video before uploading.
	        Video videoObjectDefiningMetadata = new Video();

	        /*
	         * Set the video to public, so it is available to everyone (what most people want). This is
	         * actually the default, but I wanted you to see what it looked like in case you need to set
	         * it to "unlisted" or "private" via API.
	         */
	        VideoStatus status = new VideoStatus();
	        status.setPrivacyStatus("public");
	        videoObjectDefiningMetadata.setStatus(status);

	        // We set a majority of the metadata with the VideoSnippet object.
	        VideoSnippet snippet = new VideoSnippet();

	        /*
	         * The Calendar instance is used to create a unique name and description for test purposes, so
	         * you can see multiple files being uploaded. You will want to remove this from your project
	         * and use your own standard names.
	         */
	        Calendar cal = Calendar.getInstance();
	        //snippet.setTitle("Test Upload via Java on " + cal.getTime());
	        //snippet.setDescription("Video uploaded via YouTube Data API V3 using the Java library " + "on " + cal.getTime());
	        snippet.setTitle(contents.getSubject());
	        snippet.setDescription(contents.getMemo());
	        

	        // Set completed snippet to the video object.
	        videoObjectDefiningMetadata.setSnippet(snippet);

	        InputStreamContent mediaContent = new InputStreamContent(
	          VIDEO_FILE_FORMAT, new BufferedInputStream(inputStream));
	        mediaContent.setLength(fileSize);

	        /*
	         * The upload command includes: 1. Information we want returned after file is successfully
	         * uploaded. 2. Metadata we want associated with the uploaded video. 3. Video file itself.
	         */
	        YouTube.Videos.Insert videoInsert = youtube.videos()
	          .insert("snippet,statistics,status", videoObjectDefiningMetadata, mediaContent);

	        // Set the upload type and add event listener.
	        MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

	        /*
	         * Sets whether direct media upload is enabled or disabled. True = whole media content is
	         * uploaded in a single request. False (default) = resumable media upload protocol to upload
	         * in data chunks.
	         */
	        uploader.setDirectUploadEnabled(false);

	      
	        uploader.setProgressListener(progressListener);

	        // Execute upload.
	        Video returnedVideo = videoInsert.execute();
	      
	        videoID = returnedVideo.getId();

	        // Print out returned results.
	        LOGGER.debug("================== Returned Video ==================\n");
	        LOGGER.debug("  - Id: {}", videoID);
	        LOGGER.debug("  - Title: {}", returnedVideo.getSnippet().getTitle());
	        LOGGER.debug("  - Tags: {}", returnedVideo.getSnippet().getTags());
	        LOGGER.debug("  - Privacy Status: {}", returnedVideo.getStatus().getPrivacyStatus());
	        LOGGER.debug("  - Video Count: {}", returnedVideo.getStatistics().getViewCount());

	    } catch (GoogleJsonResponseException e) {
	    	LOGGER.error("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage(), e);
	    	throw new RuntimeException(e);
	    } catch (Exception e) {
	    	LOGGER.error(e.toString(), e);
	    	throw new RuntimeException(e);
	    } 
	    
	    return videoID;
	}

}
// end of YoutubeService.java