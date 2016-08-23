package com.gemmystar.api.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.room.domain.GstarRoomContentsWeek;
import com.gemmystar.api.room.domain.GstarRoomContentsWeekRepository;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarRoomContentsWeekService {

	@Autowired
	private GstarRoomContentsWeekRepository repository;
	
	public GstarRoomContentsWeekService() {
		
	}
	
	public void save(GstarRoomContentsWeek gstarRoomContentsWeek){
		repository.save(gstarRoomContentsWeek);
	}
	
	public List<GstarRoomContentsWeek> getGstarRoomContentsWeekAllList(){
		return repository.findAll();
	}
	
	/*
	public int getGstarRoomContentsWeekListTotalCount(GridParam gridParam){
		
		return repository.getGstarRoomContentsWeekListTotalCount(gridParam);
	}
	*/
	
	public GstarRoomContentsWeek getGstarRoomContentsWeek(Long gstarRoomContentsWeekId){
		return repository.findOne(gstarRoomContentsWeekId);
	}
	
	/*
	public void updateGstarRoomContentsWeek(GstarRoomContentsWeek gstarRoomContentsWeek){
		repository.updateGstarRoomContentsWeek(gstarRoomContentsWeek);
	}
	*/
	
	public void deleteGstarRoomContentsWeek(Long gstarRoomContentsWeekId){
		repository.delete(gstarRoomContentsWeekId);
	}

}
//end of GstarRoomContentsWeekService.java