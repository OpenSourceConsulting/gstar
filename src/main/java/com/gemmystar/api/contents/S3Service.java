/**
 * 
 */
package com.gemmystar.api.contents;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.internal.ServiceUtils;
import com.amazonaws.services.s3.internal.SkipMd5CheckStrategy;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.common.util.FileUtil;

/**
 * @author BongJin Kwon
 *
 */
@Service
public class S3Service {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);
	
	public static final String S3_IMG_HOST_NAME = "https://s3.ap-northeast-2.amazonaws.com/";
	
	@Value("${gemmy.upload.location}")
	private String uploadPath;
	
	@Value("${gemmy.s3.bucketName}")
	private String s3BucketName;
	
	private AmazonS3 s3 = new AmazonS3Client();
	
	public S3Service() {
		
	}
	
	public void uploadToS3(File uploadedFile, String s3key) {
        LOGGER.debug("Uploading a new object to S3 from a file\n");
        s3.putObject(new PutObjectRequest(s3BucketName, s3key, uploadedFile));
	}
	
	public void uploadToS3(MultipartFile mfile, String s3key, boolean canPublicRead) throws IOException{
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(mfile.getContentType());
		
		
		PutObjectRequest putObject = new PutObjectRequest(s3BucketName, s3key, mfile.getInputStream(), metadata);
		
		if (canPublicRead) {
			putObject.setCannedAcl(CannedAccessControlList.PublicRead);
		}
		
		
        LOGGER.debug("Uploading a new object to S3 from a file\n");
        s3.putObject(putObject);
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
	
	public String getS3ImgURL(String s3key) {
		return getS3ImgURL(s3BucketName, s3key);
	}
	
	public String getS3ImgURL(String bucketName, String s3key) {
		return S3_IMG_HOST_NAME + bucketName + "/"+ s3key;
	}
	
	
}
