package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.athena.peacock.controller.web.common.model.ExtjsGridParam;
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
		// TODO Auto-generated constructor stub
	}
	
	public void insertGstarHashTag(GstarHashTag gstarHashTag){
		repository.insertGstarHashTag(gstarHashTag);
	}
	
	public List<GstarHashTag> getGstarHashTagList(ExtjsGridParam gridParam){
		return repository.getGstarHashTagList(gridParam);
	}
	
	public int getGstarHashTagListTotalCount(ExtjsGridParam gridParam){
		
		return repository.getGstarHashTagListTotalCount(gridParam);
	}
	
	public GstarHashTag getGstarHashTag(GstarHashTag gstarHashTag){
		return repository.getGstarHashTag(gstarHashTag);
	}
	
	public void updateGstarHashTag(GstarHashTag gstarHashTag){
		repository.updateGstarHashTag(gstarHashTag);
	}
	
	public void deleteGstarHashTag(GstarHashTag gstarHashTag){
		repository.deleteGstarHashTag(gstarHashTag);
	}

}
//end of GstarHashTagService.java