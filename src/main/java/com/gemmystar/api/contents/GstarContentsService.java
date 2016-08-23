package com.gemmystar.api.contents;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.common.exception.ContentsNotFoundException;
import com.gemmystar.api.common.util.FileUtil;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.contents.domain.GstarContentsRepository;
import com.gemmystar.api.contents.domain.GstarContentsWarn;
import com.gemmystar.api.contents.domain.GstarContentsWarnRepository;
import com.gemmystar.api.contents.specs.GstarContentsSpecs;
import com.gemmystar.api.room.GstarRoomService;
import com.gemmystar.api.room.domain.GstarRoomRepository;
import com.gemmystar.api.tag.GstarContentsTagsService;
import com.gemmystar.api.tag.GstarHashTagService;
import com.gemmystar.api.tag.domain.GstarContentsTags;
import com.gemmystar.api.tag.domain.GstarHashTag;
import com.gemmystar.api.view.domain.GstarView;
import com.gemmystar.api.view.domain.GstarViewPK;
import com.gemmystar.api.view.domain.GstarViewRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarContentsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GstarContentsService.class);

	@Autowired
	private GstarContentsRepository repository;
	
	@Autowired
	private GstarRoomService roomService;
	
	@Autowired
	private GstarHashTagService hashTagService;
	
	@Autowired
	private GstarContentsTagsService contentsTagsService;
	
	@Autowired
	private GstarInfoService infoService;
	
	@Autowired
	private GstarViewRepository viewRepo;
	
	@Autowired
	private GstarRoomRepository roomRepo;
	
	@Autowired
	private GstarContentsWarnRepository warnRepo;
	
	@Value("${gemmy.s3.bucketName}")
	private String s3BucketName;
	
	@Autowired
	private S3UploadScheduledTask s3Uploader;
	
	public GstarContentsService() {
		
	}
	
	public void save(GstarContents gstarContents, String[] tags){
		repository.save(gstarContents);
		
		for (int i = 0; i < tags.length; i++) {
			GstarHashTag tag = hashTagService.save(new GstarHashTag(tags[i]));
			
			contentsTagsService.save(new GstarContentsTags(gstarContents.getId(), tag.getId()));
		}
	}
	
	public void uploadToS3(File uploadedFile, Long gstarContentsId, String youtubeId) {
		AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
        LOGGER.debug("Uploading a new object to S3 from a file\n");
        String keyName = GemmyConstant.S3_KEY_PREFIX_VIDEO + "C" + gstarContentsId + "_" + youtubeId + "." + FileUtil.getExtension(uploadedFile);
        s3client.putObject(new PutObjectRequest(s3BucketName, keyName, uploadedFile));
	}
	
	public void backupForS3(File uploadedFile, Long gstarContentsId, String youtubeId) {
		
        String backupFileName = "C" + gstarContentsId + "_" + youtubeId + "." + FileUtil.getExtension(uploadedFile);
        uploadedFile.renameTo(new File(s3Uploader.getBackupPath() + File.separator + backupFileName));
        
        LOGGER.debug("backup {}", backupFileName);
	}
	
	public Page<GstarContents> getGstarContentsList(Pageable pageable, String search){
		
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.notMatching());
		
		if (search != null) {
			spec = spec.and(GstarContentsSpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
	}
	
	public List<GstarContents> getRecommandList(){
		
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.recommands());
		
		return repository.findAll(spec, new Sort(Direction.DESC, "createDt"));
	}
	
	public Page<GstarContents> getUserGstarContentsList(Pageable pageable, Long gstarUserId){
		
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.myContents(gstarUserId));
		
		Page<GstarContents> page = repository.findAll(spec, pageable);
		//Page<GstarContents> page = repository.getMyContents(new GstarUser(gstarUserId), pageable);
		
		return page;
	}
	
	/**
	 * <pre>
	 * 사용자 즐겨찾기 컨텐츠 목록.사용자가 좋아요(heart)했던 컨텐츠 목록.
	 * </pre>
	 * @param pageable
	 * @param gstarUserId
	 * @return
	 */
	public Page<GstarContents> getUserHeartGstarContentsList(Pageable pageable, Long gstarUserId){
		
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.myHeartContents(gstarUserId));
		
		Page<GstarContents> page = repository.findAll(spec, pageable);
		
		return page;
	}
	
	public GstarContents getGstarContents(Long contentsId){
		return repository.findOne(contentsId);
	}
	
	public void deleteGstarContents(Long contentsId){
		repository.delete(contentsId);
	}
	
	@Transactional
	public void increaseViewCnt(Long contentsId, Long userId) {
		
		GstarContents contents = getGstarContents(contentsId);
		
		if (contents == null) {
			throw new ContentsNotFoundException(contentsId);
		}
		
		infoService.increaseViewCnt(contentsId);
		
		GstarView view = viewRepo.findOne(new GstarViewPK(userId, contentsId));
		
		if(view == null) {
			viewRepo.save(new GstarView(userId, contentsId, 1L));
		} else {
			viewRepo.increaseViewCnt(userId, contentsId);
		}
		
		if (contents.getGstarRoomId() != null && contents.getGstarRoomId() > 0 ) {
			roomRepo.increaseViewSum(contents.getGstarRoomId());
		}
	}
	
	public void warnGstarContents(Long gstarUserId, Long gstarContentsId, String warnMemo, String warnTypeCd){
		
		warnRepo.save(new GstarContentsWarn(gstarUserId, gstarContentsId, warnMemo, warnTypeCd));
	}

}
//end of GstarContentsService.java