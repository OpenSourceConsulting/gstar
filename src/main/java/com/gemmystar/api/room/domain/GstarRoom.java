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
package com.gemmystar.api.room.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gemmystar.api.common.converter.JsonDateSerializer;
import com.gemmystar.api.contents.domain.GstarContents;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "gstar_room")
public class GstarRoom implements Serializable{

	private static final long serialVersionUID = -6678136169769344095L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;//
	
	@Column(name = "subject")
	private String subject;//방재목
	
	@Column(name = "master_contents_id")
	private Long masterContentsId;
	
	@Column(name = "view_sum")
	private Long viewSum = 0L;//
	
	@Column(name = "point_sum")
	private Long pointSum = 0L;//
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "create_dt")
	private java.util.Date createDt;//
	
	@Column(name = "battle_status_cd")
	private String battleStatusCd = "1";
	
	@Column(name = "battle_seq")
	private int battleSeq = 1;// 배틀 순차수
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "start_dt")
	private Date startDt;
	
	@OneToOne
	@JoinColumn(name = "master_contents_id", insertable = false, updatable = false)
	@JsonManagedReference(value = "masterCnts")
	private GstarContents masterContents;
	
	@Transient
	private List<GstarContents> challengerContentsList;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarRoom() {
		
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
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the createDt
	 */
	public java.util.Date getCreateDt() {
		return createDt;
	}

	/**
	 * @param createDt the createDt to set
	 */
	public void setCreateDt(java.util.Date createDt) {
		this.createDt = createDt;
	}
	
	public Long getMasterContentsId() {
		return masterContentsId;
	}

	public void setMasterContentsId(Long masterContentsId) {
		this.masterContentsId = masterContentsId;
	}

	public Long getViewSum() {
		return viewSum;
	}

	public void setViewSum(Long viewSum) {
		this.viewSum = viewSum;
	}

	public Long getPointSum() {
		return pointSum;
	}

	public void setPointSum(Long pointSum) {
		this.pointSum = pointSum;
	}

	public GstarContents getMasterContents() {
		return masterContents;
	}

	public void setMasterContents(GstarContents masterContents) {
		this.masterContents = masterContents;
	}

	public List<GstarContents> getChallengerContentsList() {
		return challengerContentsList;
	}

	public void setChallengerContentsList(List<GstarContents> challengerContentsList) {
		this.challengerContentsList = challengerContentsList;
	}

	public String getBattleStatusCd() {
		return battleStatusCd;
	}

	public void setBattleStatusCd(String battleStatusCd) {
		this.battleStatusCd = battleStatusCd;
	}

	public int getBattleSeq() {
		return battleSeq;
	}

	public void setBattleSeq(int battleSeq) {
		this.battleSeq = battleSeq;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	@PrePersist
	public void preInsert() {
		this.createDt = new Date();
		this.startDt = this.createDt;
	}

}
//end of GstarRoom.java