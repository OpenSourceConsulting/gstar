package com.gemmystar.api.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public GstarView getGstarLike(Long userId, Long contentsId){
		return repository.findOne(new GstarViewPK(userId, contentsId));
	}
	
	public void deleteGstarLike(Long userId, Long contentsId){
		repository.delete(new GstarViewPK(userId, contentsId));
	}

}
//end of GstarLikeService.java