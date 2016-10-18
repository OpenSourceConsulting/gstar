package com.gemmystar.api.point;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.point.domain.GstarPointHistory;
import com.gemmystar.api.point.domain.GstarPointHistoryRepository;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarPointHistoryService {

	@Autowired
	private GstarPointHistoryRepository repository;
	
	public GstarPointHistoryService() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(GstarPointHistory gstarPointHistory){
		repository.save(gstarPointHistory);
	}
	
	public void saveList(List<GstarPointHistory> gstarPointHistories){
		repository.save(gstarPointHistories);
	}
	
	public List<GstarPointHistory> getGstarPointHistoryAllList(){
		return repository.findAll();
	}
	
	public List<GstarPointHistory> getGstarPointHistories(Long gstarContentsId, String statusCd) {
		return repository.findByGstarContentsIdAndStatusCd(gstarContentsId, statusCd);
	}
	
	
	public GstarPointHistory getGstarPointHistory(Integer gstarPointHistoryId){
		return repository.findOne(gstarPointHistoryId);
	}
	
	
	public void deleteGstarPointHistory(Integer gstarPointHistoryId){
		repository.delete(gstarPointHistoryId);
	}

}
//end of GstarPointHistoryService.java