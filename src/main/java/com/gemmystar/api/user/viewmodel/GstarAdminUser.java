/**
 * 
 */
package com.gemmystar.api.user.viewmodel;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gemmystar.api.common.converter.JsonDateSerializer;
import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.user.domain.GstarUser;

/**
 * @author BongJin Kwon
 *
 */
public class GstarAdminUser {

	private Long id;//
	
	private String name;//
	
	private String email;//
	
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date createDt;
	
	private Long gstarAccountId;
	
	private String loginId;
	
	private Collection<? extends GrantedAuthority> authorities;

	public GstarAdminUser(GstarUser user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.createDt = user.getCreateDt();
		GstarAccount gstarAccount = user.getAccounts().get(0);
		
		this.gstarAccountId = gstarAccount.getId();
		this.loginId = gstarAccount.getLoginId();
		
		this.authorities = gstarAccount.getAuthorities();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Long getGstarAccountId() {
		return gstarAccountId;
	}

	public void setGstarAccountId(Long gstarAccountId) {
		this.gstarAccountId = gstarAccountId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
}
