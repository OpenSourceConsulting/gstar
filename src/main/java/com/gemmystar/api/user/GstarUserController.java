package com.gemmystar.api.user;


import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemmystar.api.common.model.GridJsonResponse;
import com.gemmystar.api.common.model.SimpleJsonResponse;
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
	
	@RequestMapping(value="/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("userId") Long userId){
		
		service.deleteGstarUser(userId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarUserId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarUser(SimpleJsonResponse jsonRes, @PathVariable("userId") Long userId){
	
		jsonRes.setData(service.getGstarUser(userId));
		
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