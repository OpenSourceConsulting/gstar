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
@RequestMapping("/admin/ad")
public class GstarAdAdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarAdAdminController.class);
	
	
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
	public GstarAdAdminController() {
		// TODO Auto-generated constructor stub
	}
	
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	
		Page<GstarAd> list = service.getGstarAdList(pageable, search);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarAd gstarAd, @RequestParam("imgFile") MultipartFile imgFile, Locale locale){
		
		
		GstarAd dbAd = null;
		if (gstarAd.getId() != null && gstarAd.getId() > 0) {
			dbAd = service.getGstarAd(gstarAd.getId());
			
			gstarAd.setViewCnt(dbAd.getViewCnt());
			gstarAd.setClickCnt(dbAd.getClickCnt());
		}
		
		
		try {
			
			if (dbAd != null && imgFile != null && imgFile.getSize() > 0) {
				/*
				 * When modify, delete old file.
				 */
				
				File uploadedFile = new File(uploadImgPath + dbAd.getImgUrl());
				uploadedFile.delete();
			}
			
			if (imgFile != null && imgFile.getSize() > 0) {
				String savedFileName = getUniqueName(imgFile.getOriginalFilename());
				
				File uploadedFile = new File(uploadImgPath + savedFileName);
				imgFile.transferTo(uploadedFile);
				
				
				gstarAd.setImgUrl(savedFileName);
			}
			
			
		} catch (IOException e) {
			LOGGER.error(e.toString(), e);
			jsonRes.setSuccess(false);
			jsonRes.setMsg(messageSource.getMessage("ex.msg.img.upload.fail", null, locale));
		}
		
		service.save(gstarAd);
		//jsonRes.setMsg(messageSource.getMessage("account.email.not.reg", new String[]{userEmail}, locale));
		
		
		return jsonRes;
	}
	
	private String getUniqueName(String fileName) {
		
		return System.currentTimeMillis() + "_" + fileName;
		
	}
	
	@RequestMapping(value="/{gstarAdId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarAdId") Integer gstarAdId){
		
		GstarAd dbAd = service.getGstarAd(gstarAdId);
		
		File uploadedFile = new File(uploadImgPath + dbAd.getImgUrl());
		uploadedFile.delete();
		
		service.deleteGstarAd(dbAd);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarAdId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarAd(SimpleJsonResponse jsonRes, @PathVariable("gstarAdId") Integer gstarAdId){
	
		jsonRes.setData(service.getGstarAd(gstarAdId));
		
		return jsonRes;
	}

}
//end of GstarAdController.java