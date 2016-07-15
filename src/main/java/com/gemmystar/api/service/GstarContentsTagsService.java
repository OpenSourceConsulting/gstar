package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.athena.peacock.controller.web.common.model.ExtjsGridParam;
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
		// TODO Auto-generated constructor stub
	}
	
	public void insertGstarContentsTags(GstarContentsTags gstarContentsTags){
		repository.insertGstarContentsTags(gstarContentsTags);
	}
	
	public List<GstarContentsTags> getGstarContentsTagsList(ExtjsGridParam gridParam){
		return repository.getGstarContentsTagsList(gridParam);
	}
	
	public int getGstarContentsTagsListTotalCount(ExtjsGridParam gridParam){
		
		return repository.getGstarContentsTagsListTotalCount(gridParam);
	}
	
	public GstarContentsTags getGstarContentsTags(GstarContentsTags gstarContentsTags){
		return repository.getGstarContentsTags(gstarContentsTags);
	}
	
	public void updateGstarContentsTags(GstarContentsTags gstarContentsTags){
		repository.updateGstarContentsTags(gstarContentsTags);
	}
	
	public void deleteGstarContentsTags(GstarContentsTags gstarContentsTags){
		repository.deleteGstarContentsTags(gstarContentsTags);
	}

}
//end of GstarContentsTagsService.java