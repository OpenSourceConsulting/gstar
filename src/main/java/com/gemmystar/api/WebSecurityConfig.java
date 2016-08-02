package com.gemmystar.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gemmystar.api.user.GstarAccountService;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties security;

	@Autowired
	private GstarAccountService userService;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/", 
				"/*.html", 
				"/resources/**", 

				
				"/auth/notLogin*", 
				"/auth/loginFail*",
				"/auth/accessDenied*", 
				"/auth/onAfterLogout*");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//.anonymous()
			//.disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.PUT, "/user/locale").permitAll()
			.antMatchers(HttpMethod.POST, "/user/join").permitAll()
			.antMatchers(HttpMethod.GET, "/account/*/changePassword").permitAll()

			.anyRequest()// other request
			.authenticated()
			
			.and().exceptionHandling().accessDeniedPage("/auth/accessDenied")
			.and().formLogin()
				.usernameParameter("loginId")
				.passwordParameter("passwd")
				.loginPage("/auth/notLogin")
				.loginProcessingUrl("/auth/login")
				.defaultSuccessUrl("/auth/onAfterLogin", true)
				.failureUrl("/auth/loginFail")
			.and().logout()
				.logoutUrl("/auth/logout")
				.logoutSuccessUrl("/auth/onAfterLogout")
				
			//remember me configuration
			.and().rememberMe(). 
                key("rem-me-key").
                rememberMeParameter("remember-me-gstar").
                rememberMeCookieName("gstar-remember-me").
                tokenValiditySeconds(86400)
			.and().csrf()
				.disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	/**
	 * <pre>
	 * configure user password encoder
	 * </pre>
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
		// return new BCryptPasswordEncoder();
	}

}
//end of SecurityConfig.java