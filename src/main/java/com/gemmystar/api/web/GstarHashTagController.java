package com.gemmystar.api.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.athena.peacock.controller.web.common.model.DtoJsonResponse;
import com.athena.peacock.controller.web.common.model.ExtjsGridParam;
import com.athena.peacock.controller.web.common.model.GridJsonResponse;
import com.athena.peacock.controller.web.common.model.SimpleJsonResponse;
import com.gemmystar.api.domain.GstarHashTag;
import com.gemmystar.api.service.GstarHashTagService;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/GstarHashTag")
public class GstarHashTagController {
	
	@Autowired
	private GstarHashTagService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarHashTagController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value=""/list", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse list(ExtjsGridParam gridParam){
	
		GridJsonResponse jsonRes = new GridJsonResponse();
		jsonRes.setTotal(service.getGstarHashTagListTotalCount(gridParam));
		jsonRes.setList(service.getGstarHashTagList(gridParam));
		
		return jsonRes;
	}
	
	@RequestMapping(value=""/create")
	@ResponseBody
	public SimpleJsonResponse create(SimpleJsonResponse jsonRes, GstarHashTag gstarHashTag){
		
		service.insertGstarHashTag(gstarHashTag);
		jsonRes.setMsg("사용자가 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public SimpleJsonResponse update(SimpleJsonResponse jsonRes, GstarHashTag gstarHashTag){
		
		service.updateGstarHashTag(gstarHashTag);
		jsonRes.setMsg("사용자 정보가 정상적으로 수정되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, GstarHashTag gstarHashTag){
		
		service.deleteGstarHashTag(gstarHashTag);
		jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/getGstarHashTag", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarHashTag(SimpleJsonResponse jsonRes, GstarHashTag gstarHashTag){
	
		jsonRes.setData(service.getGstarHashTag(gstarHashTag));
		
		return jsonRes;
	}

}
//end of GstarHashTagController.java