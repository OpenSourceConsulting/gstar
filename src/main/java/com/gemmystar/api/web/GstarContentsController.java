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
import com.gemmystar.api.domain.GstarContents;
import com.gemmystar.api.domain.GstarRoom;
import com.gemmystar.api.service.GstarContentsService;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/contents")
public class GstarContentsController {
	
	@Autowired
	private GstarContentsService service;


	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarContentsController() {
		
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse allList(GridJsonResponse jsonRes){
	
		List<GstarContents> list = service.getGstarContentsAllList();

		jsonRes.setTotal(list.size());
		jsonRes.setList(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarContents gstarContents){
		
		service.save(gstarContents);
		//jsonRes.setMsg(" 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarContentsId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarContentsId") Long gstarContentsId){
		
		service.deleteGstarContents(gstarContentsId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarContentsId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarContents(SimpleJsonResponse jsonRes, @PathVariable("gstarContentsId") Long gstarContentsId){
	
		GstarContents contents = service.getGstarContentsWithRoom(gstarContentsId);
		
		jsonRes.setData(contents);
		
		return jsonRes;
	}

}
//end of GstarContentsController.java