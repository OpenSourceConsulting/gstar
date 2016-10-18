package com.gemmystar.api.point;


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

import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.point.domain.GstarPointPayment;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/GstarPointPayment")
public class GstarPointPaymentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarPointPaymentController.class);
	
	@Autowired
	private GstarPointPaymentService service;
	
	@Autowired
	private MessageSource messageSource;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarPointPaymentController() {
		
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	
		Page<GstarPointPayment> list = service.getGstarPointPaymentList(pageable, search);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarPointPayment gstarPointPayment){
		
		service.save(gstarPointPayment);
		//jsonRes.setMsg(messageSource.getMessage("account.email.not.reg", new String[]{userEmail}, locale));
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarPointPaymentId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarPointPaymentId") Integer gstarPointPaymentId){
		
		service.deleteGstarPointPayment(gstarPointPaymentId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarPointPaymentId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarPointPayment(SimpleJsonResponse jsonRes, @PathVariable("gstarPointPaymentId") Integer gstarPointPaymentId){
	
		jsonRes.setData(service.getGstarPointPayment(gstarPointPaymentId));
		
		return jsonRes;
	}

}
//end of GstarPointPaymentController.java