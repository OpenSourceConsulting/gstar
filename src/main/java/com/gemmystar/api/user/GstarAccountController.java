package com.gemmystar.api.user;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemmystar.api.common.exception.AccountNotFoundException;
import com.gemmystar.api.common.model.GridJsonResponse;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.user.domain.GstarAccount;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/account")
public class GstarAccountController {
	
	@Autowired
	private GstarAccountService service;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
    private JavaMailSender mailSender;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarAccountController() {
		
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse allList(){
	
		List list = service.getGstarAccountAllList();
		
		GridJsonResponse jsonRes = new GridJsonResponse();
		jsonRes.setTotal(list.size());
		jsonRes.setList(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, GstarAccount gstarAccount){
		
		service.save(gstarAccount);
		jsonRes.setMsg("사용자가 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{accountId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("accountId") Long accountId){
		
		service.deleteGstarAccount(accountId);
		jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{accountId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarAccount(SimpleJsonResponse jsonRes, @PathVariable("accountId") Long accountId, Locale locale){
	
		GstarAccount account = service.getGstarAccount(accountId);
		
        if (account == null) {
            throw new AccountNotFoundException(accountId, locale);
        }
		
		jsonRes.setData(account);
		
		return jsonRes;
	}
	
	@RequestMapping(value = "/{accountId}/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public SimpleJsonResponse resetPassword(SimpleJsonResponse jsonRes, HttpServletRequest request, Locale locale,
    		@PathVariable("accountId") Long accountId, @RequestParam("email") String userEmail) {
		
		GstarAccount account = service.getGstarAccount(accountId);
		
        if (account == null) {
            throw new AccountNotFoundException(accountId, locale);
        }
        
        if (account.getGstarUser().getEmail().equals(userEmail) == false) {
        	jsonRes.setSuccess(false);
        	jsonRes.setMsg(messageSource.getMessage("account.email.not.reg", new String[]{userEmail}, locale));
        	
        	return jsonRes;
		}
        
        
        String token = service.createPasswordResetToken(accountId);
        
        mailSender.send(constructResetTokenEmail(getAppUrl(request), locale, accountId, token, userEmail));
        
        
        return jsonRes;
    }
	
	private SimpleMailMessage constructResetTokenEmail(final String appUrl, final Locale locale, Long accountId, String token, String toEmail) {
        final String url = appUrl + "/user/changePassword?id=" + accountId + "&token=" + token;
        final String message = messageSource.getMessage("account.email.resetPassword.msg", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, toEmail);
    }

    private SimpleMailMessage constructEmail(String subject, String body, String toEmail) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(toEmail);
        email.setFrom("idkbj@osci.kr");
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
//end of GstarAccountController.java