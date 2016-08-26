package com.gemmystar.api.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.room.domain.GstarRoom;
import com.gemmystar.api.room.domain.GstarWeekBattle;
import com.gemmystar.api.room.domain.GstarWeekBattleRepository;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarWeekBattleService {

	@Autowired
	private GstarWeekBattleRepository repository;
	
	@Autowired
	private GstarRoomService roomService;
	
	public GstarWeekBattleService() {
		
	}
	
	public void insertGstarWeekBattle(GstarWeekBattle gstarWeekBattle){
		repository.save(gstarWeekBattle);
	}
	
	public List<GstarWeekBattle> getGstarWeekBattleAllList(){
		return repository.findAll();
	}
	
	public GstarWeekBattle getCurrentWeekBattle(){
		
		GstarWeekBattle weekBattle = repository.getCurrentWeekBattle();
		
		if (weekBattle != null) {
			List<GstarRoom> rooms = weekBattle.getGstarRooms();
			for (GstarRoom gstarRoom : rooms) {
				
				gstarRoom.setChallengerContentsList(roomService.getChallengerContentsList(gstarRoom.getId()));
			}
		}
		
		return weekBattle;
	}
	
	public GstarWeekBattle getGstarWeekBattle(Integer gstarWeekBattleId){
		return repository.findOne(gstarWeekBattleId);
	}
	
	/*
	public void updateGstarWeekBattle(GstarWeekBattle gstarWeekBattle){
		repository.updateGstarWeekBattle(gstarWeekBattle);
	}
	*/
	
	public void deleteGstarWeekBattle(Integer gstarWeekBattleId){
		repository.delete(gstarWeekBattleId);
	}

}
//end of GstarWeekBattleService.java