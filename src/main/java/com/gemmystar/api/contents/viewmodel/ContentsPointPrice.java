/**
 * 
 */
package com.gemmystar.api.contents.viewmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.point.domain.GstarPointHistory;
import com.gemmystar.api.point.domain.GstarUserPoint;

/**
 * @author BongJin Kwon
 *
 */
public class ContentsPointPrice {
	
	private Long gstarContentsId;
	
	private int receviedPoint;
	
	private int pointPrice;
	
	@JsonIgnore
	private List<GstarPointHistory> gstarPointHistories;
	
	
	public ContentsPointPrice(Long gstarContentsId, List<GstarPointHistory> gstarPointHistories) {
		
		this.gstarContentsId = gstarContentsId;
		this.gstarPointHistories = gstarPointHistories;
		
	}
	
	public Long getGstarContentsId() {
		return gstarContentsId;
	}

	public void setGstarContentsId(Long gstarContentsId) {
		this.gstarContentsId = gstarContentsId;
	}

	public List<GstarPointHistory> getGstarPointHistories() {
		return gstarPointHistories;
	}

	public void setGstarPointHistories(List<GstarPointHistory> gstarPointHistories) {
		this.gstarPointHistories = gstarPointHistories;
	}

	public int getReceviedPoint() {
		
		List<GstarPointHistory> gstarPointHistories = getGstarPointHistories();
		
		if (gstarPointHistories != null && gstarPointHistories.size() > 0) {
			receviedPoint = 0;
			for (GstarPointHistory gstarPointHistory : gstarPointHistories) {
				receviedPoint = receviedPoint + gstarPointHistory.getUsePoint();
			}
		}
		
		return receviedPoint;
	}

	public void setReceviedPoint(int receviedPoint) {
		this.receviedPoint = receviedPoint;
	}
	
	public int getPointPrice() {
		return getReceviedPoint() * GstarUserPoint.UNIT_PRICE;
	}
	
	public void setPointPrice(int pointPrice) {
		this.pointPrice = pointPrice;
	}
}
