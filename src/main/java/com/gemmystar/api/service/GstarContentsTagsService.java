package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.domain.GstarContentsTags;
import com.gemmystar.api.domain.GstarContentsTagsRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarContentsTagsService {

	@Autowired
	private GstarContentsTagsRepository repository;
	
	public GstarContentsTagsService() {
		
	}
	
	public void save(GstarContentsTags gstarContentsTags){
		repository.save(gstarContentsTags);
	}
	
	public List<GstarContentsTags> getGstarContentsTagsAllList(){
		return repository.findAll();
	}
	/*
	public int getGstarContentsTagsListTotalCount(GridParam gridParam){
		
		return repository.getGstarContentsTagsListTotalCount(gridParam);
	}
	*/
	public GstarContentsTags getGstarContentsTags(Long contentsTagId){
		return repository.findOne(contentsTagId);
	}

	public void deleteGstarContentsTags(Long tagId){
		repository.delete(tagId);
	}

}
//end of GstarContentsTagsService.java