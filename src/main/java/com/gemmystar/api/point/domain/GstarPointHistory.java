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
 * BongJin Kwon		2016. 8. 10.		First Draft.
 */
package com.gemmystar.api.point.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "gstar_point_history")
public class GstarPointHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;//
	
	@Column(name = "gstar_user_id")
	private Long gstarUserId;//
	
	@Column(name = "gstar_contents_id")
	private Long gstarContentsId;//
	
	@Column(name = "gstar_point_id")
	private Long gstarPointId;//
	
	@Column(name = "use_point")
	private int usePoint;//사용포인트
	
	@Column(name = "use_price")
	private int usePrice;//총사용금액
	
	@Column(name = "use_dt")
	private java.util.Date useDt;//사용일시
	
	@ManyToOne
	@JoinColumn(name = "gstar_contents_id", insertable = false, updatable = false)
	private GstarContents gstarContents;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarPointHistory() {

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
	 * @return the gstarPointId
	 */
	public Long getGstarPointId() {
		return gstarPointId;
	}

	/**
	 * @param gstarPointId the gstarPointId to set
	 */
	public void setGstarPointId(Long gstarPointId) {
		this.gstarPointId = gstarPointId;
	}

	/**
	 * @return the usePoint
	 */
	public int getUsePoint() {
		return usePoint;
	}

	/**
	 * @param usePoint the usePoint to set
	 */
	public void setUsePoint(int usePoint) {
		this.usePoint = usePoint;
	}

	/**
	 * @return the usePrice
	 */
	public int getUsePrice() {
		return usePrice;
	}

	/**
	 * @param usePrice the usePrice to set
	 */
	public void setUsePrice(int usePrice) {
		this.usePrice = usePrice;
	}

	/**
	 * @return the useDt
	 */
	public java.util.Date getUseDt() {
		return useDt;
	}

	/**
	 * @param useDt the useDt to set
	 */
	public void setUseDt(java.util.Date useDt) {
		this.useDt = useDt;
	}

	public GstarContents getGstarContents() {
		return gstarContents;
	}

	public void setGstarContents(GstarContents gstarContents) {
		this.gstarContents = gstarContents;
	}

}
//end of GstarPointHistory.java