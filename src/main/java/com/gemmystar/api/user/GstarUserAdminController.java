package com.gemmystar.api.user;


import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import com.gemmystar.api.user.viewmodel.GstarAdminUser;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class GstarUserAdminController {
	
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
	public GstarUserAdminController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/members", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getMemberList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	
		Page<GstarUser> list = service.getGstarMemberList(pageable, search);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/users", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getAdminList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	
		Page<GstarUser> list = service.getGstarAdminList(pageable, search);

		jsonRes.setData(list);
		
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
	@RequestMapping(value="/user", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarUser user, GstarAccount account, Locale locale, 
			@RequestParam(name = "roles") String[] roles) {
		
		
		UserDetails gstarAccount = accountService.loadUserByUsername(account.getLoginId());
		
		if (gstarAccount != null) {
			jsonRes.setSuccess(false);
			jsonRes.setMsg(messageSource.getMessage("user.loginId.alreadyUse", new String[]{account.getLoginId()}, locale));
			
			return jsonRes;
		}
		
		
		service.saveAdminUser(user, account, roles);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/user/{gstarUserId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse deleteAdminUser(SimpleJsonResponse jsonRes, @PathVariable("gstarUserId") Long userId){
		
		service.deleteAdminUser(userId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	

	@RequestMapping(value="/user/{gstarUserId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarAdminUser(SimpleJsonResponse jsonRes, @PathVariable("gstarUserId") Long userId){
	
		GstarUser user = service.getGstarUser(userId);
		
		jsonRes.setData(new GstarAdminUser(user));
		
		return jsonRes;
	}
	
	@RequestMapping(value="/member/{gstarUserId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarUser(SimpleJsonResponse jsonRes, @PathVariable("gstarUserId") Long userId){
	
		GstarUser user = service.getGstarUser(userId);
		
		jsonRes.setData(user);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/user/{gstarUserId}/accounts", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarUserAccounts(SimpleJsonResponse jsonRes, @PathVariable("gstarUserId") Long userId){
	
		jsonRes.setData(accountService.getGstarAccountList(userId));
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarAccountId}/roles", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse saveGstarUser(SimpleJsonResponse jsonRes, @PathVariable("gstarAccountId") Long accountId,
			@RequestParam(name = "roles") String[] roles){
	
		service.saveAccountRoles(accountId, roles);

		return jsonRes;
	}
	
	@RequestMapping(value="/member/{gstarUserId}/lock", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse lockGstarUser(SimpleJsonResponse jsonRes, @PathVariable("gstarUserId") Long userId){
	
		service.lockUser(userId);

		return jsonRes;
	}
	
	@RequestMapping(value="/member/{gstarUserId}/unlock", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse unlockGstarUser(SimpleJsonResponse jsonRes, @PathVariable("gstarUserId") Long userId){
	
		service.unlockUser(userId);

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