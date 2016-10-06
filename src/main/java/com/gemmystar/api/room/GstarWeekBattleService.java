package com.gemmystar.api.room;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.room.domain.GstarRoom;
import com.gemmystar.api.room.domain.GstarRoomContents;
import com.gemmystar.api.room.domain.GstarRoomContentsRepository;
import com.gemmystar.api.room.domain.GstarRoomWeek;
import com.gemmystar.api.room.domain.GstarRoomWeekRepository;
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
	
	private static final String[] WEEK_16TEAM_NAMES = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P"};

	@Autowired
	private GstarWeekBattleRepository repository;
	
	@Autowired
	private GstarRoomContentsRepository roomContentsRepo;
	
	@Autowired
	private GstarRoomWeekRepository roomWeekRepo;
	
	@Autowired
	private GstarRoomService roomService;
	
	public GstarWeekBattleService() {
		
	}
	
	public void save(GstarWeekBattle gstarWeekBattle){
		repository.save(gstarWeekBattle);
	}
	
	public List<GstarWeekBattle> getGstarWeekBattleAllList(){
		return repository.findAll();
	}
	
	public GstarWeekBattle getCurrentWeekBattleWithChallengers(){
		
		GstarWeekBattle weekBattle = repository.getCurrentWeekBattle();
		
		if (weekBattle != null) {
			List<GstarRoom> rooms = weekBattle.getGstarRooms();
			for (GstarRoom gstarRoom : rooms) {
				
				//TODO 사용시 다른 로직으로 변경.
				//gstarRoom.setChallengerContentsList(roomService.getChallengerContentsList(gstarRoom.getId()));
			}
		}
		
		return weekBattle;
	}
	
	public GstarWeekBattle getCurrentWeekBattle(){
		
		return repository.getCurrentWeekBattle();
	}
	
	public List<GstarContents> getWeekBattleWinners(Integer GstarWeekBattleId, int battleSeq){
		return repository.getWeekBattleWinners(GstarWeekBattleId, battleSeq);
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
	
	@Transactional
	public void createWeekBattleRooms(GstarWeekBattle weekBattle, int battleSeq, List<GstarContents> contentsList) {
		
		
		GstarContents c1 = null;
		GstarContents c2 = null;
		
		int teamIndex = 0;
		for (int i = 0; i < contentsList.size(); i++) {
			
			
			if (i == 0 || i % 2 == 0) {
				c1 = contentsList.get(i);
			} else {
				c2 = contentsList.get(i);
				
				createWeekBattleRooms(weekBattle.getId(), battleSeq, WEEK_16TEAM_NAMES[teamIndex], c1, c2);
				teamIndex++;
			}
		}
		
		weekBattle.setBattleSeq(battleSeq);
		save(weekBattle);
	}
	
	@Transactional
	public void createWeekBattleRooms(Integer weekBattleId, int battleSeq, String teamName, GstarContents c1, GstarContents c2) {
		
		GstarRoom room = new GstarRoom();
		room.setSubject(battleSeq + "주차 " + teamName + "조" );
		room.setMasterContentsId(c1.getId());
		room.setBattleStatusCd(GemmyConstant.CODE_BATTLE_STATUS_ING);
		room.setBattleSeq(battleSeq);
		room.setStartDt(new Date());
		room.setGstarWeekBattleId(weekBattleId);
		
		roomService.save(room);
		
		roomWeekRepo.save(new GstarRoomWeek(room.getId(), battleSeq, room.getStartDt()));
		
		roomContentsRepo.save(new GstarRoomContents(room.getId(), c1.getId()));
		roomContentsRepo.save(new GstarRoomContents(room.getId(), c2.getId()));
		
	}

}
//end of GstarWeekBattleService.java