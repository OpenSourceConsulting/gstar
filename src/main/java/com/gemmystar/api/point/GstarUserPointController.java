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
@RequestMapping("/userpoint")
public class GstarUserPointController {
	
	@Autowired
	private GstarUserPointService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarUserPointController() {

	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse list(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, 
			@RequestParam(name = "pcStatusCd", required = false) String pcStatusCd){
	
		Long gstarUserId = WebUtil.getLoginUserId();
		
		Page<GstarUserPoint> list = service.getGstarUserPointList(pageable, gstarUserId, pcStatusCd);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	/**
	 * <pre>
	 * 포인트 구매정보 저장.
	 * </pre>
	 * @param jsonRes
	 * @param gstarPoint
	 * @param pgId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarPoint gstarPoint, @RequestParam(name = "pgId") String pgId){
		
		Long gstarUserId = WebUtil.getLoginUserId();
		
		GstarUserPoint gstarUserPoint = new GstarUserPoint(gstarUserId, gstarPoint, pgId);
		
		service.insertGstarUserPoint(gstarUserPoint);
		//jsonRes.setMsg(" 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	/**
	 * <pre>
	 * 환불요청
	 * </pre>
	 * @param jsonRes
	 * @param id
	 * @param cancelReason
	 * @return
	 */
	@RequestMapping(value="/reqcancel", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse requstCancel(SimpleJsonResponse jsonRes, @RequestParam(name = "id") Long id, @RequestParam(name = "cancelReason") String cancelReason){
		
		
		Long gstarUserId = WebUtil.getLoginUserId();
		
		service.requestCancel(id, gstarUserId, cancelReason);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/use", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse usePoint(SimpleJsonResponse jsonRes, @RequestParam(name = "gstarContentsId") Long gstarContentsId, Long[] pointIds, Integer[] points){
		
		
		Long gstarUserId = WebUtil.getLoginUserId();
		
		service.usePoint(gstarUserId, gstarContentsId, pointIds, points);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarUserPointId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarUserPointId") Long gstarUserPointId){
		
		service.deleteGstarUserPoint(gstarUserPointId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}

}
//end of GstarUserPointController.java