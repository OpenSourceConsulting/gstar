package com.gemmystar.api.room;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemmystar.api.common.model.GridJsonResponse;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.contents.GstarContentsService;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.room.domain.GstarRoom;



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
	
	@Autowired
	private GstarContentsService contentsService;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarRoomController() {
		
	}
	
	/**
	 * <pre>
	 * 최신 대결 리스트
	 * </pre>
	 * @param jsonRes
	 * @param pageable
	 * @param search
	 * @return
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse list(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	
		Page<GstarRoom> page = service.getGstarRoomList(pageable, search);

		jsonRes.setData(page);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/best", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse bestList(SimpleJsonResponse jsonRes, int size){
	
		Page<GstarRoom> page = service.getGstarRoomBestList(size);

		jsonRes.setData(page);
		
		return jsonRes;
	}
	
	/**
	 * <pre>
	 * 나만의 동영상 업로드 / 도전하기
	 * </pre>
	 * @param jsonRes
	 * @param gstarRoom
	 * @param contents
	 * @param tags
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse saveWithContents(SimpleJsonResponse jsonRes, GstarRoom gstarRoom, GstarContents contents, String[] tags){
		
		service.saveWithContents(gstarRoom, contents, tags);
		
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
	
		GstarRoom room = service.getGstarRoomWithChallengers(gstarRoomId);
		
		jsonRes.setData(room);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/top", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getBestTop(SimpleJsonResponse jsonRes){
	
		GstarRoom room = service.getBestTopGstarRoom();
		
		jsonRes.setData(room);
		
		return jsonRes;
	}

}
//end of GstarRoomController.java