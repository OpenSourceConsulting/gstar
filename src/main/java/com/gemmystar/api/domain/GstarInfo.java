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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "gstar_info")
public class GstarInfo implements Serializable {

	private static final long serialVersionUID = 5002717021887938531L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;//
	
	@Column(name = "gstar_contents_id")
	private Long gstarContentsId;//컨텐츠ID
	
	@Column(name = "gs_victory")
	private short gsVictory;//우승횟수
	
	@Column(name = "gs_report")
	private int gsReport;//신고하기횟수
	
	@Column(name = "gs_like")
	private Long gsLike;//좋아요 Count
	
	@Column(name = "gs_check")
	private Object gsCheck;//false: 일반 도전모드 가능, true: 추천영상
	
	@Column(name = "gs_state_cd")
	private String gsStateCd;//상태코드 (1: 종료, 2:명예의전당, 3:중지, 4:HOT, 5:신규)
	
	@Column(name = "update_dt")
	private java.util.Date updateDt;//업데이트 일시

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarInfo() {
		
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the gsVictory
	 */
	public short getGsVictory() {
		return gsVictory;
	}

	/**
	 * @param gsVictory the gsVictory to set
	 */
	public void setGsVictory(short gsVictory) {
		this.gsVictory = gsVictory;
	}

	/**
	 * @return the gsReport
	 */
	public int getGsReport() {
		return gsReport;
	}

	/**
	 * @param gsReport the gsReport to set
	 */
	public void setGsReport(int gsReport) {
		this.gsReport = gsReport;
	}

	/**
	 * @return the gsLike
	 */
	public Long getGsLike() {
		return gsLike;
	}

	/**
	 * @param gsLike the gsLike to set
	 */
	public void setGsLike(Long gsLike) {
		this.gsLike = gsLike;
	}

	/**
	 * @return the gsCheck
	 */
	public Object getGsCheck() {
		return gsCheck;
	}

	/**
	 * @param gsCheck the gsCheck to set
	 */
	public void setGsCheck(Object gsCheck) {
		this.gsCheck = gsCheck;
	}

	/**
	 * @return the gsStateCd
	 */
	public String getGsStateCd() {
		return gsStateCd;
	}

	/**
	 * @param gsStateCd the gsStateCd to set
	 */
	public void setGsStateCd(String gsStateCd) {
		this.gsStateCd = gsStateCd;
	}

	/**
	 * @return the updateDt
	 */
	public java.util.Date getUpdateDt() {
		return updateDt;
	}

	/**
	 * @param updateDt the updateDt to set
	 */
	public void setUpdateDt(java.util.Date updateDt) {
		this.updateDt = updateDt;
	}
	
	@PrePersist
	@PreUpdate
	public void preSave(){
		this.updateDt = new Date();
	}

}
//end of GstarInfo.java