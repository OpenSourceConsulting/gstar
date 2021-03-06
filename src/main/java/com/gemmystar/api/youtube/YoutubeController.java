package com.gemmystar.api.youtube;

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
@RequestMapping("/youtube")
public class YoutubeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(YoutubeController.class);
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private YoutubeService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public YoutubeController() {

	}

	@RequestMapping(value="/upload", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse uploadVideo(SimpleJsonResponse jsonRes, @RequestParam("vFile") MultipartFile vFile, Locale locale) throws IOException {

		try {
			
			GstarContents contents = new GstarContents();
			Calendar cal = Calendar.getInstance();
			contents.setSubject("Test Upload via Java on " + cal.getTime());
			contents.setMemo("Video uploaded via YouTube Data API V3 using the Java library " + "on " + cal.getTime());
			
			String videoId = service.uploadVideo(vFile.getInputStream(), vFile.getSize(), contents);
			jsonRes.setData(videoId);
			
		} catch (IOException e) {
			LOGGER.error(e.toString(), e);
			jsonRes.setSuccess(false);
			jsonRes.setMsg(messageSource.getMessage("ex.msg.video.upload.fail", null, locale));
		}
		
		return jsonRes;
	}


}
// end of UserController.java