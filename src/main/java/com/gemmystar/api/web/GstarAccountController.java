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
import com.gemmystar.api.domain.GstarAccount;
import com.gemmystar.api.service.GstarAccountService;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/GstarAccount")
public class GstarAccountController {
	
	@Autowired
	private GstarAccountService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarAccountController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value=""/list", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse list(ExtjsGridParam gridParam){
	
		GridJsonResponse jsonRes = new GridJsonResponse();
		jsonRes.setTotal(service.getGstarAccountListTotalCount(gridParam));
		jsonRes.setList(service.getGstarAccountList(gridParam));
		
		return jsonRes;
	}
	
	@RequestMapping(value=""/create")
	@ResponseBody
	public SimpleJsonResponse create(SimpleJsonResponse jsonRes, GstarAccount gstarAccount){
		
		service.insertGstarAccount(gstarAccount);
		jsonRes.setMsg("사용자가 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public SimpleJsonResponse update(SimpleJsonResponse jsonRes, GstarAccount gstarAccount){
		
		service.updateGstarAccount(gstarAccount);
		jsonRes.setMsg("사용자 정보가 정상적으로 수정되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, GstarAccount gstarAccount){
		
		service.deleteGstarAccount(gstarAccount);
		jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/getGstarAccount", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarAccount(SimpleJsonResponse jsonRes, GstarAccount gstarAccount){
	
		jsonRes.setData(service.getGstarAccount(gstarAccount));
		
		return jsonRes;
	}

}
//end of GstarAccountController.java