package com.gemmystar.api.room;

import java.io.File;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.common.util.FileUtil;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.contents.domain.GstarContentsRepository;
import com.gemmystar.api.room.domain.GstarRoom;
import com.gemmystar.api.room.domain.GstarRoomRepository;
import com.gemmystar.api.room.domain.GstarRoomSpecs;
import com.gemmystar.api.tag.GstarContentsTagsService;
import com.gemmystar.api.tag.GstarHashTagService;
import com.gemmystar.api.tag.domain.GstarContentsTags;
import com.gemmystar.api.tag.domain.GstarHashTag;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarRoomService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GstarRoomService.class);

	@Autowired
	private GstarRoomRepository repository;
	
	@Autowired
	private GstarContentsRepository contentsRepo;
	
	@Autowired
	private GstarHashTagService hashTagService;
	
	@Autowired
	private GstarContentsTagsService contentsTagsService;
	
	public GstarRoomService() {
		
	}
	
	@Transactional
	@CacheEvict(cacheNames="rooms", allEntries=true)
	public void saveWithContents(GstarRoom gstarRoom, GstarContents contents, String[] tags){
		
		
		contentsRepo.save(contents);
		
		if (contents.getGstarRoomId() == null) {
			
			gstarRoom.setMasterContentsId(contents.getId());
			repository.save(gstarRoom);
			
			contents.setGstarRoomId(gstarRoom.getId());
			contentsRepo.save(contents);// update gstar_room_id
		} 
		
		for (int i = 0; i < tags.length; i++) {
			GstarHashTag tag = hashTagService.save(new GstarHashTag(tags[i]));
			
			contentsTagsService.save(new GstarContentsTags(contents.getId(), tag.getId()));
		}
	}
	
	
	public void save(GstarRoom gstarRoom){
		repository.save(gstarRoom);
	}
	
	@Cacheable("rooms")
	public Page<GstarRoom> getGstarRoomList(Pageable pageable, String search){
		
		Page<GstarRoom> page = null;

		if (search != null) {
			Specifications<GstarRoom> spec = Specifications.where(GstarRoomSpecs.likeSubject(search)).or(GstarRoomSpecs.likeTag(search));
			
			page = repository.findAll(spec, pageable);
		} else {
			page = repository.findAll(pageable);
		}
		
		
		return page;
	}
	
	public Page<GstarRoom> getUserGstarRoomList(Pageable pageable, Long gstarUserId){
		
		Specifications<GstarRoom> spec = Specifications.where(GstarRoomSpecs.myRoom(gstarUserId));
		
		Page<GstarRoom> page = repository.findAll(spec, pageable);
		
		return page;
	}
	
	public Page<GstarRoom> getGstarRoomBestList(int size){
		
		Sort sort = new Sort(Sort.Direction.DESC, "pointSum", "createDt");
		PageRequest pageable = new PageRequest(0, size, sort);
		
		Page<GstarRoom> page = repository.findAll(pageable);
		
		return page;
	}
	
	public GstarRoom getGstarRoom(Long roomId){
		GstarRoom room = repository.findOne(roomId);
		
		return room;
	}
	
	public GstarRoom getGstarRoomWithChallengers(Long roomId){
		GstarRoom room = repository.findOne(roomId);
		
		room.setChallengerContentsList(contentsRepo.findChallengerContents(roomId));
		
		return room;
	}
	
	public GstarRoom getBestTopGstarRoom() {
		return repository.findTopByOrderByPointSumDescCreateDtDesc();
	}
	
	
	public void deleteGstarRoom(Long roomId){
		repository.delete(roomId);
	}

}
//end of GstarRoomService.java