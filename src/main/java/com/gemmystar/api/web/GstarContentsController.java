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
import com.gemmystar.api.domain.GstarContents;
import com.gemmystar.api.service.GstarContentsService;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/GstarContents")
public class GstarContentsController {
	
	@Autowired
	private GstarContentsService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarContentsController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value=""/list", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse list(ExtjsGridParam gridParam){
	
		GridJsonResponse jsonRes = new GridJsonResponse();
		jsonRes.setTotal(service.getGstarContentsListTotalCount(gridParam));
		jsonRes.setList(service.getGstarContentsList(gridParam));
		
		return jsonRes;
	}
	
	@RequestMapping(value=""/create")
	@ResponseBody
	public SimpleJsonResponse create(SimpleJsonResponse jsonRes, GstarContents gstarContents){
		
		service.insertGstarContents(gstarContents);
		jsonRes.setMsg("사용자가 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public SimpleJsonResponse update(SimpleJsonResponse jsonRes, GstarContents gstarContents){
		
		service.updateGstarContents(gstarContents);
		jsonRes.setMsg("사용자 정보가 정상적으로 수정되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, GstarContents gstarContents){
		
		service.deleteGstarContents(gstarContents);
		jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/getGstarContents", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarContents(SimpleJsonResponse jsonRes, GstarContents gstarContents){
	
		jsonRes.setData(service.getGstarContents(gstarContents));
		
		return jsonRes;
	}

}
//end of GstarContentsController.java