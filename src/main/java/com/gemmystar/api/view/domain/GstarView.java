
package com.gemmystar.api.view.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "gstar_like")
public class GstarView implements Serializable{

	private static final long serialVersionUID = -1382292277139367158L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;//
	
	@Column(name = "gstar_user_id")
	private Long gstarUserId;//
	
	@Column(name = "gstar_contents_id")
	private Long gstarContentsId;//
	
	@Column(name = "like_cnt")
	private Long viewCnt;//조회 횟수

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarView() {
		
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