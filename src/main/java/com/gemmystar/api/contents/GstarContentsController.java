package com.gemmystar.api.contents;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
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

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.common.exception.ContentsNotFoundException;
import com.gemmystar.api.common.model.GridJsonResponse;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.common.util.WebUtil;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.contents.viewmodel.ContentsPointPrice;
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
	
	@Value("${gemmy.upload.location}")
	private String uploadPath;


	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarContentsController() {
		
	}
	
	/**
	 * 최신 영상 리스트를 조회하여 반환한다.
	 * @param jsonRes
	 * @param pageable
	 * @param search
	 * @return
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	
		Page<GstarContents> list = service.getGstarContentsList(pageable, search);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/recommands", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse recommandList(SimpleJsonResponse jsonRes){
	
		Iterable<GstarContents> list = service.getRecommandList(false);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/honors", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getHonoraryWinnerList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable){
	
		Page<GstarContents> list = service.getHonoraryWinnerList(pageable);

		jsonRes.setData(list);
		
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
	
	@RequestMapping(value="/myhearts", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse myHeartList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable){
	
		Long gstarUserId = WebUtil.getLoginUserId();
		
		Page<GstarContents> list = service.getUserHeartGstarContentsList(pageable, gstarUserId);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	/**
	 * 영상정보 등록 with 동영상 업로드
	 * - 업로드된 동영상 파일은 background job scheduler({@link com.gemmystar.api.contents.S3UploadScheduledTask} 가 S3 업로드함.
	 * @param jsonRes
	 * @param gstarContents
	 * @param tags
	 * @param vFile
	 * @param locale
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarContents gstarContents, String[] tags, 
			@RequestParam("vFile") MultipartFile vFile, 
			@RequestParam("thumbFile") MultipartFile thumbFile, Locale locale){
		
		try {
			
			File uploadedFile = new File(uploadPath + vFile.getOriginalFilename());
			vFile.transferTo(uploadedFile);
			
			String videoId = youtubeService.uploadVideo(new FileInputStream(uploadedFile), vFile.getSize(), gstarContents);
			jsonRes.setData(videoId);
			
			gstarContents.setUrl(videoId);
			gstarContents.setLocale(locale.getLanguage());
			service.save(gstarContents, thumbFile, tags);
			
			service.backupForS3(uploadedFile, gstarContents.getId(), videoId);
			
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
		
		GstarContents contents = service.getGstarContents(gstarContentsId);
		
		Long myUserId = WebUtil.getLoginUserId();
		
		if (myUserId.equals(contents.getGstarUser().getId()) == false) {
			//나의 영상이 아니면 에러.
			throw new RuntimeException("can not delete. it's not my contents.");
		}
		
		service.deleteGstarContents(contents);
		
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
	public SimpleJsonResponse increaseViewCnt(SimpleJsonResponse jsonRes, @RequestParam("gstarContentsId") Long gstarContentsId){
		
		GstarAccount account = WebUtil.getLoginUser();
	
		try{
			service.increaseViewCnt(gstarContentsId, account.getGstarUser().getId());
		}catch(ContentsNotFoundException e) {
			jsonRes.setSuccess(false);
			jsonRes.setMsg(e.toString());
		}

		
		return jsonRes;
	}
	
	/**
	 * 신고하기
	 * @param jsonRes
	 * @param gstarContentsId
	 * @param warnMemo
	 * @param warnTypeCd
	 * @return
	 */
	@RequestMapping(value="/{gstarContentsId}/warn", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse warnGstarContents(SimpleJsonResponse jsonRes, @PathVariable("gstarContentsId") Long gstarContentsId,
			@RequestParam(value = "warnMemo") String warnMemo, @RequestParam(value = "warnTypeCd") String warnTypeCd){
	
		GstarAccount account = WebUtil.getLoginUser();
		
		service.warnGstarContents(account.getGstarUser().getId(), gstarContentsId, warnMemo, warnTypeCd);
		
		return jsonRes;
	}
	
	/**
	 * 대결 포기 하기.
	 * @param jsonRes
	 * @param gstarContentsId
	 * @param gstarRoomId
	 * @return
	 */
	@RequestMapping(value="/{gstarContentsId}/giveup", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse giveupBattle(SimpleJsonResponse jsonRes, @PathVariable("gstarContentsId") Long gstarContentsId,
			@RequestParam(value = "gstarRoomId") Long gstarRoomId){
	
		GstarContents contents = service.getGstarContents(gstarContentsId);
		
		Long myUserId = WebUtil.getLoginUserId();
		
		if (myUserId.equals(contents.getGstarUser().getId()) == false) {
			//나의 영상이 아니면 에러.
			throw new RuntimeException("can not give up. it's not my contents.");
		}
		
		contents.setStatusCd(GemmyConstant.CODE_CNTS_STATUS_GIVEUP);
		
		service.save(contents);
		
		return jsonRes;
	}

}
//end of GstarContentsController.java