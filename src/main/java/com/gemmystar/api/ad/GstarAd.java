/**
 * 
 */
package com.gemmystar.api.ad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author BongJin Kwon
 *
 */
@Entity
@Table(name = "gstar_ad")
public class GstarAd {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//
	
	@Column(name = "title")
	private String title;//
	
	@Column(name = "ad_url")
	private String adUrl;//
	
	@Column(name = "img_url")
	private String imgUrl;//
	
	@Column(name = "img_file_path")
	private String imgFilePath;//
	
	@Column(name = "note")
	private String note;//
	
	@Column(name = "view_cnt")
	private int viewCnt;//
	
	@Column(name = "click_cnt")
	private int clickCnt;//
	
	@Column(name = "create_dt", updatable = false)
	private java.util.Date createDt;//

	/**
	 * 
	 */
	public GstarAd() {
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the adUrl
	 */
	public String getAdUrl() {
		return adUrl;
	}

	/**
	 * @param adUrl the adUrl to set
	 */
	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * @return the imgFilePath
	 */
	public String getImgFilePath() {
		return imgFilePath;
	}

	/**
	 * @param imgFilePath the imgFilePath to set
	 */
	public void setImgFilePath(String imgFilePath) {
		this.imgFilePath = imgFilePath;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the viewCnt
	 */
	public int getViewCnt() {
		return viewCnt;
	}

	/**
	 * @param viewCnt the viewCnt to set
	 */
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	/**
	 * @return the clickCnt
	 */
	public int getClickCnt() {
		return clickCnt;
	}

	/**
	 * @param clickCnt the clickCnt to set
	 */
	public void setClickCnt(int clickCnt) {
		this.clickCnt = clickCnt;
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

}
