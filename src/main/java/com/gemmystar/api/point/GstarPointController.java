package com.gemmystar.api.point;


import java.util.List;

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
import com.gemmystar.api.point.domain.GstarPoint;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/point")
public class GstarPointController {
	
	@Autowired
	private GstarPointService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarPointController() {
		
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse list(SimpleJsonResponse jsonRes, @PageableDefault Pageable pageable){
	
		Page<GstarPoint> list = service.getGstarPointList(pageable);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarPoint gstarPoint){
		
		service.save(gstarPoint);
		//jsonRes.setMsg(" 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarPointId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarPointId") Integer gstarPointId){
		
		service.deleteGstarPoint(gstarPointId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarPointId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarPoint(SimpleJsonResponse jsonRes, @PathVariable("gstarPointId") Integer gstarPointId){
	
		jsonRes.setData(service.getGstarPoint(gstarPointId));
		
		return jsonRes;
	}

}
//end of GstarPointController.java