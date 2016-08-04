package com.gemmystar.api.tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gemmystar.api.tag.domain.GstarHashTag;
import com.gemmystar.api.tag.domain.GstarHashTagRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarHashTagService {

	@Autowired
	private GstarHashTagRepository repository;
	
	public GstarHashTagService() {
	}
	
	public GstarHashTag save(GstarHashTag gstarHashTag){
		
		GstarHashTag tag = repository.findByTag(gstarHashTag.getTag());
		
		if (tag == null) {
			tag = repository.save(gstarHashTag);
		}
		
		return tag;
	}
	
	public List<GstarHashTag> getGstarHashTagAllList(){
		return repository.findAll();
	}
	
	public GstarHashTag getGstarHashTag(Long tagId){
		return repository.findOne(tagId);
	}

	
	public void deleteGstarHashTag(Long tagId){
		repository.delete(tagId);
	}
	
	public List<GstarHashTag> findMainTags() {
		return repository.findMainTags();
	}

}
//end of GstarHashTagService.java