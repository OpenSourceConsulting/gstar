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
 * BongJin Kwon		2016. 8. 23.		First Draft.
 */
package com.gemmystar.api.common.exception;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
public class ContentsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -252541681678132782L;
	private Long gstarContentsId;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public ContentsNotFoundException(Long gstarContentsId) {
		
		super("contents " + gstarContentsId + " is not found.");
		this.gstarContentsId = gstarContentsId;
	}

	public Long getGstarContentsId() {
		return gstarContentsId;
	}
	
}
//end of ContentsNotFoundException.java