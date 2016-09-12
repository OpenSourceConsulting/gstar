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
 * BongJin Kwon		2016. 8. 24.		First Draft.
 */
package com.gemmystar.api.point.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.common.converter.JsonDateSerializer;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "gstar_user_point")
public class GstarUserPoint {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;//
	
	@Column(name = "point")
	private int point;//
	
	@Column(name = "gstar_user_id")
	private Long gstarUserId;//
	
	@Column(name = "price")
	private int price;//총구매가격
	
	@Column(name = "pg_id")
	private String pgId;//결제id
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "create_dt", updatable = false)
	private java.util.Date createDt;//구매일자
	
	@Column(name = "gstar_point_id")
	private int gstarPointId;//
	
	@Column(name = "pc_status_cd")
	private String pcStatusCd;//구매상태 (1: 정상구매, 2: 환불요청, 3: 환불완료)
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "cancel_dt")
	private java.util.Date cancelDt;//환불완료일자
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "cancel_req_dt")
	private java.util.Date cancelReqDt;//환불요청일자
	
	@Column(name = "cancel_reason")
	private String cancelReason;//환불사유
	
	@OneToMany(mappedBy = "gstarUserPoint", fetch = FetchType.EAGER)
	private List<GstarPointHistory> gstarPointHistories;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarUserPoint() {
		
	}
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarUserPoint(Long gstarUserId, GstarPoint gstarPoint, String pgId) {
		this.gstarPointId = gstarPoint.getId();
		this.point = gstarPoint.getPoint();
		this.price = gstarPoint.getPrice();
		this.pgId = pgId;
		
		this.gstarUserId = gstarUserId;
		this.pcStatusCd = GemmyConstant.CODE_PC_STATUS_OK;
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
	 * @return the point
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * @param point the point to set
	 */
	public void setPoint(int point) {
		this.point = point;
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
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the pgId
	 */
	public String getPgId() {
		return pgId;
	}

	/**
	 * @param pgId the pgId to set
	 */
	public void setPgId(String pgId) {
		this.pgId = pgId;
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

	/**
	 * @return the gstarPointId
	 */
	public int getGstarPointId() {
		return gstarPointId;
	}

	/**
	 * @param gstarPointId the gstarPointId to set
	 */
	public void setGstarPointId(int gstarPointId) {
		this.gstarPointId = gstarPointId;
	}

	/**
	 * @return the pcStatusCd
	 */
	public String getPcStatusCd() {
		return pcStatusCd;
	}

	/**
	 * @param pcStatusCd the pcStatusCd to set
	 */
	public void setPcStatusCd(String pcStatusCd) {
		this.pcStatusCd = pcStatusCd;
	}

	public java.util.Date getCancelDt() {
		return cancelDt;
	}

	public void setCancelDt(java.util.Date cancelDt) {
		this.cancelDt = cancelDt;
	}

	public java.util.Date getCancelReqDt() {
		return cancelReqDt;
	}

	public void setCancelReqDt(java.util.Date cancelReqDt) {
		this.cancelReqDt = cancelReqDt;
	}

	/**
	 * @return the cancelReason
	 */
	public String getCancelReason() {
		return cancelReason;
	}

	/**
	 * @param cancelReason the cancelReason to set
	 */
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	public List<GstarPointHistory> getGstarPointHistories() {
		return gstarPointHistories;
	}

	public void setGstarPointHistories(List<GstarPointHistory> gstarPointHistories) {
		this.gstarPointHistories = gstarPointHistories;
	}

	@PrePersist
	public void preInsert() {
		this.createDt = new Date();
	}

}
//end of GstarUserPoint.java