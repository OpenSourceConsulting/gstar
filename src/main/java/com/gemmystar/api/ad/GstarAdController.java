package com.gemmystar.api.ad;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gemmystar.api.ad.domain.GstarAd;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.common.util.FileUtil;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/ad")
public class GstarAdController implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarAdController.class);
	
	public static String IMG_URI;
	
	@Autowired
	private GstarAdService service;
	
	@Autowired
	private MessageSource messageSource;
	
	@Value("${gemmy.upload.img.loc}")
	private String uploadImgPath;
	
	@Value("${gemmy.img.uri}")
	private String imgUri;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarAdController() {
		// TODO Auto-generated constructor stub
	}
	
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable){
	
		Page<GstarAd> list = service.getCurrentAdList(pageable);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarAdId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarAd(SimpleJsonResponse jsonRes, @PathVariable("gstarAdId") Integer gstarAdId){
	
		jsonRes.setData(service.getGstarAd(gstarAdId));
		
		return jsonRes;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		
		Assert.notNull(this.imgUri);
		IMG_URI = this.imgUri;
	}

}
//end of GstarAdController.java