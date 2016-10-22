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
 * BongJin Kwon		2016. 8. 1.		First Draft.
 */
package com.gemmystar.api.common.exception;

import java.util.Locale;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
public class AccountNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -3809407621045665477L;
	
	private String loginId;
	private Locale locale;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public AccountNotFoundException(String loginId, String message) {
		super(message);
		this.loginId = loginId;
	}
	
	public AccountNotFoundException(String loginId, Locale locale) {
		this.loginId = loginId;
		this.locale = locale;
	}

	public String getLoginId() {
		return loginId;
	}

	public Locale getLocale() {
		return locale;
	}

	@Override
	public String getMessage() {
		if (super.getMessage() == null) {
			return this.loginId + " account is not found.";
		}
		
		return super.getMessage();
	}

}
//end of AccountNotFoundException.java