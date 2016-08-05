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
 * BongJin Kwon		2016. 8. 5.		First Draft.
 */
package com.gemmystar.api.common.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
public class RefreshableRememberMeAuthenticationFilter extends
		RememberMeAuthenticationFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RefreshableRememberMeAuthenticationFilter.class);

	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param authenticationManager
	 * @param rememberMeServices
	 */
	public RefreshableRememberMeAuthenticationFilter(
			AuthenticationManager authenticationManager,
			RememberMeServices rememberMeServices) {
		super(authenticationManager, rememberMeServices);
		
	}

	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
		super.onSuccessfulAuthentication(request, response, authResult);
		
		TokenBasedRememberMeServices tokenRememberMeService = (TokenBasedRememberMeServices)getRememberMeServices();
		
		tokenRememberMeService.onLoginSuccess(request, response, authResult);
		LOGGER.debug("onSuccessfulAuthentication.");
	}
	
	

}
//end of RefreshableRememberMeAuthenticationFilter.java