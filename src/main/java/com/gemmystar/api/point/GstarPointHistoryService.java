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
		
	}
	
	public void save(GstarPointHistory gstarPointHistory){
		repository.save(gstarPointHistory);
	}
	
	public List<GstarPointHistory> getGstarPointHistoryAllList(){
		return repository.findAll();
	}
	
	public GstarPointHistory getGstarPointHistory(Integer gstarPointHistoryId){
		return repository.findOne(gstarPointHistoryId);
	}
	
	public void deleteGstarPointHistory(Integer gstarPointHistoryId){
		repository.delete(gstarPointHistoryId);
	}

}
//end of GstarPointHistoryService.java