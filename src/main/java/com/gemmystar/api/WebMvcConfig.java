package com.gemmystar.api;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Spring MVC Configuration
 * 
 * @author BongJin Kwon
 * 
 */
@Configuration
//@EnableAsync
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	//@Autowired
	//private JsonHttpMessageConverter jsonCustomConverter;

	@Override
	public void addFormatters(FormatterRegistry registry) {

		// 'yyyy-MM-dd' format string to Date converting.
		//registry.addFormatter(new DateFormatter("yyyy-MM-dd"));

		//registry.removeConvertible(String.class, Number.class);// remove default converter.
		//registry.addConverterFactory(new StringToNumberConverterFactory());
	}
	
	/*
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		jsonCustomConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		converters.add(jsonCustomConverter);
	}
	*/
	
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {

		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		return resolver;
	}
	
	@Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //WEB-INF 밑에 해당 폴더에서 properties를 찾는다.
        messageSource.setBasename("messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
	
	@Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }
 
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}
