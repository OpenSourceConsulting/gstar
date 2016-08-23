package com.gemmystar.api.contents;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.contents.domain.GstarInfo;
import com.gemmystar.api.contents.domain.GstarInfoRepository;

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
	
	public GstarInfo getGstarInfo(Long infoId){
		return repository.findOne(infoId);
	}
	
	public void deleteGstarInfo(Long infoId){
		repository.delete(infoId);
	}
	
	public void increaseViewCnt(Long gstarContentsId) {
		repository.increaseViewCnt(gstarContentsId, new Date());
	}

}
//end of GstarInfoService.java