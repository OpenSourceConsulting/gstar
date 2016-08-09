package com.gemmystar.api.contents;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
import org.springframework.web.multipart.MultipartFile;

import com.gemmystar.api.common.model.GridJsonResponse;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.common.util.WebUtil;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.youtube.YoutubeService;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/contents")
public class GstarContentsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GstarContentsController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private GstarContentsService service;
	
	@Autowired
	private YoutubeService youtubeService;


	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarContentsController() {
		
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse allList(GridJsonResponse jsonRes){
	
		List<GstarContents> list = service.getGstarContentsAllList();

		jsonRes.setTotal(list.size());
		jsonRes.setList(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/my", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse myList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable){
	
		Long gstarUserId = WebUtil.getLoginUserId();
		
		Page<GstarContents> list = service.getUserGstarContentsList(pageable, gstarUserId);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarContents gstarContents, String[] tags, 
			@RequestParam("vFile") MultipartFile vFile, Locale locale){
		
		try {
			String videoId = youtubeService.uploadVideo(vFile.getInputStream(), vFile.getSize(), gstarContents);
			jsonRes.setData(videoId);
			
			gstarContents.setUrl(videoId);
			gstarContents.setLocale(locale.getLanguage());
			service.save(gstarContents, tags);
		
		} catch (IOException e) {
			LOGGER.error(e.toString(), e);
			jsonRes.setSuccess(false);
			jsonRes.setMsg(messageSource.getMessage("ex.msg.video.upload.fail", null, locale));
		}
		
		
		return jsonRes;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String saveSuccess(){
		
		return "ContentsSuccess";
	}
	
	@RequestMapping(value="/{gstarContentsId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarContentsId") Long gstarContentsId){
		
		service.deleteGstarContents(gstarContentsId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarContentsId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarContents(SimpleJsonResponse jsonRes, @PathVariable("gstarContentsId") Long gstarContentsId){
	
		GstarContents contents = service.getGstarContents(gstarContentsId);
		
		jsonRes.setData(contents);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/view", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse increaseViewCnt(SimpleJsonResponse jsonRes, @RequestParam("gstarContentsId") Long gstarContentsId,
			@RequestParam(value = "gstarRoomId", required = false) Long gstarRoomId){
		
		GstarAccount account = WebUtil.getLoginUser();
	
		service.increaseViewCnt(gstarContentsId, account.getGstarUser().getId(), gstarRoomId);

		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarContentsId}/warn", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse warnGstarContents(SimpleJsonResponse jsonRes, @PathVariable("gstarContentsId") Long gstarContentsId,
			@RequestParam(value = "warnMemo") String warnMemo, @RequestParam(value = "warnTypeCd") String warnTypeCd){
	
		GstarAccount account = WebUtil.getLoginUser();
		
		service.warnGstarContents(account.getGstarUser().getId(), gstarContentsId, warnMemo, warnTypeCd);
		
		return jsonRes;
	}

}
//end of GstarContentsController.java