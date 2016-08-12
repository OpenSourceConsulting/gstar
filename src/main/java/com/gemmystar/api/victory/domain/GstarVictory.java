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
 * BongJin Kwon		2016. 8. 11.		First Draft.
 */
package com.gemmystar.api.victory.domain;

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
@Table(name = "gstar_victory")
public class GstarVictory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;//
	
	@Column(name = "gstar_room_id")
	private Long gstarRoomId;
	
	@Column(name = "gstar_contents_id")
	private Long gstarContentsId;//
	
	@Column(name = "victory_dt")
	private java.util.Date victoryDt;//우승일자

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarVictory() {
	}

	public GstarVictory(Long gstarRoomId, Long gstarContentsId) {
		super();
		this.gstarRoomId = gstarRoomId;
		this.gstarContentsId = gstarContentsId;
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

	public Long getGstarRoomId() {
		return gstarRoomId;
	}

	public void setGstarRoomId(Long gstarRoomId) {
		this.gstarRoomId = gstarRoomId;
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
	 * @return the victoryDt
	 */
	public java.util.Date getVictoryDt() {
		return victoryDt;
	}

	/**
	 * @param victoryDt the victoryDt to set
	 */
	public void setVictoryDt(java.util.Date victoryDt) {
		this.victoryDt = victoryDt;
	}
	
	@PrePersist
	public void preInsert() {
		this.victoryDt = new Date();
	}

}
//end of GstarVictory.java