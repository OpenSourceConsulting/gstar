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
 * BongJin Kwon		2016. 8. 8.		First Draft.
 */
package com.gemmystar.api.contents.domain;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
public class GstarContentsWarnPK implements Serializable {
	
	private static final long serialVersionUID = 6166392569042564849L;

	private Long gstarUserId;//
	
	private Long gstarContentsId;//

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarContentsWarnPK() {
	}
	
	public GstarContentsWarnPK(Long gstarUserId, Long gstarContentsId) {
		super();
		this.gstarUserId = gstarUserId;
		this.gstarContentsId = gstarContentsId;
	}


	public Long getGstarUserId() {
		return gstarUserId;
	}

	public void setGstarUserId(Long gstarUserId) {
		this.gstarUserId = gstarUserId;
	}

	public Long getGstarContentsId() {
		return gstarContentsId;
	}

	public void setGstarContentsId(Long gstarContentsId) {
		this.gstarContentsId = gstarContentsId;
	}

}
//end of GstarContentsWarnPK.java