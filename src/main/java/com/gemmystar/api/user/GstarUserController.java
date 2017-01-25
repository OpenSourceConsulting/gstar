package com.gemmystar.api.user;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import com.gemmystar.api.user.dao.UserDao;
import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.user.domain.GstarUser;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @author Ji-Woong Choi(jchoi@osci.kr) 
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class GstarUserController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private GstarUserService service;
	
	@Autowired
	private GstarAccountService accountService;
	
	@Autowired
	private UserDao userDao;
	
	@Value("${gemmy.upload.profile.location}")
	private String profileUploadPath;
	
	@Value("${gemmy.profile.uri}")
	private String profileUri;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GstarUserController.class);

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarUserController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/mybatis", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse myBatisTest(SimpleJsonResponse jsonRes){
		
		
		String now = userDao.getCurrentDateTime();
		
		if (now != null) {
			jsonRes.setSuccess(false);
			jsonRes.setMsg(now);
			
			return jsonRes;
		}
		
		
		return jsonRes;
	}
	
	
	
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse allList(GridJsonResponse jsonRes){
	
		List<GstarUser> list = service.getGstarUserAllList();

		jsonRes.setTotal(list.size());
		jsonRes.setList(list);
		
		return jsonRes;
	}
	
	/**
	 * <pre>
	 * 회원 가입.
	 * </pre>
	 * @param jsonRes
	 * @param user
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/join", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse join(SimpleJsonResponse jsonRes, GstarUser user, GstarAccount account, Locale locale){
		
		
		UserDetails gstarAccount = accountService.loadUserByUsername(account.getLoginId());
		
		if (gstarAccount != null) {
			jsonRes.setSuccess(false);
			jsonRes.setMsg(messageSource.getMessage("user.loginId.alreadyUse", new String[]{account.getLoginId()}, locale));
			
			return jsonRes;
		}
		
		service.join(user, account);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarUser user){
		
		service.save(user);
		//jsonRes.setMsg(" 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	
	/**
	 * 사용자 프로필 업로드를 수행한다. 해당 내용은 별도의 업로드 프로세스를 거친다.
	 * @param jsonRes
	 * @param profileImgFile
	 * @param locale
	 * @return
	 */
	@RequestMapping(value="profile", method = RequestMethod.POST)
	@ResponseBody 
	public SimpleJsonResponse profile(SimpleJsonResponse jsonRes, 
									  @RequestParam("profileImg") MultipartFile profileImgFile, Locale locale) {
		
		Long gstarUserId = WebUtil.getLoginUserId();
		
		try {
			String originName = profileImgFile.getOriginalFilename();
			String postfix = originName.substring(originName.lastIndexOf("."));
			
			File uploadedFile = new File(profileUploadPath + gstarUserId + postfix);
			profileImgFile.transferTo(uploadedFile);

			LOGGER.info("Upload File Info: " + uploadedFile.toString());
			
			// Update database
			userDao.updateProfileImageUrl(gstarUserId, profileUri + uploadedFile.getName());
		} catch (IOException e) {
			LOGGER.error(e.toString(), e);
			jsonRes.setSuccess(false);
			jsonRes.setMsg(messageSource.getMessage("ex.msg.img.upload.fail", null, locale));
		}
		return jsonRes;
	}


	@RequestMapping(value="/{gstarUserId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarUser(SimpleJsonResponse jsonRes, @PathVariable("gstarUserId") Long userId){
	
		jsonRes.setData(service.getGstarUser(userId));
		
		return jsonRes;
	}
	
	@RequestMapping(value="/my", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getMyInfo(SimpleJsonResponse jsonRes){
	
		GstarAccount account = (GstarAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		account.getGstarUser().getId();//lazy loading.
		
		jsonRes.setData(account.getGstarUser());
		
		return jsonRes;
	}
	
	@RequestMapping(value="/my", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse saveMyInfo(SimpleJsonResponse jsonRes, GstarUser gstarUser, Locale locale){
	
		GstarAccount account = (GstarAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		GstarUser dbUser = service.getGstarUser(account.getGstarUser().getId());
		
		dbUser.setName(gstarUser.getName());
		dbUser.setNickname(gstarUser.getNickname());
		dbUser.setEmail(gstarUser.getEmail());
		dbUser.setBankCmpyCd(gstarUser.getBankCmpyCd());
		dbUser.setBankAccount(gstarUser.getBankAccount());
		
		service.save(dbUser);
		
		account.setGstarUser(dbUser);
		
		return jsonRes;
	}
	
	/**
	 * <pre>
	 * 회원 탈퇴 처리.
	 * </pre>
	 * @param jsonRes
	 * @return
	 */
	@RequestMapping(value="/withdraw", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse withdraw(SimpleJsonResponse jsonRes, HttpServletRequest request, HttpServletResponse response){
	
		GstarAccount account = WebUtil.getLoginUser();
		
		service.widthdraw(account.getGstarUser());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		
		return jsonRes;
	}
	
	@RequestMapping(value="/settings", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse settings(SimpleJsonResponse jsonRes, 
			@RequestParam(name = "mobileNoti") boolean mobileNoti, 
			@RequestParam(name = "mobileAppVer") String mobileAppVer, 
			@RequestParam(name = "fcmToken") String fcmToken){
	
		Long gstarUserId = WebUtil.getLoginUserId();
		GstarUser dbUser = service.getGstarUser(gstarUserId);
		
		dbUser.setMobileNoti(mobileNoti);
		dbUser.setMobileAppVer(mobileAppVer);
		dbUser.setFcmToken(fcmToken);
		
		service.save(dbUser);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/locale", method = RequestMethod.PUT)
	@ResponseBody
	public SimpleJsonResponse chageLocale(SimpleJsonResponse jsonRes, Locale locale){
	
		jsonRes.setMsg(messageSource.getMessage("user.locale.change.success", null, locale));
		
		return jsonRes;
	}

}
//end of GstarUserController.java