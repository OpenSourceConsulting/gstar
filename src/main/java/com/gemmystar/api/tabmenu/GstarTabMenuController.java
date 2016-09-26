package com.gemmystar.api.tabmenu;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.tabmenu.domain.GstarTabMenu;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/tabmenu")
public class GstarTabMenuController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarTabMenuController.class);
	
	@Autowired
	private GstarTabMenuService service;
	
	@Autowired
	private MessageSource messageSource;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarTabMenuController() {
	}

	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getList(SimpleJsonResponse jsonRes){
	
		List<GstarTabMenu> list = service.getShownGstarTabMenuList();

		jsonRes.setData(list);
		
		return jsonRes;
	}
	

}
//end of GstarTabMenuController.java