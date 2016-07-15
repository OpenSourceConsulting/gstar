package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.athena.peacock.controller.web.common.model.ExtjsGridParam;
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
		// TODO Auto-generated constructor stub
	}
	
	public void insertGstarRoom(GstarRoom gstarRoom){
		repository.insertGstarRoom(gstarRoom);
	}
	
	public List<GstarRoom> getGstarRoomList(ExtjsGridParam gridParam){
		return repository.getGstarRoomList(gridParam);
	}
	
	public int getGstarRoomListTotalCount(ExtjsGridParam gridParam){
		
		return repository.getGstarRoomListTotalCount(gridParam);
	}
	
	public GstarRoom getGstarRoom(GstarRoom gstarRoom){
		return repository.getGstarRoom(gstarRoom);
	}
	
	public void updateGstarRoom(GstarRoom gstarRoom){
		repository.updateGstarRoom(gstarRoom);
	}
	
	public void deleteGstarRoom(GstarRoom gstarRoom){
		repository.deleteGstarRoom(gstarRoom);
	}

}
//end of GstarRoomService.java