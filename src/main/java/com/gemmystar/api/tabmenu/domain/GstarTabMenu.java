/**
 * 
 */
package com.gemmystar.api.tabmenu.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gemmystar.api.common.converter.JsonDateSerializer;

/**
 * @author BongJin Kwon
 *
 */
@Entity
@Table(name = "gstar_tab_menu")
public class GstarTabMenu {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//
	
	@Column(name = "name")
	private String name;//
	
	@Column(name = "menu_type_cd")
	private String menuTypeCd = "1";//
	
	@Column(name = "order_seq")
	private int orderSeq;
	
	@Column(name = "hidden")
	private boolean hidden;//
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "create_dt", updatable = false)
	private Date createDt;//

	/**
	 * 
	 */
	public GstarTabMenu() {
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the menuTypeCd
	 */
	public String getMenuTypeCd() {
		return menuTypeCd;
	}

	/**
	 * @param menuTypeCd the menuTypeCd to set
	 */
	public void setMenuTypeCd(String menuTypeCd) {
		this.menuTypeCd = menuTypeCd;
	}

	public int getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public java.util.Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(java.util.Date createDt) {
		this.createDt = createDt;
	}
	
	@PrePersist
	public void preInsert() {
		this.createDt = new Date();
	}

}
