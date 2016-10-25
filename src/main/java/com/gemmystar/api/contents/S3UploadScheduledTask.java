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
 * BongJin Kwon		2016. 8. 16.		First Draft.
 */
package com.gemmystar.api.contents;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.common.util.FileUtil;
import com.gemmystar.api.contents.domain.GstarContents;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Component
public class S3UploadScheduledTask implements InitializingBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(S3UploadScheduledTask.class);
	
	@Value("${gemmy.upload.location}")
	private String uploadPath;
	
	@Value("${gemmy.s3.bucketName}")
	private String s3BucketName;
	
	@Autowired
	private GstarContentsService contentsService;
	
	private File uploadDir;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public S3UploadScheduledTask() {
		
	}
	
	@Scheduled(cron="0 0/5 * * * *")
	//@Scheduled(fixedRate = 15000)
	public void upload() {
		
		
		File[] files = uploadDir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.canRead();
			}
			
		});
		
		LOGGER.debug("files length : {}", files.length);
		
		for (int i = 0; i < files.length; i++) {
			uploadToS3(files[i]);
		}
		
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		uploadDir = new File(getBackupPath());
		
		if (uploadDir.exists() == false) {
			uploadDir.mkdirs();
			
			LOGGER.info("make {}", uploadDir.getAbsolutePath());
		} else {
			LOGGER.info("exist {}", uploadDir.getAbsolutePath());
		}
	}
	
	public void uploadToS3(File uploadedFile) {
		
		try{
			AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
	        LOGGER.debug("Uploading a new object to S3 from a {}", uploadedFile.getName());
	        String keyName = GemmyConstant.S3_KEY_PREFIX_VIDEO + uploadedFile.getName();
	        s3client.putObject(new PutObjectRequest(s3BucketName, keyName, uploadedFile));
	        
	        contentsService.saveS3Key(getContentsId(uploadedFile.getName()), keyName);
	        
	        LOGGER.debug("upload success.");
	        
	        uploadedFile.delete();
        
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
		}
	}
	
	public String getBackupPath() {
		return uploadPath + File.separator + "s3";
	}
	
	private Long getContentsId(String fileName) {
		int pos = fileName.indexOf("_");
		
		return Long.parseLong(fileName.substring(1, pos));
	}
	
}
//end of S3UploadScheduledTask.java