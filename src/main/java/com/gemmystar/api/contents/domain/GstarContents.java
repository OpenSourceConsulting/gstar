package com.gemmystar.api.contents.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gemmystar.api.common.converter.JsonDateSerializer;
import com.gemmystar.api.common.util.WebUtil;
import com.gemmystar.api.point.domain.GstarPointHistory;
import com.gemmystar.api.room.domain.GstarRoom;
import com.gemmystar.api.tag.domain.GstarHashTag;
import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.user.domain.GstarUser;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "gstar_contents")
public class GstarContents implements Serializable {

	private static final long serialVersionUID = -2873126313761745558L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;//
	
	@Column(name = "subject")
	private String subject;//제목
	
	@Column(name = "url")
	private String url;//컨텐츠URL(유투브ID)
	
	@Column(name = "memo")
	private String memo;//
	
	@Column(name = "gstar_room_id")
	private Long gstarRoomId;
	
	@Column(name = "gstar_category_id")
	private Integer gstarCategoryId;//
	
	@Column(name = "member_type_cd")
	private String memberTypeCd = "1";
	
	@Column(name = "div_cd")
	private String divCd = "1";
	
	@Column(name = "status_cd")
	private String statusCd = "1";
	
	@Column(name = "locale")
	private String locale;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "create_dt")
	private java.util.Date createDt;//
	
	
	@ManyToOne
	@JoinColumn(name = "gstar_user_id")
	private GstarUser gstarUser;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "gstar_room_id", insertable = false, updatable = false)
	private GstarRoom gstarRoom;
	
	@OneToOne(mappedBy = "gstarContents", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private GstarInfo gstarInfo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "gstar_contents_tags", joinColumns = @JoinColumn(name = "gstar_contents_id"), inverseJoinColumns = @JoinColumn(name = "gstar_hash_tag_id"))
	private List<GstarHashTag> gstarHashTags;
	
	@JsonIgnore
	@OneToMany(mappedBy = "gstarContents", fetch = FetchType.LAZY)
	private List<GstarPointHistory> gstarPointHistories;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarContents() {
		
	}

	public GstarContents(Long id) {
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

	public GstarUser getGstarUser() {
		return gstarUser;
	}

	public void setGstarUser(GstarUser gstarUser) {
		this.gstarUser = gstarUser;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the gstarCategoryId
	 */
	public Integer getGstarCategoryId() {
		return gstarCategoryId;
	}

	/**
	 * @param gstarCategoryId the gstarCategoryId to set
	 */
	public void setGstarCategoryId(Integer gstarCategoryId) {
		this.gstarCategoryId = gstarCategoryId;
	}

	/**
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return statusCd;
	}

	/**
	 * @param statusCd the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getMemberTypeCd() {
		return memberTypeCd;
	}

	public void setMemberTypeCd(String memberTypeCd) {
		this.memberTypeCd = memberTypeCd;
	}

	public String getDivCd() {
		return divCd;
	}

	public void setDivCd(String divCd) {
		this.divCd = divCd;
	}

	public Long getGstarRoomId() {
		return gstarRoomId;
	}

	public void setGstarRoomId(Long gstarRoomId) {
		this.gstarRoomId = gstarRoomId;
	}

	public GstarInfo getGstarInfo() {
		return gstarInfo;
	}

	public void setGstarInfo(GstarInfo gstarInfo) {
		this.gstarInfo = gstarInfo;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
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
	
	public List<GstarHashTag> getGstarHashTags() {
		return gstarHashTags;
	}

	public void setGstarHashTags(List<GstarHashTag> gstarHashTags) {
		this.gstarHashTags = gstarHashTags;
	}

	public List<GstarPointHistory> getGstarPointHistories() {
		return gstarPointHistories;
	}

	public void setGstarPointHistories(List<GstarPointHistory> gstarPointHistories) {
		this.gstarPointHistories = gstarPointHistories;
	}

	public GstarRoom getGstarRoom() {
		return gstarRoom;
	}

	public void setGstarRoom(GstarRoom gstarRoom) {
		this.gstarRoom = gstarRoom;
	}

	@PrePersist
	public void preInsert() {

		GstarAccount loginAccount = WebUtil.getLoginUser();
		
		this.gstarUser = loginAccount.getGstarUser();
		this.createDt = new Date();
		
		this.gstarInfo = new GstarInfo();
		this.gstarInfo.setGstarContents(this);
	}

}
//end of GstarContents.java