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
package com.gemmystar.api.contents.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Column(name = "victory_cnt")
	private short victoryCnt;//우승횟수
	
	@Column(name = "warn_cnt")
	private int warnCnt;//신고횟수
	
	@Column(name = "point_cnt")
	private Long pointCnt = 0L;//하트 수
	
	@Column(name = "view_cnt")
	private Long viewCnt = 0L;//조회수
	
	@Column(name = "update_dt")
	private java.util.Date updateDt;//업데이트 일시
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gstar_contents_id")
	@JsonIgnore
	private GstarContents gstarContents;

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

	public GstarContents getGstarContents() {
		return gstarContents;
	}

	public void setGstarContents(GstarContents gstarContents) {
		this.gstarContents = gstarContents;
	}

	public short getVictoryCnt() {
		return victoryCnt;
	}

	public void setVictoryCnt(short victoryCnt) {
		this.victoryCnt = victoryCnt;
	}

	public int getWarnCnt() {
		return warnCnt;
	}

	public void setWarnCnt(int warnCnt) {
		this.warnCnt = warnCnt;
	}

	public Long getPointCnt() {
		return pointCnt;
	}

	public void setPointCnt(Long pointCnt) {
		this.pointCnt = pointCnt;
	}

	public Long getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(Long viewCnt) {
		this.viewCnt = viewCnt;
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