package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.domain.GstarContents;
import com.gemmystar.api.domain.GstarContentsRepository;

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
	
	public GstarContentsService() {
		
	}
	
	public void save(GstarContents gstarContents){
		repository.save(gstarContents);
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