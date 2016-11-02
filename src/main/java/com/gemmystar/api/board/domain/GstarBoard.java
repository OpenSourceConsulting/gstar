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
import com.gemmystar.api.ad.GstarAdController;
import com.gemmystar.api.common.converter.JsView;
import com.gemmystar.api.common.converter.JsonDateSerializer;
import com.gemmystar.api.common.converter.JsonUserSerializer;
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
	
	@Column(name = "board_type_cd", updatable = false)
	@JsonView(JsView.BordList.class)
	private String boardTypeCd = "1";//
	
	@Column(name = "subject")
	@JsonView(JsView.BordList.class)
	private String subject;//
	
	@Column(name = "contents")
	@JsonView(JsView.BordAll.class)
	private String contents;//
	
	@Column(name = "img_url")
	@JsonView(JsView.BordList.class)
	private String imgUrl;
	
	@Column(name = "youtubeId")
	@JsonView(JsView.BordList.class)
	private String youtubeId;
	
	@Column(name = "gstar_user_id", updatable = false)
	@JsonView(JsView.BordAll.class)
	private Long gstarUserId;//
	
	@Column(name = "start_dt", updatable = false)
	@JsonView(JsView.BordList.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	private java.util.Date startDt;//
	
	@Column(name = "end_dt", updatable = false)
	@JsonView(JsView.BordList.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	private java.util.Date endDt;//
	
	@Column(name = "create_dt", updatable = false)
	@JsonView(JsView.BordList.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	private java.util.Date createDt;//
	
	@OneToOne
	@JoinColumn(name = "gstar_user_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonView(JsView.BordList.class)
	@JsonSerialize(using = JsonUserSerializer.class)
	private GstarUser writer;

	/**
	 * 
	 */
	public GstarBoard() {
	}
	
	public GstarBoard(Integer id, String subject, Long gstarUserId, Date createDt, GstarUser writer, Date startDt, Date endDt, String boardTypeCd, String imgUrl, String youtubeId) {
		super();
		this.id = id;
		this.subject = subject;
		this.gstarUserId = gstarUserId;
		this.createDt = createDt;
		this.writer = writer;
		this.startDt = startDt;
		this.endDt = endDt;
		this.boardTypeCd = boardTypeCd;
		this.imgUrl = imgUrl;
		this.youtubeId = youtubeId;
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

	public String getBoardTypeCd() {
		return boardTypeCd;
	}

	public void setBoardTypeCd(String boardTypeCd) {
		this.boardTypeCd = boardTypeCd;
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

	public String getImgUrl() {
		
		if (imgUrl != null) {
			return GstarAdController.IMG_URI + imgUrl;
		}
		
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getYoutubeId() {
		return youtubeId;
	}

	public void setYoutubeId(String youtubeId) {
		this.youtubeId = youtubeId;
	}

	public java.util.Date getStartDt() {
		return startDt;
	}

	public void setStartDt(java.util.Date startDt) {
		this.startDt = startDt;
	}

	public java.util.Date getEndDt() {
		return endDt;
	}

	public void setEndDt(java.util.Date endDt) {
		this.endDt = endDt;
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
