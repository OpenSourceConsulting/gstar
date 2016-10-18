/**
 * 
 */
package com.gemmystar.api.point.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.gemmystar.api.common.util.WebUtil;

/**
 * @author BongJin Kwon
 *
 */
@Entity
@Table(name = "gstar_point_payment")
public class GstarPointPayment {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//
	
	@Column(name = "gstar_user_id")
	private Long gstarUserId;//지급 대상 사용자ID
	
	@Column(name = "user_name")
	private String userName;//예금주명
	
	@Column(name = "gstar_contents_id")
	private Long gstarContentsId;//지금대상 컨텐츠id
	
	@Column(name = "point")
	private Integer point;//지급 쨈(포인트) 합계
	
	@Column(name = "deposit_ammount")
	private int depositAmmount;//입금액(지급액)
	
	@Column(name = "bank_name")
	private String bankName;//은행명
	
	@Column(name = "bank_account")
	private String bankAccount;//은행계좌
	
	@Column(name = "deposit_user_name")
	private String depositUserName;//입금자명
	
	@Column(name = "deposit_dt")
	private java.util.Date depositDt;//입금일시
	
	@Column(name = "create_dt", updatable = false)
	private java.util.Date createDt;//생성일시
	
	@Column(name = "create_user_id")
	private Long createUserId;//생성자ID

	/**
	 * 
	 */
	public GstarPointPayment() {
		// TODO Auto-generated constructor stub
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the point
	 */
	public Integer getPoint() {
		return point;
	}

	/**
	 * @param point the point to set
	 */
	public void setPoint(Integer point) {
		this.point = point;
	}

	/**
	 * @return the depositAmmount
	 */
	public int getDepositAmmount() {
		return depositAmmount;
	}

	/**
	 * @param depositAmmount the depositAmmount to set
	 */
	public void setDepositAmmount(int depositAmmount) {
		this.depositAmmount = depositAmmount;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	/**
	 * @return the depositUserName
	 */
	public String getDepositUserName() {
		return depositUserName;
	}

	/**
	 * @param depositUserName the depositUserName to set
	 */
	public void setDepositUserName(String depositUserName) {
		this.depositUserName = depositUserName;
	}

	/**
	 * @return the depositDt
	 */
	public java.util.Date getDepositDt() {
		return depositDt;
	}

	/**
	 * @param depositDt the depositDt to set
	 */
	public void setDepositDt(java.util.Date depositDt) {
		this.depositDt = depositDt;
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
	 * @return the createUserId
	 */
	public Long getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	@PrePersist
	public void preInsert() {
		
		this.createUserId = WebUtil.getLoginUserId();
		this.createDt = new Date();
	}

}
