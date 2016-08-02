package com.gemmystar.api.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.LocaleResolver;

import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.user.domain.GstarAccount;

/**
 * <pre>
 * 사용자 인증 컨트롤러.
 * </pre>
 * 
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private GstarAccountService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public AuthController() {

	}

	@RequestMapping("/onAfterLogout")
	@ResponseBody
	public SimpleJsonResponse logout(SimpleJsonResponse jsonRes, HttpSession session) {

		session.invalidate();

		jsonRes.setMsg("로그아웃 되었습니다.");

		return jsonRes;
	}

	@RequestMapping("/onAfterLogin")
	@ResponseBody
	public SimpleJsonResponse onAfterLogin(SimpleJsonResponse jsonRes) {

		GstarAccount loginUser = (GstarAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		loginUser.getGstarUser().getName();// lazy loading.
		
		jsonRes.setData(loginUser);
		//service.updateLastLogin(((GstarAccount) loginUser).getId());
		return jsonRes;
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@RequestMapping("/notLogin")
	@ResponseBody
	public SimpleJsonResponse notLogin(SimpleJsonResponse jsonRes, HttpServletResponse response, HttpServletRequest request) {

		jsonRes.setSuccess(false);
		jsonRes.setMsg(messageSource.getMessage("auth.not.login", null, localeResolver.resolveLocale(request)));
		jsonRes.setData("notLogin");

		return jsonRes;
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@RequestMapping("/accessDenied")
	@ResponseBody
	public SimpleJsonResponse accessDenied(SimpleJsonResponse jsonRes) {

		jsonRes.setSuccess(false);
		jsonRes.setMsg("해당 작업에 대한 권한이 없습니다. 관리자에게 문의하세요.");

		return jsonRes;
	}

	@RequestMapping("/loginFail")
	@ResponseBody
	public SimpleJsonResponse loginFail(HttpServletRequest request, SimpleJsonResponse jsonRes) {

		jsonRes.setSuccess(false);

		AuthenticationException ex = (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (ex instanceof AuthenticationServiceException) {
			jsonRes.setMsg(ex.toString());
			
		} else if (ex instanceof LockedException) {
			jsonRes.setMsg("비밀번호가 초기화 되었습니다. 전송된 비밀번호 재설정 메일을 통해서 비밀번호를 재설정해주세요.");
			
		} else {
			jsonRes.setMsg("login ID 또는 password 가 잘못되었습니다.");
		}

		return jsonRes;
	}

}
// end of UserController.java