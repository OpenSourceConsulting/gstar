package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.athena.peacock.controller.web.common.model.ExtjsGridParam;
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
		// TODO Auto-generated constructor stub
	}
	
	public void insertGstarInfo(GstarInfo gstarInfo){
		repository.insertGstarInfo(gstarInfo);
	}
	
	public List<GstarInfo> getGstarInfoList(ExtjsGridParam gridParam){
		return repository.getGstarInfoList(gridParam);
	}
	
	public int getGstarInfoListTotalCount(ExtjsGridParam gridParam){
		
		return repository.getGstarInfoListTotalCount(gridParam);
	}
	
	public GstarInfo getGstarInfo(GstarInfo gstarInfo){
		return repository.getGstarInfo(gstarInfo);
	}
	
	public void updateGstarInfo(GstarInfo gstarInfo){
		repository.updateGstarInfo(gstarInfo);
	}
	
	public void deleteGstarInfo(GstarInfo gstarInfo){
		repository.deleteGstarInfo(gstarInfo);
	}

}
//end of GstarInfoService.java