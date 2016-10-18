package com.gemmystar.api.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gemmystar.api.common.model.SimpleJsonResponse;

@ControllerAdvice
class GlobalDefaultExceptionHandler {
    //public static final String DEFAULT_ERROR_VIEW = "error";
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
	
	@Autowired
	private MessageSource messageSource;

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public SimpleJsonResponse defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

    	/*
    	 * If the exception is annotated with @ResponseStatus rethrow it and let
         * the framework handle it - like the OrderNotFoundException example
         * at the start of this post.
         * AnnotationUtils is a Spring Framework utility class.
    	 */
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;

        LOGGER.error(e.getLocalizedMessage(), e);
        SimpleJsonResponse json = new SimpleJsonResponse();
        
        json.setSuccess(false);
        
        if (e instanceof AccountNotFoundException) {
        	
        	AccountNotFoundException ex = (AccountNotFoundException)e;
        	
        	json.setMsg(messageSource.getMessage("ex.msg.account.notFound", new Long[]{ex.getAccountId()}, ex.getLocale()));
        } else {
        	//json.setMsg(e.toString());
        	json.setMsg(e.getMessage());
        }
        
        return json;
        
    }
}