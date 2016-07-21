/* 
 * Copyright (C) 2012-2015 Open Source Consulting, Inc. All rights reserved by Open Source Consulting, Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * Revision History
 * Author			Date				Description
 * ---------------	----------------	------------
 * BongJin Kwon		2016. 7. 15.		First Draft.
 */
package com.gemmystar.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
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
			.antMatchers("/**").authenticated()

			//.antMatchers(HttpMethod.POST, "/user/**").access("hasRole('ROLE_USER_ADMIN')")
			//.antMatchers(HttpMethod.GET, "/user/**").access("hasRole('ROLE_USER_USER')")

			//.anyRequest()// other request
			//.permitAll()
			//.fullyAuthenticated()
			
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