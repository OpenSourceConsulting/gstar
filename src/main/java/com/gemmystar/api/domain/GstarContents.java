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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "gstar_contents")
public class GstarContents implements Serializable {

	private static final long serialVersionUID = -2873126313761745558L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;//
	
	@Column(name = "subject")
	private String subject;//제목
	
	@Column(name = "url")
	private String url;//컨텐츠URL(유투브ID)
	
	@Column(name = "memo")
	private String memo;//
	
	@Column(name = "gstar_room_id")
	private Long gstarRoomId;
	
	@Column(name = "gstar_category_id")
	private Integer gstarCategoryId;//
	
	@Column(name = "member_type_cd")
	private String memberTypeCd;
	
	@Column(name = "div_cd")
	private String divCd;
	
	@Column(name = "status_cd")
	private String statusCd;
	
	@Column(name = "create_dt")
	private java.util.Date createDt;//
	
	
	@ManyToOne
	@JoinColumn(name = "gstar_user_id")
	private GstarUser gstarUser;
	
	@Transient
	private GstarRoom gstarRoom;

	
	@OneToOne(mappedBy = "gstarContents")
	private GstarInfo gstarInfo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "gstar_contents_tags", joinColumns = @JoinColumn(name = "gstar_contents_id"), inverseJoinColumns = @JoinColumn(name = "gstar_hash_tag_id"))
	private List<GstarHashTag> tags;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarContents() {
		
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

	public GstarUser getGstarUser() {
		return gstarUser;
	}

	public void setGstarUser(GstarUser gstarUser) {
		this.gstarUser = gstarUser;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the gstarCategoryId
	 */
	public Integer getGstarCategoryId() {
		return gstarCategoryId;
	}

	/**
	 * @param gstarCategoryId the gstarCategoryId to set
	 */
	public void setGstarCategoryId(Integer gstarCategoryId) {
		this.gstarCategoryId = gstarCategoryId;
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

	public String getMemberTypeCd() {
		return memberTypeCd;
	}

	public void setMemberTypeCd(String memberTypeCd) {
		this.memberTypeCd = memberTypeCd;
	}

	public String getDivCd() {
		return divCd;
	}

	public void setDivCd(String divCd) {
		this.divCd = divCd;
	}

	public Long getGstarRoomId() {
		return gstarRoomId;
	}

	public void setGstarRoomId(Long gstarRoomId) {
		this.gstarRoomId = gstarRoomId;
	}

	public GstarRoom getGstarRoom() {
		return gstarRoom;
	}

	public void setGstarRoom(GstarRoom gstarRoom) {
		this.gstarRoom = gstarRoom;
	}

	public GstarInfo getGstarInfo() {
		return gstarInfo;
	}

	public void setGstarInfo(GstarInfo gstarInfo) {
		this.gstarInfo = gstarInfo;
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
	
	public List<GstarHashTag> getTags() {
		return tags;
	}

	public void setTags(List<GstarHashTag> tags) {
		this.tags = tags;
	}

	@PrePersist
	public void preInsert() {
		this.createDt = new Date();
	}

}
//end of GstarContents.java