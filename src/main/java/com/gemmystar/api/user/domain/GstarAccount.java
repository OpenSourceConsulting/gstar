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

import java.util.ArrayList;
import java.util.Collection;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "gstar_account")
public class GstarAccount implements UserDetails{

	private static final long serialVersionUID = -6369708557069341450L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;//
	
	@Column(name = "account_type_cd")
	private String accountTypeCd;//계정타입: (1:자체계정, 2: facbook계정, 3: 카카오계정)
	
	@Column(name = "login_id")
	private String loginId;//로그인ID : email or etc
	
	@Column(name = "passwd")
	private String passwd;//비밀번호
	
	@Column(name = "last_login_dt")
	private java.util.Date lastLoginDt;//마지막 로그인 일시
	
	@Column(name = "enabled")
	private boolean enabled;
	
	@Column(name = "locked")
	private boolean locked;
	
	@Column(name = "create_dt")
	private java.util.Date createDt;//
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="gstar_user_id")
	private GstarUser gstarUser;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "gstarAccount", cascade = CascadeType.REMOVE)// TODO 단방향 연결 삭제 테스트
	@JsonIgnore
	private List<GstarAccountAuth> accountAuths;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarAccount() {

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
	 * @return the accountTypeCd
	 */
	public String getAccountTypeCd() {
		return accountTypeCd;
	}

	/**
	 * @param accountTypeCd the accountTypeCd to set
	 */
	public void setAccountTypeCd(String accountTypeCd) {
		this.accountTypeCd = accountTypeCd;
	}

	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @return the lastLoginDt
	 */
	public java.util.Date getLastLoginDt() {
		return lastLoginDt;
	}

	/**
	 * @param lastLoginDt the lastLoginDt to set
	 */
	public void setLastLoginDt(java.util.Date lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
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

	public GstarUser getGstarUser() {
		return gstarUser;
	}

	public void setGstarUser(GstarUser gstarUser) {
		this.gstarUser = gstarUser;
	}

	public List<GstarAccountAuth> getAccountAuths() {
		return accountAuths;
	}

	public void setAccountAuths(List<GstarAccountAuth> accountAuths) {
		this.accountAuths = accountAuths;
	}

	@PrePersist
	public void preInsert() {
		
		if (this.accountTypeCd == null) {
			this.accountTypeCd = "1";
		}
		
		this.enabled = true;
		this.createDt = new Date();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		if (this.accountAuths != null) {
			for (GstarAccountAuth auth : this.accountAuths) {
				if (auth != null) {
					authorities.add(new SimpleGrantedAuthority(auth.getAuthority()));
				}
			}
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return getPasswd();
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		
		return getLoginId();
	}
	
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
//end of GstarAccount.java