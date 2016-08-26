package com.gemmystar.api.room;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemmystar.api.common.model.GridJsonResponse;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.room.domain.GstarWeekBattle;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/weekbattle")
public class GstarWeekBattleController {
	
	@Autowired
	private GstarWeekBattleService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarWeekBattleController() {
		
	}
	
	/**
	 * <pre>
	 * 현재 진행중인 주간배틀.
	 * </pre>
	 * @param jsonRes
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getCurrentWeekBattle(SimpleJsonResponse jsonRes){
	
		jsonRes.setData(service.getCurrentWeekBattle());
		
		return jsonRes;
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse allList(GridJsonResponse jsonRes){
	
		List<GstarWeekBattle> list = service.getGstarWeekBattleAllList();

		jsonRes.setTotal(list.size());
		jsonRes.setList(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarWeekBattle gstarWeekBattle){
		
		service.insertGstarWeekBattle(gstarWeekBattle);
		//jsonRes.setMsg(" 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarWeekBattleId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarWeekBattleId") Integer gstarWeekBattleId){
		
		service.deleteGstarWeekBattle(gstarWeekBattleId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarWeekBattleId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarWeekBattle(SimpleJsonResponse jsonRes, @PathVariable("gstarWeekBattleId") Integer gstarWeekBattleId){
	
		jsonRes.setData(service.getGstarWeekBattle(gstarWeekBattleId));
		
		return jsonRes;
	}

}
//end of GstarWeekBattleController.java