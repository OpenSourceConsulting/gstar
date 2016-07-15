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
import com.gemmystar.api.domain.GstarContentsTags;
import com.gemmystar.api.service.GstarContentsTagsService;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/GstarContentsTags")
public class GstarContentsTagsController {
	
	@Autowired
	private GstarContentsTagsService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarContentsTagsController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value=""/list", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse list(ExtjsGridParam gridParam){
	
		GridJsonResponse jsonRes = new GridJsonResponse();
		jsonRes.setTotal(service.getGstarContentsTagsListTotalCount(gridParam));
		jsonRes.setList(service.getGstarContentsTagsList(gridParam));
		
		return jsonRes;
	}
	
	@RequestMapping(value=""/create")
	@ResponseBody
	public SimpleJsonResponse create(SimpleJsonResponse jsonRes, GstarContentsTags gstarContentsTags){
		
		service.insertGstarContentsTags(gstarContentsTags);
		jsonRes.setMsg("사용자가 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public SimpleJsonResponse update(SimpleJsonResponse jsonRes, GstarContentsTags gstarContentsTags){
		
		service.updateGstarContentsTags(gstarContentsTags);
		jsonRes.setMsg("사용자 정보가 정상적으로 수정되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, GstarContentsTags gstarContentsTags){
		
		service.deleteGstarContentsTags(gstarContentsTags);
		jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/getGstarContentsTags", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarContentsTags(SimpleJsonResponse jsonRes, GstarContentsTags gstarContentsTags){
	
		jsonRes.setData(service.getGstarContentsTags(gstarContentsTags));
		
		return jsonRes;
	}

}
//end of GstarContentsTagsController.java