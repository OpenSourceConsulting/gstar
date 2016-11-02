package com.gemmystar.api.room;


import java.io.File;
import java.io.FileInputStream;
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

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.common.util.FileUtil;
import com.gemmystar.api.common.util.WebUtil;
import com.gemmystar.api.contents.GstarContentsService;
import com.gemmystar.api.contents.S3Service;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.room.domain.GstarRoom;
import com.gemmystar.api.youtube.YoutubeService;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/room")
public class GstarRoomController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GstarRoomController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private GstarRoomService service;
	
	@Autowired
	private GstarContentsService contentsService;
	
	@Autowired
	private YoutubeService youtubeService;
	
	@Value("${gemmy.upload.location}")
	private String uploadPath;
	
	@Autowired
	private S3Service s3Service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarRoomController() {
		
	}
	
	/**
	 * <pre>
	 * 최신 대결 리스트
	 * </pre>
	 * @param jsonRes
	 * @param pageable
	 * @param search
	 * @return
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse list(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, 
			String search, String tag, Integer tabMenuId){
	
		Page<GstarRoom> page = null;
		
		if (tabMenuId != null) {
			page = service.getGstarRoomListByTabMenu(pageable, tabMenuId);
		} else if (tag != null) {
			page = service.getGstarRoomListByTag(pageable, tag);
		} else {
			page = service.getGstarRoomList(pageable, search);
		}
		
		jsonRes.setData(page);
		
		return jsonRes;
	}
	
	/**
	 * <pre>
	 * 나의 대결 리스트
	 * </pre>
	 * @param jsonRes
	 * @param pageable
	 * @param search
	 * @return
	 */
	@RequestMapping(value="/my", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse myList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable){
		
		Long gstarUserId = WebUtil.getLoginUserId();
	
		Page<GstarRoom> page = service.getUserGstarRoomList(pageable, gstarUserId);

		jsonRes.setData(page);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/best", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse bestList(SimpleJsonResponse jsonRes, int size){
	
		Page<GstarRoom> page = service.getGstarRoomBestList(size);

		jsonRes.setData(page);
		
		return jsonRes;
	}
	
	/**
	 * <pre>
	 * 대결 동영상 업로드 / 도전하기
	 * - 업로드된 동영상 파일은 background job scheduler({@link com.gemmystar.api.contents.S3UploadScheduledTask} 가 S3 업로드함.
	 * </pre>
	 * @param jsonRes
	 * @param gstarRoom
	 * @param contents
	 * @param tags
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse saveWithContents(SimpleJsonResponse jsonRes, GstarRoom gstarRoom, GstarContents contents, String[] tags,
			@RequestParam("vFile") MultipartFile vFile, 
			@RequestParam(name = "thumbFile", required = false) MultipartFile thumbFile,
			@RequestParam(name = "rSubject", required = false) String rSubject, Locale locale){
		
		
		try{
			File uploadedFile = new File(uploadPath + vFile.getOriginalFilename());
			vFile.transferTo(uploadedFile);
			
			String videoId = youtubeService.uploadVideo(new FileInputStream(uploadedFile), vFile.getSize(), contents);
			jsonRes.setData(videoId);
			
			contents.setUrl(videoId);
			contents.setLocale(locale.getLanguage());
			
			gstarRoom.setSubject(rSubject);
			service.saveWithContents(gstarRoom, contents, thumbFile, tags);
			
			contentsService.backupForS3(uploadedFile, contents.getId(), videoId);
			
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
	
	@RequestMapping(value="/{gstarRoomId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarRoomId") Long gstarRoomId){
		
		service.deleteGstarRoom(gstarRoomId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarRoomId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarRoom(SimpleJsonResponse jsonRes, @PathVariable("gstarRoomId") Long gstarRoomId){
	
		GstarRoom room = service.getGstarRoomWithChallengers(gstarRoomId);
		
		jsonRes.setData(room);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarRoomId}/challenge", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse challenge(SimpleJsonResponse jsonRes, @PathVariable("gstarRoomId") Long gstarRoomId, 
			GstarContents contents, String[] tags,	
			@RequestParam(name = "s3key") String s3key, Locale locale){
		
		
		try {
		
			File downloadedFile = s3Service.download(s3key);
			
			String videoId = youtubeService.uploadVideo(new FileInputStream(downloadedFile), downloadedFile.length(), contents);
			jsonRes.setData(videoId);
			
			contents.setUrl(videoId);
			contents.setLocale(locale.getLanguage());
			
			
			service.saveChallenger(gstarRoomId, contents, tags);
			
			String newS3key = GemmyConstant.S3_KEY_PREFIX_VIDEO +  FileUtil.getS3FileName(downloadedFile, contents.getId(), videoId);
			
			s3Service.renameObject(s3key, newS3key);
			
			contents.setS3key(newS3key);
			contentsService.save(contents);
			
			downloadedFile.delete();
		
		} catch (IOException e) {
			LOGGER.error(e.toString(), e);
			jsonRes.setSuccess(false);
			jsonRes.setMsg("도전하기 실패. " + e.toString());
		}
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarRoomId}/best", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarRoomBest(SimpleJsonResponse jsonRes, @PathVariable("gstarRoomId") Long gstarRoomId,
			@PageableDefault(sort = { "gstarInfo.pointCnt", "createDt" }, direction = Direction.DESC, size = 5) Pageable pageable){
	
		Page<GstarContents> challengers = service.getChallengerContentsList(gstarRoomId, pageable);
		
		jsonRes.setData(challengers);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarRoomId}/latest", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarRoomLatest(SimpleJsonResponse jsonRes, @PathVariable("gstarRoomId") Long gstarRoomId,
			@PageableDefault(sort = { "createDt" }, direction = Direction.DESC, size = 5) Pageable pageable){
	
		Page<GstarContents> challengers = service.getChallengerContentsList(gstarRoomId, pageable);
		
		jsonRes.setData(challengers);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/top", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getBestTop(SimpleJsonResponse jsonRes){
	
		GstarRoom room = service.getBestTopGstarRoom();
		
		jsonRes.setData(room);
		
		return jsonRes;
	}

}
//end of GstarRoomController.java