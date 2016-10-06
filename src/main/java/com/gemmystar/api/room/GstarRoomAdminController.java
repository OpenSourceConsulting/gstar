package com.gemmystar.api.room;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.common.util.WebUtil;
import com.gemmystar.api.contents.GstarContentsService;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.room.domain.GstarRoom;
import com.gemmystar.api.youtube.YoutubeService;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/room")
public class GstarRoomAdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GstarRoomAdminController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private GstarRoomService service;
	
	@Autowired
	private GstarContentsService contentsService;
	
	@Autowired
	private YoutubeService youtubeService;
	
	@Value("${gemmy.upload.location}")
	private String uploadPath;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarRoomAdminController() {
		
	}
	
	/**
	 * <pre>
	 * 최신 대결 리스트
	 * </pre>
	 * @param jsonRes
	 * @param pageable
	 * @param search
	 * @return
	 
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse list(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, 
			String search, String tag, Integer tabMenuId){
	
		Page<GstarRoom> page = null;
		
		if (tabMenuId != null) {
			page = service.getGstarRoomListByTabMenu(pageable, tabMenuId);
		} else if (tag != null) {
			page = service.getGstarRoomListByTag(pageable, tag);
		} else {
			page = service.getGstarRoomList(pageable, search);
		}
		
		jsonRes.setData(page);
		
		return jsonRes;
	}
	* 사용자 API 와 동일.
	*/
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String saveSuccess(){
		
		return "ContentsSuccess";
	}
	
	@RequestMapping(value="/{gstarRoomId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarRoomId") Long gstarRoomId){
		
		service.deleteGstarRoom(gstarRoomId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	

}
//end of GstarRoomController.java