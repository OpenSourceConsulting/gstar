package com.gemmystar.api.point.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gemmystar.api.GemmyConstant;
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
	
	@Column(name = "gstar_user_point_id")
	private Long gstarUserPointId;//
	
	@Column(name = "use_point", updatable = false)
	private int usePoint;//사용포인트
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "use_dt", updatable = false)
	private java.util.Date useDt;//사용일시
	
	@Column(name = "status_cd")
	private String statusCd;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gstar_contents_id", insertable = false, updatable = false)
	private GstarContents gstarContents;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gstar_user_point_id", insertable = false, updatable = false)
	private GstarUserPoint gstarUserPoint;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarPointHistory() {
		
	}
	
	public GstarPointHistory(Long gstarUserId, Long gstarContentsId, Long gstarUserPointId, Integer usePoint) {
		this.gstarUserId = gstarUserId;
		this.gstarContentsId = gstarContentsId;
		this.gstarUserPointId = gstarUserPointId;
		this.usePoint = usePoint;
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
	 * @return the gstarUserPointId
	 */
	public Long getGstarUserPointId() {
		return gstarUserPointId;
	}

	/**
	 * @param gstarUserPointId the gstarUserPointId to set
	 */
	public void setGstarUserPointId(Long gstarUserPointId) {
		this.gstarUserPointId = gstarUserPointId;
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

	public GstarUserPoint getGstarUserPoint() {
		return gstarUserPoint;
	}

	public void setGstarUserPoint(GstarUserPoint gstarUserPoint) {
		this.gstarUserPoint = gstarUserPoint;
	}
	
	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	@PrePersist
	public void preInsert() {
		
		this.statusCd = GemmyConstant.CODE_POINT_HS_STATUS_NORMAL;
		this.useDt = new Date();
	}

}
//end of GstarPointHistory.java