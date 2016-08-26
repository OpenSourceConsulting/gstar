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
 * BongJin Kwon		2016. 8. 26.		First Draft.
 */
package com.gemmystar.api.room.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.gemmystar.api.GemmyConstant;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "gstar_week_battle")
public class GstarWeekBattle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;//
	
	@Column(name = "tag")
	private String tag;//
	
	@Column(name = "status_cd")
	private String statusCd;//주간배틀상태(1:진행중, 2: 종료)
	
	@Column(name = "battle_seq")
	private int battleSeq;//배틀 주차수
	
	@Column(name = "start_dt")
	private java.util.Date startDt;//배틀 시작일시
	
	@Column(name = "end_dt")
	private java.util.Date endDt;//배틀 종료일시
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "gstar_week_battle_id")
	private List<GstarRoom> gstarRooms;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarWeekBattle() {
		
	}
	
	public GstarWeekBattle(String tag, Date startDt, Date endDt) {
		this.tag = tag;
		this.startDt = startDt;
		this.endDt = endDt;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return statusCd;
	}

	/**
	 * @param statusCd the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	/**
	 * @return the battleSeq
	 */
	public int getBattleSeq() {
		return battleSeq;
	}

	/**
	 * @param battleSeq the battleSeq to set
	 */
	public void setBattleSeq(int battleSeq) {
		this.battleSeq = battleSeq;
	}

	/**
	 * @return the startDt
	 */
	public java.util.Date getStartDt() {
		return startDt;
	}

	/**
	 * @param startDt the startDt to set
	 */
	public void setStartDt(java.util.Date startDt) {
		this.startDt = startDt;
	}

	/**
	 * @return the endDt
	 */
	public java.util.Date getEndDt() {
		return endDt;
	}

	/**
	 * @param endDt the endDt to set
	 */
	public void setEndDt(java.util.Date endDt) {
		this.endDt = endDt;
	}
	
	public List<GstarRoom> getGstarRooms() {
		return gstarRooms;
	}

	public void setGstarRooms(List<GstarRoom> gstarRooms) {
		this.gstarRooms = gstarRooms;
	}

	@PrePersist
	public void preInsert() {
		this.battleSeq = 1;
		this.statusCd = GemmyConstant.CODE_WEEK_BATTLE_STATUS_ING;
	}

}
//end of GstarWeekBattle.java