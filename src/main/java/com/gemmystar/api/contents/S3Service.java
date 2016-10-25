/**
 * 
 */
package com.gemmystar.api.contents;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.internal.ServiceUtils;
import com.amazonaws.services.s3.internal.SkipMd5CheckStrategy;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

/**
 * @author BongJin Kwon
 *
 */
@Service
public class S3Service {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);
	
	@Value("${gemmy.upload.location}")
	private String uploadPath;
	
	@Value("${gemmy.s3.bucketName}")
	private String s3BucketName;
	
	private AmazonS3 s3 = new AmazonS3Client();
	
	public S3Service() {
		
	}
	
	public File download(String s3key) {
		
		File destinationFile = new File(uploadPath + s3key);
		
		final GetObjectRequest getObjectRequest = new GetObjectRequest(s3BucketName, s3key);
		
		LOGGER.debug("downloading {}/{}", s3BucketName, s3key);
		
		ServiceUtils.retryableDownloadS3ObjectToFile(destinationFile, new ServiceUtils.RetryableS3DownloadTask() {

            @Override
            public S3Object getS3ObjectStream() {
                return s3.getObject(getObjectRequest);
            }

            @Override
            public boolean needIntegrityCheck() {
                return !SkipMd5CheckStrategy.INSTANCE.skipClientSideValidationPerRequest(getObjectRequest);
            }

        }, ServiceUtils.OVERWRITE_MODE);
		
		LOGGER.debug("download completed!! {}", destinationFile.getAbsolutePath());
		
		return destinationFile;
	}
	
	public void renameObject(String originKey, String renamedKey) {
		s3.copyObject(s3BucketName, originKey, s3BucketName, renamedKey);
		
		s3.deleteObject(s3BucketName, originKey);
	}
	
	public void deleteObject(String s3key) {
		
		s3.deleteObject(s3BucketName, s3key);
	}
	
	
}
