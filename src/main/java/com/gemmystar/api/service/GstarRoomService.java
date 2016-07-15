package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.domain.GstarRoom;
import com.gemmystar.api.domain.GstarRoomRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarRoomService {

	@Autowired
	private GstarRoomRepository repository;
	
	public GstarRoomService() {
		
	}
	
	public void save(GstarRoom gstarRoom){
		repository.save(gstarRoom);
	}
	
	public List<GstarRoom> getGstarRoomAllList(){
		return repository.findAll();
	}
	/*
	public int getGstarRoomListTotalCount(GridParam gridParam){
		
		return repository.getGstarRoomListTotalCount(gridParam);
	}
	*/
	public GstarRoom getGstarRoom(Long roomId){
		return repository.findOne(roomId);
	}
	
	public void deleteGstarRoom(Long roomId){
		repository.delete(roomId);
	}

}
//end of GstarRoomService.java