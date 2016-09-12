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
 * BongJin Kwon		2016. 8. 19.		First Draft.
 */
package com.gemmystar.api.room.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.gemmystar.api.contents.domain.GstarContents;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "gstar_room_contents")
public class GstarRoomContents {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;//
	
	@Column(name = "gstar_room_id")
	private Long gstarRoomId;//
	
	@Column(name = "gstar_contents_id")
	private Long gstarContentsId;//
	
	@Column(name = "start_dt", updatable = false)
	private java.util.Date startDt;//배틀 참여시작일시
	
	@Column(name = "end_dt")
	private java.util.Date endDt;//배틀 참여종료일시
	
	@ManyToOne
	@JoinColumn(name = "gstar_room_id", insertable = false, updatable = false)
	private GstarRoom gstarRoom;
	
	@ManyToOne
	@JoinColumn(name = "gstar_contents_id", insertable = false, updatable = false)
	private GstarContents gstarContents;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarRoomContents() {
		
	}

	public GstarRoomContents(Long gstarRoomId, Long gstarContentsId) {
		super();
		this.gstarRoomId = gstarRoomId;
		this.gstarContentsId = gstarContentsId;
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
	
	public GstarRoom getGstarRoom() {
		return gstarRoom;
	}

	public void setGstarRoom(GstarRoom gstarRoom) {
		this.gstarRoom = gstarRoom;
	}

	public GstarContents getGstarContents() {
		return gstarContents;
	}

	public void setGstarContents(GstarContents gstarContents) {
		this.gstarContents = gstarContents;
	}

	@PrePersist
	public void preInsert() {
		this.startDt = new Date();
	}

}
//end of GstarRoomContents.java