package com.gemmystar.api.user;


import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.gemmystar.api.common.model.GridJsonResponse;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.common.util.WebUtil;
import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.user.domain.GstarUser;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
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

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarUserController() {
		// TODO Auto-generated constructor stub
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