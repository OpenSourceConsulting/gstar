package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.domain.GstarView;
import com.gemmystar.api.domain.GstarViewRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarViewService {

	@Autowired
	private GstarViewRepository repository;
	
	public GstarViewService() {

	}
	
	public void save(GstarView gstarLike){
		repository.save(gstarLike);
	}
	
	public List<GstarView> getGstarLikeAllList(){
		return repository.findAll();
	}
	/*
	public int getGstarLikeListTotalCount(GridParam gridParam){
		
		return repository.getGstarLikeListTotalCount(gridParam);
	}
	*/
	public GstarView getGstarLike(Long likeId){
		return repository.findOne(likeId);
	}
	
	public void deleteGstarLike(Long likeId){
		repository.delete(likeId);
	}

}
//end of GstarLikeService.java