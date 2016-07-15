package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.domain.GstarInfo;
import com.gemmystar.api.domain.GstarInfoRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarInfoService {

	@Autowired
	private GstarInfoRepository repository;
	
	public GstarInfoService() {
	}
	
	public void save(GstarInfo gstarInfo){
		repository.save(gstarInfo);
	}
	
	public List<GstarInfo> getGstarInfoAllList(){
		return repository.findAll();
	}
	/*
	public int getGstarInfoListTotalCount(GridParam gridParam){
		
		return repository.getGstarInfoListTotalCount(gridParam);
	}
	*/
	public GstarInfo getGstarInfo(Long infoId){
		return repository.findOne(infoId);
	}
	
	public void deleteGstarInfo(Long infoId){
		repository.delete(infoId);
	}

}
//end of GstarInfoService.java