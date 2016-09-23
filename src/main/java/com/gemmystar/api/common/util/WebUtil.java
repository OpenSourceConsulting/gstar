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
 * BongJin Kwon		2016. 3. 25.		First Draft.
 */
package com.gemmystar.api.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gemmystar.api.user.domain.GstarAccount;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
public class WebUtil {

	public static GstarAccount getLoginUser() {
		return (GstarAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public static Long getLoginAccountId() {
		GstarAccount account = getLoginUser();
		return account.getId();
	}
	
	public static Long getLoginUserId() {
		GstarAccount account = getLoginUser();
		return account.getGstarUser().getId();
	}
	
	public static WebApplicationContext getWebApplicationContext(HttpServletRequest request) {
		return WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
	}
	
	public static boolean hasRole(String role) {
		
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority()))
                return true;
        }

        return false;
	}
	

}
//end of WebUtil.java