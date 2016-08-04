package com.gemmystar.api;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

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
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public void addFormatters(FormatterRegistry registry) {

		// 'yyyy-MM-dd' format string to Date converting.
		//registry.addFormatter(new DateFormatter("yyyy-MM-dd"));

		//registry.removeConvertible(String.class, Number.class);// remove default converter.
		//registry.addConverterFactory(new StringToNumberConverterFactory());
	}
	
	/**
	 * <pre>
	 * refer http://stackoverflow.com/questions/16833893/how-to-serialize-lazy-loaded-entities-with-jackson-module-hibernate
	 * http://stackoverflow.com/questions/21708339/avoid-jackson-serialization-on-non-fetched-lazy-objects/21760361#21760361
	 * </pre>
	 * @return
	 */
	public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        Hibernate4Module hbm = new Hibernate4Module();
		//hbm.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);// default is false.
        hbm.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION); // default is true.
		
        mapper.registerModule(hbm);
		//mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        messageConverter.setObjectMapper(mapper);
        return messageConverter;

    }

	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		converters.add(jacksonMessageConverter());

	}
	
	
	@Bean
	public MultipartResolver multipartResolver() {

		//CommonsMultipartResolver resolver = new CommonsMultipartResolver();//spring boot 1.2*
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
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
        slr.setDefaultLocale(Locale.KOREA);
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
