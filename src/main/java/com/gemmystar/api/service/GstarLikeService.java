package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.athena.peacock.controller.web.common.model.ExtjsGridParam;
import com.gemmystar.api.domain.GstarLike;
import com.gemmystar.api.domain.GstarLikeRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarLikeService {

	@Autowired
	private GstarLikeRepository repository;
	
	public GstarLikeService() {
		// TODO Auto-generated constructor stub
	}
	
	public void insertGstarLike(GstarLike gstarLike){
		repository.insertGstarLike(gstarLike);
	}
	
	public List<GstarLike> getGstarLikeList(ExtjsGridParam gridParam){
		return repository.getGstarLikeList(gridParam);
	}
	
	public int getGstarLikeListTotalCount(ExtjsGridParam gridParam){
		
		return repository.getGstarLikeListTotalCount(gridParam);
	}
	
	public GstarLike getGstarLike(GstarLike gstarLike){
		return repository.getGstarLike(gstarLike);
	}
	
	public void updateGstarLike(GstarLike gstarLike){
		repository.updateGstarLike(gstarLike);
	}
	
	public void deleteGstarLike(GstarLike gstarLike){
		repository.deleteGstarLike(gstarLike);
	}

}
//end of GstarLikeService.java