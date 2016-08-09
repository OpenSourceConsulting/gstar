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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "gstar_contents_warn")
@IdClass(GstarContentsWarnPK.class)
public class GstarContentsWarn {

	@Id
	@Column(name = "gstar_user_id")
	private Long gstarUserId;//
	
	@Id
	@Column(name = "gstar_contents_id")
	private Long gstarContentsId;//
	
	@Column(name = "warn_type_cd")
	private String warnTypeCd;
	
	@Column(name = "warn_dt")
	private Date warnDt;//
	
	@Column(name = "warn_memo")
	private String warnMemo;//

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarContentsWarn() {
		
	}

	public GstarContentsWarn(Long gstarUserId, Long gstarContentsId, String warnMemo, String warnTypeCd) {
		this.gstarUserId = gstarUserId;
		this.gstarContentsId = gstarContentsId;
		this.warnMemo = warnMemo;
		this.warnTypeCd = warnTypeCd;
	}

	public String getWarnTypeCd() {
		return warnTypeCd;
	}

	public void setWarnTypeCd(String warnTypeCd) {
		this.warnTypeCd = warnTypeCd;
	}

	/**
	 * @return the gstarUserId
	 */
	public Long getGstarUserId() {
		return gstarUserId;
	}

	/**
	 * @param gstarUserId the gstarUserId to set
	 */
	public void setGstarUserId(Long gstarUserId) {
		this.gstarUserId = gstarUserId;
	}

	/**
	 * @return the gstarContentsId
	 */
	public Long getGstarContentsId() {
		return gstarContentsId;
	}

	/**
	 * @param gstarContentsId the gstarContentsId to set
	 */
	public void setGstarContentsId(Long gstarContentsId) {
		this.gstarContentsId = gstarContentsId;
	}

	/**
	 * @return the warnDt
	 */
	public Date getWarnDt() {
		return warnDt;
	}

	/**
	 * @param warnDt the warnDt to set
	 */
	public void setWarnDt(Date warnDt) {
		this.warnDt = warnDt;
	}

	/**
	 * @return the warnMemo
	 */
	public String getWarnMemo() {
		return warnMemo;
	}

	/**
	 * @param warnMemo the warnMemo to set
	 */
	public void setWarnMemo(String warnMemo) {
		this.warnMemo = warnMemo;
	}
	
	@PrePersist
	public void preInsert() {
		this.warnDt = new Date();
	}

}
//end of GstarContentsWarn.java