package com.gemmystar.api.board;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;
import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.board.domain.GstarBoard;
import com.gemmystar.api.common.converter.JsView;
import com.gemmystar.api.common.model.SimpleJsonResponse;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/board")
public class GstarBoardAdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarBoardAdminController.class);
	
	@Autowired
	private GstarBoardService service;
	
	@Autowired
	private MessageSource messageSource;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarBoardAdminController() {
		
	}
	
	@JsonView(JsView.BordList.class)
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	
		Page<GstarBoard> list = service.getGstarBoardList(pageable, search);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarBoard gstarBoard){
		
		gstarBoard.setBoardTypeCd(GemmyConstant.CODE_BOARD_TYPE_BOARD);
		service.save(gstarBoard);
		
		service.sendMessageToAllUser(gstarBoard.getSubject(), gstarBoard.getContents());
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarBoardId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarBoardId") Integer gstarBoardId){
		
		service.deleteGstarBoard(gstarBoardId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@JsonView(JsView.BordAll.class)
	@RequestMapping(value="/{gstarBoardId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarBoard(SimpleJsonResponse jsonRes, @PathVariable("gstarBoardId") Integer gstarBoardId){
	
		jsonRes.setData(service.getGstarBoard(gstarBoardId));
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarBoardId}/messaging", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse sendBoardMessage(SimpleJsonResponse jsonRes, @PathVariable("gstarBoardId") Integer gstarBoardId){
		
		GstarBoard gstarBoard = service.getGstarBoard(gstarBoardId);
	
		service.sendMessageToAllUser(gstarBoard.getSubject(), gstarBoard.getContents());
		
		return jsonRes;
	}

}
//end of GstarBoardController.java