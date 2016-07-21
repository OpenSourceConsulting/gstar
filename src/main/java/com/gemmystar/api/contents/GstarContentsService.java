package com.gemmystar.api.contents;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.contents.domain.GstarContentsRepository;
import com.gemmystar.api.room.GstarRoomService;
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
public class GstarContentsService {

	@Autowired
	private GstarContentsRepository repository;
	
	@Autowired
	private GstarRoomService roomService;
	
	@Autowired
	private GstarHashTagService hashTagService;
	
	@Autowired
	private GstarContentsTagsService contentsTagsService;
	
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
	/*
	public int getGstarContentsListTotalCount(GridParam gridParam){
		
		return repository.getGstarContentsListTotalCount(gridParam);
	}
	*/
	public GstarContents getGstarContents(Long contentsId){
		return repository.findOne(contentsId);
	}
	
	public void deleteGstarContents(Long contentsId){
		repository.delete(contentsId);
	}

}
//end of GstarContentsService.java