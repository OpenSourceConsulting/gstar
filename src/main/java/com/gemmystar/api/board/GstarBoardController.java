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
@RequestMapping("/board")
public class GstarBoardController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarBoardController.class);
	
	@Autowired
	private GstarBoardService service;
	
	@Autowired
	private MessageSource messageSource;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarBoardController() {
		
	}
	
	@JsonView(JsView.BordList.class)
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	
		Page<GstarBoard> list = service.getGstarBoardList(pageable, search);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	
	@JsonView(JsView.BordAll.class)
	@RequestMapping(value="/{gstarBoardId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarBoard(SimpleJsonResponse jsonRes, @PathVariable("gstarBoardId") Integer gstarBoardId){
	
		jsonRes.setData(service.getGstarBoard(gstarBoardId));
		
		return jsonRes;
	}

}
//end of GstarBoardController.java