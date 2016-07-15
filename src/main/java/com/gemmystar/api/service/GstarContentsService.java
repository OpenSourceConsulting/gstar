package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.athena.peacock.controller.web.common.model.ExtjsGridParam;
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
		// TODO Auto-generated constructor stub
	}
	
	public void insertGstarContents(GstarContents gstarContents){
		repository.insertGstarContents(gstarContents);
	}
	
	public List<GstarContents> getGstarContentsList(ExtjsGridParam gridParam){
		return repository.getGstarContentsList(gridParam);
	}
	
	public int getGstarContentsListTotalCount(ExtjsGridParam gridParam){
		
		return repository.getGstarContentsListTotalCount(gridParam);
	}
	
	public GstarContents getGstarContents(GstarContents gstarContents){
		return repository.getGstarContents(gstarContents);
	}
	
	public void updateGstarContents(GstarContents gstarContents){
		repository.updateGstarContents(gstarContents);
	}
	
	public void deleteGstarContents(GstarContents gstarContents){
		repository.deleteGstarContents(gstarContents);
	}

}
//end of GstarContentsService.java