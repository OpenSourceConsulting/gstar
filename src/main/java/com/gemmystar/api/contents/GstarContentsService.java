package com.gemmystar.api.contents;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.contents.domain.GstarContentsRepository;
import com.gemmystar.api.contents.domain.GstarInfo;
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
	
	public GstarContentsService() {
		
	}
	
	public void save(GstarContents gstarContents, String[] tags){
		repository.save(gstarContents);
		
		for (int i = 0; i < tags.length; i++) {
			GstarHashTag tag = hashTagService.save(new GstarHashTag(tags[i]));
			
			contentsTagsService.save(new GstarContentsTags(gstarContents.getId(), tag.getId()));
		}
	}
	
	public List<GstarContents> getGstarContentsAllList(){
		return repository.findAll();
	}
	
	public GstarContents getGstarContents(Long contentsId){
		return repository.findOne(contentsId);
	}
	
	public void deleteGstarContents(Long contentsId){
		repository.delete(contentsId);
	}
	
	@Transactional
	public void increaseViewCnt(Long contentsId, Long userId, Long gstarRoomId) {
		infoService.increaseViewCnt(contentsId);
		
		GstarView view = viewRepo.findOne(new GstarViewPK(userId, contentsId));
		
		if(view == null) {
			viewRepo.save(new GstarView(userId, contentsId, 1L));
		} else {
			viewRepo.increaseViewCnt(userId, contentsId);
		}
		
		if (gstarRoomId != null && gstarRoomId > 0 ) {
			roomRepo.increaseViewSum(gstarRoomId);
		}
	}

}
//end of GstarContentsService.java