
package com.gemmystar.api.view.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.gemmystar.api.code.domain.CommonCodePK;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "gstar_view")
@IdClass(GstarViewPK.class)
public class GstarView implements Serializable{

	private static final long serialVersionUID = -1382292277139367158L;

	@Id
	@Column(name = "gstar_user_id")
	private Long gstarUserId;//
	
	@Id
	@Column(name = "gstar_contents_id")
	private Long gstarContentsId;//
	
	@Column(name = "view_cnt")
	private Long viewCnt;//조회 횟수

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarView() {
		
	}

	public GstarView(Long gstarUserId, Long gstarContentsId, Long viewCnt) {
		super();
		this.gstarUserId = gstarUserId;
		this.gstarContentsId = gstarContentsId;
		this.viewCnt = viewCnt;
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

	public Long getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(Long viewCnt) {
		this.viewCnt = viewCnt;
	}

}
//end of GstarLike.java