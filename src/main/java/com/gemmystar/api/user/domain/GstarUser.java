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
package com.gemmystar.api.user.domain;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gemmystar.api.common.converter.JsView;
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
@Table(name = "gstar_user")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //http://stackoverflow.com/questions/24994440/no-serializer-found-for-class-org-hibernate-proxy-pojo-javassist-javassist
public class GstarUser implements Serializable {

	private static final long serialVersionUID = 7382139896085707035L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;//
	
	@Column(name = "name")
	private String name;//사용자명(본명, 예금자명)
	
	@Column(name = "nickname")
	private String nickname;//별명
	
	@Column(name = "email")
	private String email;//
	
	@Column(name = "gender_cd")
	private String genderCd;//성별코드 ( 1: 남자, 2: 여자)
	
	@Column(name = "age")
	private short age;//
	
	@Column(name = "bank_cmpy_cd")
	private String bankCmpyCd;//은행사코드
	
	@Column(name = "bank_account")
	private String bankAccount;//은행계좌
	
	@Column(name = "fcm_token")
	private String fcmToken;
	
	@Column(name = "mobile_noti")
	private boolean mobileNoti;
	
	@Column(name = "email_noti")
	private boolean emailNoti;
	
	@Column(name = "mobile_app_ver")
	private String mobileAppVer;
	
	@Column(name = "profile_img_url")
	private String profileImgUrl;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "create_dt", updatable = false)
	private java.util.Date createDt;//생성일시
	
	@OneToMany(mappedBy = "gstarUser", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<GstarAccount> accounts;
	
	@OneToMany(mappedBy = "gstarUser", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<GstarContents> gstarContents;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarUser() {
	}
	
	public GstarUser(Long id) {
		super();
		this.id = id;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the genderCd
	 */
	public String getGenderCd() {
		return genderCd;
	}

	/**
	 * @param genderCd the genderCd to set
	 */
	public void setGenderCd(String genderCd) {
		this.genderCd = genderCd;
	}

	/**
	 * @return the age
	 */
	public short getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(short age) {
		this.age = age;
	}

	/**
	 * @return the bankCmpyCd
	 */
	public String getBankCmpyCd() {
		return bankCmpyCd;
	}

	/**
	 * @param bankCmpyCd the bankCmpyCd to set
	 */
	public void setBankCmpyCd(String bankCmpyCd) {
		this.bankCmpyCd = bankCmpyCd;
	}

	/**
	 * @return the bankAccount
	 */
	public String getBankAccount() {
		return bankAccount;
	}

	/**
	 * @param bankAccount the bankAccount to set
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public boolean isMobileNoti() {
		return mobileNoti;
	}

	public void setMobileNoti(boolean mobileNoti) {
		this.mobileNoti = mobileNoti;
	}

	public boolean isEmailNoti() {
		return emailNoti;
	}

	public void setEmailNoti(boolean emailNoti) {
		this.emailNoti = emailNoti;
	}

	public String getMobileAppVer() {
		return mobileAppVer;
	}
	
	public String getProfileImgUrl() {
		return profileImgUrl;
	}

	public void setMobileAppVer(String mobileAppVer) {
		this.mobileAppVer = mobileAppVer;
	}
	
	public void setProfileImgUrl(String profileImgUrl) {
		this.profileImgUrl = profileImgUrl;
	}

	public List<GstarContents> getGstarContents() {
		return gstarContents;
	}

	public void setGstarContents(List<GstarContents> gstarContents) {
		this.gstarContents = gstarContents;
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
	
	public List<GstarAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<GstarAccount> accounts) {
		this.accounts = accounts;
	}

	@PrePersist
	public void preInsert() {
		this.createDt = new Date();
	}

}
//end of GstarUser.java
