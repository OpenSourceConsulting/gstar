package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.domain.GstarHashTag;
import com.gemmystar.api.domain.GstarHashTagRepository;

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
	
	public void save(GstarHashTag gstarHashTag){
		repository.save(gstarHashTag);
	}
	
	public List<GstarHashTag> getGstarHashTagAllList(){
		return repository.findAll();
	}
	/*
	public int getGstarHashTagListTotalCount(GridParam gridParam){
		
		return repository.getGstarHashTagListTotalCount(gridParam);
	}
	*/
	public GstarHashTag getGstarHashTag(Long tagId){
		return repository.findOne(tagId);
	}

	
	public void deleteGstarHashTag(Long tagId){
		repository.delete(tagId);
	}

}
//end of GstarHashTagService.java