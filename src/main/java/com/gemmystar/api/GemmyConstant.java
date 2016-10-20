/* 
 * Copyright (C) 2012-2015 Open Source Consulting, Inc. All rights reserved by Open Source Consulting, Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * Revision History
 * Author			Date				Description
 * ---------------	----------------	------------
 * BongJin Kwon		2016. 8. 10.		First Draft.
 */
package com.gemmystar.api;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
public class GemmyConstant {

	public static final String CONTENTS_ATTR_MEMBER_TYPE_CD = "memberTypeCd";
	public static final String CONTENTS_ATTR_STATUS_CD = "statusCd";
	public static final String CONTENTS_ATTR_DIV_CD = "divCd";
	
	/** 방장영상  */
	public static final String CODE_MEMBER_TYPE_MASTER = "1";
	/** 도전영상 */
	public static final String CODE_MEMBER_TYPE_CHALLENGER = "2";
	
	/** 대결 포기 */
	public static final String CODE_CNTS_STATUS_GIVEUP = "2";
	/** 영상 게시 종료 */
	public static final String CODE_CNTS_STATUS_CLOSED = "3";
	
	/** 추천 영상 */
	public static final String CODE_CNTS_DIV_RECOMMAND = "2";
	/** 명예의 전당 */
	public static final String CODE_CNTS_DIV_HONOR = "4";
	
	/** 대결준비중 */
	public static final String CODE_BATTLE_STATUS_READY = "1";
	/** 대결중 */
	public static final String CODE_BATTLE_STATUS_ING = "2";
	/** 대결종료 */
	public static final String CODE_BATTLE_STATUS_FINISHED = "3";
	
	/** 주간배틀 대결중 */
	public static final String CODE_WEEK_BATTLE_STATUS_ING = "1";
	/** 주간배틀 대결종료 */
	public static final String CODE_WEEK_BATTLE_STATUS_FINISHED = "2";
	
	/** 사용자포인트: 정상구매 */
	public static final String CODE_PC_STATUS_OK = "1";
	/** 사용자포인트: 환불완료 */
	public static final String CODE_PC_STATUS_CANCEL = "3";
	
	/** 관리자계정 */
	public static final String CODE_ACCOUNT_TYPE_ADMIN = "4";
	
	/** 포인트 사용이력 상태: 정상사용 */
	public static final String CODE_POINT_HS_STATUS_NORMAL = "1";
	/** 포인트 사용이력 상태: 지급완료 */
	public static final String CODE_POINT_HS_STATUS_PAID = "2";
	/** 포인트 사용이력 상태: 사용취소 */
	public static final String CODE_POINT_HS_STATUS_CANCEL = "3";
	
	
	public static final String S3_KEY_PREFIX_VIDEO = "video/";

	

}
//end of GemmyConstant.java