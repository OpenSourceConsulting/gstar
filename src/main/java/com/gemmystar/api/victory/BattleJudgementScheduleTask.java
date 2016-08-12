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
 * BongJin Kwon		2016. 8. 11.		First Draft.
 */
package com.gemmystar.api.victory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gemmystar.api.contents.domain.GstarContentsRepository;
import com.gemmystar.api.contents.domain.GstarInfo;
import com.gemmystar.api.contents.domain.GstarInfoRepository;
import com.gemmystar.api.contents.specs.GstarInfoSpecs;
import com.gemmystar.api.room.domain.GstarRoom;
import com.gemmystar.api.room.domain.GstarRoomRepository;
import com.gemmystar.api.victory.domain.GstarVictory;
import com.gemmystar.api.victory.domain.GstarVictoryRepository;

/**
 * <pre>
 * 주기적으로 현재 대결중인것들에 대해 판정처리한다.
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Component
public class BattleJudgementScheduleTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BattleJudgementScheduleTask.class);
	
	@Autowired
	private GstarRoomRepository roomRepo;
	
	@Autowired
	private GstarContentsRepository contentsRepo;
	
	@Autowired
	private GstarVictoryRepository victoryRepo;
	
	@Autowired
	private GstarInfoRepository infoRepo;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public BattleJudgementScheduleTask() {
	}
	
	@Scheduled(cron="0 0/10 * * * *")
	//@Scheduled(fixedRate = 60000)
	public void judge() {
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DAY_OF_WEEK, -7);
		
		//dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		LOGGER.debug("==== check {} ago.", dateFormat.format(cal.getTime()) );
		
		List<GstarRoom> rooms = roomRepo.getJudgementRooms(cal.getTime());
		
		for (GstarRoom gstarRoom : rooms) {
			doJudge(gstarRoom);
		}
	}
	
	
	protected void doJudge(GstarRoom gstarRoom) {
		
		Specifications<GstarInfo> infoSpec = Specifications.where(GstarInfoSpecs.topGstarInfos(gstarRoom.getId()));
		//List<GstarInfo> gstarInfos = infoRepo.getTopGstarInfos(gstarRoom.getId());
		List<GstarInfo> gstarInfos = infoRepo.findAll(infoSpec);
		
		GstarInfo info = null;
		if (gstarInfos.size() == 1) {
			
			info = gstarInfos.get(0);
		} else {
			
			/*
			 * max 값 확인.
			 */
			long max = 0L;
			for (GstarInfo gstarInfo : gstarInfos) {
				if (max < gstarInfo.getViewCnt()) {
					max = gstarInfo.getViewCnt();
				} 
			}
			
			
			for (GstarInfo gstarInfo : gstarInfos) {
				
				if (max == gstarInfo.getViewCnt()) {
					
					if (info != null) {
						/*
						 * 조회수까지 같으면 최근 등록한 영상이 우승.
						 */
						if(info.getGstarContents().getCreateDt().before(gstarInfo.getGstarContents().getCreateDt())){
							info = gstarInfo;
						}
					} else {
					
						info = gstarInfo;
					}
				} 
			}
			
		}
		
		victoryRepo.save(new GstarVictory(gstarRoom.getId(), info.getGstarContents().getId()));
		
		
		info.setVictoryCnt((short)(info.getVictoryCnt() + 1));
		
		infoRepo.save(info);
		
		
		gstarRoom.setBattleStatusCd("2");// 대결 일시 중지.
		roomRepo.save(gstarRoom);
	}

	
	/*
	public static void main(String[] args) {
		for (String string : TimeZone.getAvailableIDs()) {
			System.out.println(string);
		}
		
		Calendar cal = Calendar.getInstance();
		//dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal.add(Calendar.DAY_OF_WEEK, -7);
		
		System.out.println("========== " + dateFormat.format(cal.getTime()));
	}
*/
}
//end of BattleJudgementScheduleTask.java