package com.gemmystar.api.board;


import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.ad.domain.GstarAd;
import com.gemmystar.api.board.domain.GstarBoard;
import com.gemmystar.api.common.converter.JsView;
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
@RequestMapping("/admin/event")
public class GstarEventAdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarEventAdminController.class);
	
	@Autowired
	private GstarBoardService service;
	
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
	public GstarEventAdminController() {
		
	}
	
	//@JsonView(JsView.BordList.class)
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	
		Page<GstarBoard> list = service.getGstarEventList(pageable, search);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarBoard gstarBoard, 
			@RequestParam(name="imgFile", required = false) MultipartFile imgFile, Locale locale) {
		
		
		
		GstarBoard dbBoard = null;
		if (gstarBoard.getId() != null && gstarBoard.getId() > 0) {
			dbBoard = service.getGstarBoard(gstarBoard.getId());
		}
		
		
		try {
			
			if (dbBoard != null && imgFile != null && imgFile.getSize() > 0) {
				/*
				 * When modify, delete old file.
				 */
				
				File uploadedFile = new File(uploadImgPath + dbBoard.getImgUrl());
				uploadedFile.delete();
			}
			
			if (imgFile != null && imgFile.getSize() > 0) {
				String savedFileName = getUniqueName(imgFile.getOriginalFilename());
				
				File uploadedFile = new File(uploadImgPath + savedFileName);
				imgFile.transferTo(uploadedFile);
				
				
				gstarBoard.setImgUrl(savedFileName);
			}
			
			
		} catch (IOException e) {
			LOGGER.error(e.toString(), e);
			jsonRes.setSuccess(false);
			jsonRes.setMsg(messageSource.getMessage("ex.msg.img.upload.fail", null, locale));
		}
		
		
		service.save(gstarBoard);
		
		
		return jsonRes;
	}
	
	private String getUniqueName(String fileName) {
		
		return "event" + File.separator + System.currentTimeMillis() + "_" + fileName;
		
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
	public SimpleJsonResponse sendEventMessage(SimpleJsonResponse jsonRes, @PathVariable("gstarBoardId") Integer gstarBoardId){
		
		GstarBoard gstarBoard = service.getGstarBoard(gstarBoardId);
	
		service.sendMessageToAllUser(gstarBoard.getSubject(), gstarBoard.getContents());
		
		return jsonRes;
	}
	

}
//end of GstarBoardController.java