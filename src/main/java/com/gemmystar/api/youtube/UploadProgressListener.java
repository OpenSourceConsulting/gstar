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

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author Bongjin Kwon
 * @version 1.0
 */
public class UploadProgressListener implements MediaHttpUploaderProgressListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadProgressListener.class);

	public UploadProgressListener() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener
	 * #progressChanged
	 * (com.google.api.client.googleapis.media.MediaHttpUploader)
	 */
	@Override
	public void progressChanged(MediaHttpUploader uploader) throws IOException {
		switch (uploader.getUploadState()) {
		case INITIATION_STARTED:
			LOGGER.info("Initiation Started");
			break;
		case INITIATION_COMPLETE:
			LOGGER.info("Initiation Completed");
			break;
		case MEDIA_IN_PROGRESS:
			//LOGGER.debug("Upload in progress");
			LOGGER.debug("Uploading percentage: {}", uploader.getProgress());
			break;
		case MEDIA_COMPLETE:
			LOGGER.info("Upload Completed!");
			break;
		case NOT_STARTED:
			LOGGER.info("Upload Not Started!");
			break;
		}

	}

}
// end of UploadProgressListener.java