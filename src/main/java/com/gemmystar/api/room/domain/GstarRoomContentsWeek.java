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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "gstar_room_contents_week")
public class GstarRoomContentsWeek {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;//
	
	@Column(name = "gstar_room_id")
	private Long gstarRoomId;//
	
	@Column(name = "gstar_contents_id")
	private Long gstarContentsId;//
	
	@Column(name = "battle_seq")
	private int battleSeq;//
	
	@Column(name = "point_cnt")
	private Long pointCnt = 0L;//하트수(좋아요수)
	
	@Column(name = "view_cnt")
	private Long viewCnt = 0L;//조회수

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarRoomContentsWeek() {
		
	}

	public GstarRoomContentsWeek(Long gstarRoomId, Long gstarContentsId,
			int battleSeq, Long pointCnt, Long viewCnt) {
		super();
		this.gstarRoomId = gstarRoomId;
		this.gstarContentsId = gstarContentsId;
		this.battleSeq = battleSeq;
		this.pointCnt = pointCnt;
		this.viewCnt = viewCnt;
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
	 * @return the pointCnt
	 */
	public Long getPointCnt() {
		return pointCnt;
	}

	/**
	 * @param pointCnt the pointCnt to set
	 */
	public void setPointCnt(Long pointCnt) {
		this.pointCnt = pointCnt;
	}

	/**
	 * @return the viewCnt
	 */
	public Long getViewCnt() {
		return viewCnt;
	}

	/**
	 * @param viewCnt the viewCnt to set
	 */
	public void setViewCnt(Long viewCnt) {
		this.viewCnt = viewCnt;
	}

}
//end of GstarRoomContentsWeek.java