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
package com.gemmystar.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "common_code")
@IdClass(CommonCodePK.class)
public class CommonCode implements Serializable {

	private static final long serialVersionUID = -123297111285564394L;

	@Id
	@Column(name = "group_cd")
	private String groupCd;//그룹코드
	
	@Column(name = "code")
	private String code;//코드
	
	@Column(name = "code_nm")
	private String codeNm;//코드명
	
	@Column(name = "order_seq")
	private short orderSeq;//정렬순서
	
	@Column(name = "cd_desc")
	private String cdDesc;//
	
	@Column(name = "hidden")
	private boolean hidden;//

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public CommonCode() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the groupCd
	 */
	public String getGroupCd() {
		return groupCd;
	}

	/**
	 * @param groupCd the groupCd to set
	 */
	public void setGroupCd(String groupCd) {
		this.groupCd = groupCd;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the codeNm
	 */
	public String getCodeNm() {
		return codeNm;
	}

	/**
	 * @param codeNm the codeNm to set
	 */
	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}

	/**
	 * @return the orderSeq
	 */
	public short getOrderSeq() {
		return orderSeq;
	}

	/**
	 * @param orderSeq the orderSeq to set
	 */
	public void setOrderSeq(short orderSeq) {
		this.orderSeq = orderSeq;
	}

	/**
	 * @return the cdDesc
	 */
	public String getCdDesc() {
		return cdDesc;
	}

	/**
	 * @param cdDesc the cdDesc to set
	 */
	public void setCdDesc(String cdDesc) {
		this.cdDesc = cdDesc;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
//end of CommonCode.java