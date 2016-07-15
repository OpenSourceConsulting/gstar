package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	}
	
	public void save(GstarLike gstarLike){
		repository.save(gstarLike);
	}
	
	public List<GstarLike> getGstarLikeAllList(){
		return repository.findAll();
	}
	/*
	public int getGstarLikeListTotalCount(GridParam gridParam){
		
		return repository.getGstarLikeListTotalCount(gridParam);
	}
	*/
	public GstarLike getGstarLike(Long likeId){
		return repository.findOne(likeId);
	}
	
	public void deleteGstarLike(Long likeId){
		repository.delete(likeId);
	}

}
//end of GstarLikeService.java