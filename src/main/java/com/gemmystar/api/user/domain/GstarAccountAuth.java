/**
 * 
 */
package com.gemmystar.api.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "gstar_account_auth")
public class GstarAccountAuth {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//
	
	@Column(name = "gstar_account_id")
	private Long gstarAccountId;//
	
	@Column(name = "authority")
	private String authority;//
	
	@ManyToOne
	@JoinColumn(name = "gstar_account_id", insertable = false, updatable = false)
	private GstarAccount gstarAccount;

	/**
	 * 
	 */
	public GstarAccountAuth() {
	}

	public GstarAccountAuth(Long gstarAccountId, String authority) {
		this.gstarAccountId = gstarAccountId;
		this.authority = authority;
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
	 * @return the gstarAccountId
	 */
	public Long getGstarAccountId() {
		return gstarAccountId;
	}

	/**
	 * @param gstarAccountId the gstarAccountId to set
	 */
	public void setGstarAccountId(Long gstarAccountId) {
		this.gstarAccountId = gstarAccountId;
	}

	/**
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
