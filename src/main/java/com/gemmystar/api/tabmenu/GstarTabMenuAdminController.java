package com.gemmystar.api.tabmenu;


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
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/admin/tabmenu")
public class GstarTabMenuAdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarTabMenuAdminController.class);
	
	@Autowired
	private GstarTabMenuService service;
	
	@Autowired
	private MessageSource messageSource;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarTabMenuAdminController() {

	}

	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	
		Page<GstarTabMenu> list = service.getGstarTabMenuList(pageable, search);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarTabMenu gstarTabMenu){
		
		service.save(gstarTabMenu);
		//jsonRes.setMsg(messageSource.getMessage("account.email.not.reg", new String[]{userEmail}, locale));
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarTabMenuId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarTabMenuId") Integer gstarTabMenuId){
		
		service.deleteGstarTabMenu(gstarTabMenuId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarTabMenuId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarTabMenu(SimpleJsonResponse jsonRes, @PathVariable("gstarTabMenuId") Integer gstarTabMenuId){
	
		jsonRes.setData(service.getGstarTabMenu(gstarTabMenuId));
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarTabMenuId}", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse visibility(SimpleJsonResponse jsonRes, @PathVariable("gstarTabMenuId") Integer gstarTabMenuId, 
			@RequestParam(name = "hidden") boolean hidden){
		
		GstarTabMenu tabMenu = service.getGstarTabMenu(gstarTabMenuId);
		tabMenu.setHidden(hidden);
		
		service.save(tabMenu);
		
		return jsonRes;
	}

}
//end of GstarTabMenuController.java