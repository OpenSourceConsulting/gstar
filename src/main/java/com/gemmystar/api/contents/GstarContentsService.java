package com.gemmystar.api.contents;

import java.io.File;
import java.util.ArrayList;
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
import com.gemmystar.api.contents.domain.GstarContentsQRepository;
import com.gemmystar.api.contents.domain.GstarContentsRepository;
import com.gemmystar.api.contents.domain.GstarContentsWarn;
import com.gemmystar.api.contents.domain.GstarContentsWarnRepository;
import com.gemmystar.api.contents.specs.GstarContentsPredicate;
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
import com.gemmystar.api.youtube.YoutubeService;

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
	private GstarContentsQRepository qrepository;
	
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
	
	@Autowired
	private S3Service s3service;
	
	@Autowired
	private YoutubeService youtubeService;
	
	
	
	public GstarContentsService() {
		
	}
	
	public void save(GstarContents gstarContents){
		repository.save(gstarContents);
	}
	
	/**
	 * <pre>
	 * 일반 동영상 등록.
	 * </pre>
	 * @param gstarContents
	 * @param tags
	 */
	@Transactional
	public void save(GstarContents gstarContents, String[] tags){
		
		gstarContents.setMemberTypeCd(null);
		repository.save(gstarContents);
		
		for (int i = 0; i < tags.length; i++) {
			GstarHashTag tag = hashTagService.save(new GstarHashTag(tags[i]));
			
			contentsTagsService.save(new GstarContentsTags(gstarContents.getId(), tag.getId()));
		}
	}
	
	public void saveS3Key(Long gstarContentsId, String s3key) {
		
		GstarContents contents = getGstarContents(gstarContentsId);
		
		contents.setS3key(s3key);
		
		save(contents);
	}
	
	public void uploadToS3(File uploadedFile, Long gstarContentsId, String youtubeId) {
		AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
        LOGGER.debug("Uploading a new object to S3 from a file\n");
        String keyName = GemmyConstant.S3_KEY_PREFIX_VIDEO + "C" + gstarContentsId + "_" + youtubeId + "." + FileUtil.getExtension(uploadedFile);
        s3client.putObject(new PutObjectRequest(s3BucketName, keyName, uploadedFile));
	}
	
	public void backupForS3(File uploadedFile, Long gstarContentsId, String youtubeId) {
		
        String backupFileName = FileUtil.getS3FileName(uploadedFile, gstarContentsId, youtubeId);
        uploadedFile.renameTo(new File(s3Uploader.getBackupPath() + File.separator + backupFileName));
        
        LOGGER.debug("backup {}", backupFileName);
	}
	
	
	public Page<GstarContents> getGstarContentsList(Pageable pageable, String search){
		
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.notBattle()).and(GstarContentsSpecs.notDeteled());
		
		if (search != null) {
			spec = spec.and(GstarContentsSpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
	}
	
	public Page<GstarContents> getGstarBattleWarnContentsList(Pageable pageable, String search){
		
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.battleWarn(10)).and(GstarContentsSpecs.notDeteled());
		
		if (search != null) {
			spec = spec.and(GstarContentsSpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
	}
	
	public List<GstarContents> getGstarContentsList(String search){
		
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.notBattle())
				.and(GstarContentsSpecs.notDeteled())
				.and(GstarContentsSpecs.search(search));

		
		return repository.findAll(spec);
	}
	
	/**
	 * <pre>
	 * 명예의 전당 영상 목록.
	 * </pre>
	 * @param pageable
	 * @return
	 */
	public Page<GstarContents> getHonoraryWinnerList(Pageable pageable){
		
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.honoraryWinner()).and(GstarContentsSpecs.notDeteled());
		
		
		return repository.findAll(spec, pageable);
	}
	
	public Iterable<GstarContents> getRecommandList(boolean isAll){
		
		return qrepository.findAll(GstarContentsPredicate.recommands(isAll), new Sort("orderSeq"));// default asc.
		
		/*
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.recommands());
		
		return repository.findAll(spec, new Sort(Direction.DESC, "createDt"));
		*/
	}
	
	/**
	 * 정렬순서 수정.
	 * @param ids
	 */
	public void ordering(Long[] ids) {
		
		List<GstarContents> contentsList = new ArrayList<GstarContents>();
		
		int orderSeq = 1;
		for (Long contentsId : ids) {
			GstarContents contents = getGstarContents(contentsId);
			contents.setOrderSeq(orderSeq);
			contentsList.add(contents);
			
			orderSeq++;
		}
		
		repository.save(contentsList);
	}
	
	public Page<GstarContents> getUserGstarContentsList(Pageable pageable, Long gstarUserId){
		
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.myContents(gstarUserId)).and(GstarContentsSpecs.notDeteled());
		
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
		
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.myHeartContents(gstarUserId)).and(GstarContentsSpecs.notDeteled());
		
		Page<GstarContents> page = repository.findAll(spec, pageable);
		
		return page;
	}
	
	public GstarContents getGstarContents(Long contentsId){
		return repository.findOne(contentsId);
	}
	
	@Transactional
	public void deleteGstarContents(GstarContents contents){
		
		if (contents.getBattleHistories() != null && contents.getBattleHistories().size() > 0) {
			
			throw new RuntimeException("대결이 진행중이어서 삭제할수 없습니다.");
		}
		
		
		if (contents.getGstarPointHistories().size() > 0) {
			
			contents.setStatusCd(GemmyConstant.CODE_CNTS_STATUS_CLOSED);
			contents.setDeleted(true);
			
			save(contents);
		} else {
			
			if (contents.getGstarRoom() != null && contents.getGstarRoom().getMasterContentsId().equals(contents.getId())) {
				/*
				 * 대결방 방장인경우
				 */
				
				if (contents.getGstarRoom().getGstarRoomContentsList().size() == 1) {
					/*
					 * 도전자들이 없으면 대결방까지 삭제.
					 */
					Long roomId = contents.getGstarRoomId();
					
					contents.setGstarRoomId(null);
					repository.saveAndFlush(contents);// save 만 하면 update 안됨.
					
					
					roomService.deleteGstarRoom(roomId);
					
					
				} else {
					throw new RuntimeException("현재 대결방의 방장이어서 삭제할수 없습니다.");
				}
				
			}
			
			
			repository.delete(contents);
		}
		
		if (contents.getS3key() != null) {
			s3service.deleteObject(contents.getS3key());
		}
		
		youtubeService.deleteVideo(contents.getUrl()); // url is youtube id.
		
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
	
	@Transactional
	public void increasePoint(Long contentsId, Long usePoint) {
		
		GstarContents contents = getGstarContents(contentsId);
		
		if (contents == null) {
			throw new ContentsNotFoundException(contentsId);
		}
		
		infoService.increasePointCnt(contentsId, usePoint);
		
		if (contents.getGstarRoomId() != null && contents.getGstarRoomId() > 0 ) {
			roomRepo.increasePointSum(contents.getGstarRoomId(), usePoint);
		}
	}

}
//end of GstarContentsService.java