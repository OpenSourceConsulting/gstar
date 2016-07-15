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
@RequestMapping("/account")
public class GstarAccountController {
	
	@Autowired
	private GstarAccountService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarAccountController() {
		
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse allList(){
	
		List list = service.getGstarAccountAllList();
		
		GridJsonResponse jsonRes = new GridJsonResponse();
		jsonRes.setTotal(list.size());
		jsonRes.setList(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarAccount gstarAccount){
		
		service.save(gstarAccount);
		jsonRes.setMsg("사용자가 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{accountId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("accountId") Long accountId){
		
		service.deleteGstarAccount(accountId);
		jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{accountId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarAccount(SimpleJsonResponse jsonRes, @PathVariable("accountId") Long accountId){
	
		jsonRes.setData(service.getGstarAccount(accountId));
		
		return jsonRes;
	}

}
//end of GstarAccountController.java