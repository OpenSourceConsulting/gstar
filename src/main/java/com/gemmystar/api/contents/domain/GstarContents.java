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
import com.gemmystar.api.common.converter.JsonUserSerializer;
import com.gemmystar.api.common.util.WebUtil;
import com.gemmystar.api.point.domain.GstarPointHistory;
import com.gemmystar.api.point.domain.GstarUserPoint;
import com.gemmystar.api.room.domain.GstarRoom;
import com.gemmystar.api.room.domain.GstarRoomContents;
import com.gemmystar.api.room.domain.GstarRoomContentsWeek;
import com.gemmystar.api.tag.domain.GstarHashTag;
import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.user.domain.GstarUser;
import com.gemmystar.api.victory.domain.GstarVictory;
import com.gemmystar.api.view.domain.GstarView;

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
	
	@Transient
	private Long userId;
	
	@Column(name = "subject")
	private String subject;//제목
	
	@Column(name = "url")
	private String url;//컨텐츠URL(유투브ID)
	
	@Column(name = "memo")
	private String memo;//
	
	@Column(name = "thumbnail_url")
	private String thumbnailUrl;
	
	@Column(name = "gstar_room_id")
	private Long gstarRoomId;
	
	@Column(name = "gstar_category_id")
	private Integer gstarCategoryId;//
	
	@Column(name = "member_type_cd")
	private String memberTypeCd = "1";
	
	@Column(name = "div_cd")
	private String divCd = "1";
	
	// 작사
	@Column(name = "lyrics")
	private String lyrics;
	
	// 작곡
	@Column(name = "composition")
	private String composition;
	
	// 편곡
	@Column(name = "arrangement")
	private String arrangement;
	
	@Column(name = "status_cd")
	private String statusCd = "1";
	
	@Column(name = "s3key")
	private String s3key;
	
	@Column(name = "locale")
	private String locale;
	
	@Column(name = "order_seq")
	private Integer orderSeq;
	
	@Column(name = "deleted")
	private boolean deleted;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "create_dt")
	private java.util.Date createDt;//
	
	
	@ManyToOne
	@JoinColumn(name = "gstar_user_id")
	@JsonSerialize(using = JsonUserSerializer.class)
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
	
	@OneToMany(mappedBy = "gstarContents", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	private List<GstarRoomContents> gstarRommContentsList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "gstarContents", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	private List<GstarContentsWarn> gstarContentsWarns;
	
	@JsonIgnore
	@OneToMany(mappedBy = "gstarContents", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	private List<GstarView> gstarViews;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "gstar_contents_id")
	private List<GstarVictory> gstarVictories;
	
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "gstar_contents_id")
	private List<GstarRoomContentsWeek> battleHistories;
	

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
	

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
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
	
	/**
	 * @return the lyrics
	 */
	public String getLyrics() {
		return lyrics;
	}

	/**
	 * @param lyrics the lyrics to set
	 */
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	/**
	 * @return the composition
	 */
	public String getComposition() {
		return composition;
	}

	/**
	 * @param composition the composition to set
	 */
	public void setComposition(String composition) {
		this.composition = composition;
	}

	/**
	 * @return the arrangement
	 */
	public String getArrangement() {
		return arrangement;
	}

	/**
	 * @param arrangement the arrangement to set
	 */
	public void setArrangement(String arrangement) {
		this.arrangement = arrangement;
	}

	public String getS3key() {
		return s3key;
	}

	public void setS3key(String s3key) {
		this.s3key = s3key;
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

	public Integer getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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

	public List<GstarRoomContents> getGstarRommContentsList() {
		return gstarRommContentsList;
	}

	public void setGstarRommContentsList(
			List<GstarRoomContents> gstarRommContentsList) {
		this.gstarRommContentsList = gstarRommContentsList;
	}

	public List<GstarContentsWarn> getGstarContentsWarns() {
		return gstarContentsWarns;
	}

	public void setGstarContentsWarns(List<GstarContentsWarn> gstarContentsWarns) {
		this.gstarContentsWarns = gstarContentsWarns;
	}

	public List<GstarView> getGstarViews() {
		return gstarViews;
	}

	public void setGstarViews(List<GstarView> gstarViews) {
		this.gstarViews = gstarViews;
	}

	public List<GstarVictory> getGstarVictories() {
		return gstarVictories;
	}

	public void setGstarVictories(List<GstarVictory> gstarVictories) {
		this.gstarVictories = gstarVictories;
	}

	public List<GstarRoomContentsWeek> getBattleHistories() {
		return battleHistories;
	}

	public void setBattleHistories(List<GstarRoomContentsWeek> battleHistories) {
		this.battleHistories = battleHistories;
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