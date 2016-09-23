/**
 * 
 */
package com.gemmystar.api.board.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gemmystar.api.common.converter.JsView;
import com.gemmystar.api.common.converter.JsonDateSerializer;
import com.gemmystar.api.common.util.WebUtil;
import com.gemmystar.api.user.domain.GstarUser;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "gstar_board")
public class GstarBoard {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(JsView.BordList.class)
	private Integer id;//
	
	@Column(name = "border_type_cd", updatable = false)
	@JsonView(JsView.BordList.class)
	private String borderTypeCd = "1";//
	
	@Column(name = "subject")
	@JsonView(JsView.BordList.class)
	private String subject;//
	
	@Column(name = "contents")
	@JsonView(JsView.BordAll.class)
	private String contents;//
	
	@Column(name = "gstar_user_id", updatable = false)
	@JsonView(JsView.BordAll.class)
	private Long gstarUserId;//
	
	@Column(name = "create_dt", updatable = false)
	@JsonView(JsView.BordList.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	private java.util.Date createDt;//
	
	@OneToOne
	@JoinColumn(name = "gstar_user_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonView(JsView.BordList.class)
	private GstarUser writer;

	/**
	 * 
	 */
	public GstarBoard() {
	}
	
	public GstarBoard(Integer id, String subject, Long gstarUserId, Date createDt, GstarUser writer) {
		super();
		this.id = id;
		this.subject = subject;
		this.gstarUserId = gstarUserId;
		this.createDt = createDt;
		this.writer = writer;
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
	 * @return the borderTypeCd
	 */
	public String getBorderTypeCd() {
		return borderTypeCd;
	}

	/**
	 * @param borderTypeCd the borderTypeCd to set
	 */
	public void setBorderTypeCd(String borderTypeCd) {
		this.borderTypeCd = borderTypeCd;
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
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
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
	
	public GstarUser getWriter() {
		return writer;
	}

	public void setWriter(GstarUser writer) {
		this.writer = writer;
	}

	@PrePersist
	public void preInsert() {
		this.gstarUserId = WebUtil.getLoginUserId();
		this.createDt = new Date();
	}

}
