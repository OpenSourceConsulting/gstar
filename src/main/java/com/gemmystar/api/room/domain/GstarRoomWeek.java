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
 * BongJin Kwon		2016. 8. 22.		First Draft.
 */
package com.gemmystar.api.room.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "gstar_room_week")
public class GstarRoomWeek {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;//
	
	@Column(name = "gstar_room_id")
	private Long gstarRoomId;//
	
	@Column(name = "battle_seq")
	private int battleSeq;//
	
	@Column(name = "start_dt", updatable = false)
	private java.util.Date startDt;//
	
	@Column(name = "end_dt")
	private java.util.Date endDt;//

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarRoomWeek() {
		
	}

	public GstarRoomWeek(Long gstarRoomId, int battleSeq, Date startDt) {
		super();
		this.gstarRoomId = gstarRoomId;
		this.battleSeq = battleSeq;
		this.startDt = startDt;
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
	 * @return the gstarRoomId
	 */
	public Long getGstarRoomId() {
		return gstarRoomId;
	}

	/**
	 * @param gstarRoomId the gstarRoomId to set
	 */
	public void setGstarRoomId(Long gstarRoomId) {
		this.gstarRoomId = gstarRoomId;
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

}
//end of GstarRoomWeek.java