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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemmystar.api.common.model.GridJsonResponse;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.common.util.WebUtil;
import com.gemmystar.api.point.domain.GstarPoint;
import com.gemmystar.api.point.domain.GstarUserPoint;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/userpoint")
public class GstarUserPointAdminController {
	
	@Autowired
	private GstarUserPointService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarUserPointAdminController() {

	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse list(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, 
			@RequestParam(name = "pcStatusCd", required = false) String pcStatusCd){
	
		Page<GstarUserPoint> list = service.getGstarUserPointList(pageable, pcStatusCd);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarUserPointId}/cancel", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse cancelUserPoint(SimpleJsonResponse jsonRes, @PathVariable("gstarUserPointId") Long gstarUserPointId){
		
		service.cancel(gstarUserPointId);
		
		return jsonRes;
	}
	

}
//end of GstarUserPointController.java