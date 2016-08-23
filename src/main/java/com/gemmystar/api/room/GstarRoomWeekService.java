package com.gemmystar.api.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.room.domain.GstarRoomWeek;
import com.gemmystar.api.room.domain.GstarRoomWeekRepository;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarRoomWeekService {

	@Autowired
	private GstarRoomWeekRepository repository;
	
	public GstarRoomWeekService() {
		
	}
	
	public void save(GstarRoomWeek gstarRoomWeek){
		repository.save(gstarRoomWeek);
	}
	
	public List<GstarRoomWeek> getGstarRoomWeekAllList(){
		return repository.findAll();
	}
	
	/*
	public int getGstarRoomWeekListTotalCount(GridParam gridParam){
		
		return repository.getGstarRoomWeekListTotalCount(gridParam);
	}
	*/
	
	public GstarRoomWeek getGstarRoomWeek(Long gstarRoomWeekId){
		return repository.findOne(gstarRoomWeekId);
	}
	
	/*
	public void updateGstarRoomWeek(GstarRoomWeek gstarRoomWeek){
		repository.updateGstarRoomWeek(gstarRoomWeek);
	}
	*/
	
	public void deleteGstarRoomWeek(Long gstarRoomWeekId){
		repository.delete(gstarRoomWeekId);
	}

}
//end of GstarRoomWeekService.java