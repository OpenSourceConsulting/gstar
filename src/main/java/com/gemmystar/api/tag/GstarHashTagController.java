package com.gemmystar.api.tag;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.contents.domain.GstarContents;

/**
 * <pre>
 * 사용자 인증 컨트롤러.
 * </pre>
 * 
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/hashtag")
public class GstarHashTagController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarHashTagController.class);
	

	@Autowired
	private GstarHashTagService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarHashTagController() {

	}

	@RequestMapping(value="/mains", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getMainTags(SimpleJsonResponse jsonRes) {

		jsonRes.setData(service.findMainTags());
		
		return jsonRes;
	}


}
// end of UserController.java