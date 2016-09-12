package com.gemmystar.api.point;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemmystar.api.common.model.GridJsonResponse;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.point.domain.GstarPointHistory;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/GstarPointHistory")
public class GstarPointHistoryController {
	
	@Autowired
	private GstarPointHistoryService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarPointHistoryController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse allList(GridJsonResponse jsonRes){
	
		List<GstarPointHistory> list = service.getGstarPointHistoryAllList();

		jsonRes.setTotal(list.size());
		jsonRes.setList(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarPointHistory gstarPointHistory){
		
		service.insertGstarPointHistory(gstarPointHistory);
		//jsonRes.setMsg(" 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarPointHistoryId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarPointHistoryId") Integer gstarPointHistoryId){
		
		service.deleteGstarPointHistory(gstarPointHistoryId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarPointHistoryId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarPointHistory(SimpleJsonResponse jsonRes, @PathVariable("gstarPointHistoryId") Integer gstarPointHistoryId){
	
		jsonRes.setData(service.getGstarPointHistory(gstarPointHistoryId));
		
		return jsonRes;
	}

}
//end of GstarPointHistoryController.java