package com.gemmystar.api.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemmystar.api.common.model.GridJsonResponse;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.domain.GstarRoom;
import com.gemmystar.api.service.GstarRoomService;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/room")
public class GstarRoomController {
	
	@Autowired
	private GstarRoomService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarRoomController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse allList(GridJsonResponse jsonRes){
	
		List<GstarRoom> list = service.getGstarRoomAllList();

		jsonRes.setTotal(list.size());
		jsonRes.setList(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarRoom gstarRoom){
		
		service.save(gstarRoom);
		//jsonRes.setMsg(" 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarRoomId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarRoomId") Long gstarRoomId){
		
		service.deleteGstarRoom(gstarRoomId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarRoomId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarRoom(SimpleJsonResponse jsonRes, @PathVariable("gstarRoomId") Long gstarRoomId){
	
		GstarRoom room = service.getGstarRoomWithContents(gstarRoomId);
		
		jsonRes.setData(room);
		
		return jsonRes;
	}

}
//end of GstarRoomController.java