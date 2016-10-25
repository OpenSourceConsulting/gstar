package com.gemmystar.api.user;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemmystar.api.common.exception.AccountNotFoundException;
import com.gemmystar.api.common.mail.MailSender;
import com.gemmystar.api.common.model.GridJsonResponse;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.user.domain.GstarPassresetToken;

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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GstarAccountController.class);
	
	@Autowired
	private GstarAccountService service;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	@Qualifier("gemmyMailSender")
    private MailSender mailSender;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
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
            throw new AccountNotFoundException("" + accountId, locale);
        }
		
		jsonRes.setData(account);
		
		return jsonRes;
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public SimpleJsonResponse resetPassword(SimpleJsonResponse jsonRes, HttpServletRequest request, Locale locale,
    		@RequestParam("email") String userEmail) {
		
		GstarAccount account = service.getGstarAccountByLoginId(userEmail);
		
        if (account == null) {
            throw new AccountNotFoundException(userEmail, locale);
        }
        
        if (account.getGstarUser().getEmail().equals(userEmail) == false) {
        	jsonRes.setSuccess(false);
        	jsonRes.setMsg(messageSource.getMessage("account.email.not.reg", new String[]{userEmail}, locale));
        	
        	return jsonRes;
		}
        
        String token = service.createPasswordResetToken(account);
        
        sendMail(getAppUrl(request), locale, account.getId(), token, userEmail);
        LOGGER.info("email sended to {}", userEmail);
        
        return jsonRes;
    }
	
	protected void sendMail(final String appUrl, final Locale locale, Long accountId, String token, String toEmail) {
        final String url = appUrl + "/account/"+accountId+"/changePassword?token=" + token;
        final String message = messageSource.getMessage("account.email.resetPassword.msg", null, locale);
        mailSender.sendMail("Reset Password", message + " \r\n" + url, toEmail);
    }

    

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
    
    @RequestMapping(value = "/{accountId}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(HttpServletRequest request, HttpServletResponse response, 
    		Locale locale, Model model, @PathVariable("accountId") Long accountId, @RequestParam("token") String token) throws Exception {
         
    	GstarPassresetToken passToken = service.getPasswordResetToken(token);
        GstarAccount user = null;
        
        if (passToken != null) {
        	user = passToken.getGstarAccount();
		}
        
        Calendar cal = Calendar.getInstance();
        
        String invalidMsg = null;
        if (passToken == null || user.getId() != accountId) {
        	invalidMsg = messageSource.getMessage("auth.invalidToken", null, locale);
            
        } else if ((passToken.getExpireDt().getTime() - cal.getTime().getTime()) <= 0) {
        	invalidMsg = messageSource.getMessage("auth.expiredToken", null, locale);
        }
     
        if (invalidMsg == null) {
        	//UserDetails userDetail = service.loadUserByUsername(user.getLoginId());
            
            Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPasswd(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
            
            
		} else {
			model.addAttribute("message", invalidMsg);
		}
     
        //return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
        return "ChangePassword";
    }
    
    @RequestMapping(value = "/save/password", method = RequestMethod.POST)
    @ResponseBody
    public SimpleJsonResponse savePassword(SimpleJsonResponse jsonRes, Locale locale, @RequestParam("password") String password, HttpSession session) {
        GstarAccount user = (GstarAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        service.changeUserPassword(user, password);
        
        jsonRes.setMsg(messageSource.getMessage("message.update.success.password", null, locale));
        
        session.invalidate();
        
        return jsonRes;
    }

}
//end of GstarAccountController.java