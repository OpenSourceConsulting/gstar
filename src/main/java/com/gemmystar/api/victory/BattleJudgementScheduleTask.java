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

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.contents.GstarContentsService;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.contents.domain.GstarContentsRepository;
import com.gemmystar.api.contents.domain.GstarInfo;
import com.gemmystar.api.contents.domain.GstarInfoRepository;
import com.gemmystar.api.contents.specs.GstarContentsSpecs;
import com.gemmystar.api.contents.specs.GstarInfoSpecs;
import com.gemmystar.api.room.GstarRoomService;
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
	
	@Autowired
	private GstarRoomService roomService;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public BattleJudgementScheduleTask() {
	}
	
	@Scheduled(cron="0 0/10 * * * *")
	//@Scheduled(fixedRate = 10000)
	public void judge() {
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DAY_OF_WEEK, -7);
		
		//dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		LOGGER.debug("==== check {} ago.", dateFormat.format(cal.getTime()) );
		
		List<GstarRoom> rooms = roomRepo.getJudgementRooms(cal.getTime());
		
		for (GstarRoom gstarRoom : rooms) {
			doJudge(gstarRoom);
			decideHonoraryWinner(gstarRoom.getId());
		}
	}
	
	/**
	 * <pre>
	 * 명예의 전당 선정.
	 * </pre>
	 * @param gstarRoomId
	 */
	protected void decideHonoraryWinner(Long gstarRoomId) {
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.fiveWinner(gstarRoomId));
		GstarContents gstarContents = contentsRepo.findOne(spec);
		
		if (gstarContents != null) {
			gstarContents.setDivCd(GemmyConstant.CODE_CNTS_DIV_HONOR);
			contentsRepo.save(gstarContents);
		}
	}
	
	@Transactional
	protected void doJudge(GstarRoom gstarRoom) {
		
		Specifications<GstarInfo> infoSpec = Specifications.where(GstarInfoSpecs.topGstarInfos(gstarRoom.getId()));
		//List<GstarInfo> gstarInfos = infoRepo.getTopGstarInfos(gstarRoom.getId());
		List<GstarInfo> gstarInfos = infoRepo.findAll(infoSpec);
		
		GstarInfo info = null;
		if (gstarInfos.size() == 1) {
			
			info = gstarInfos.get(0);
		} else {
			
			/*
			 * 포인트수가 같다면 조회수로 판정.
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
						GstarContents infoContents = contentsRepo.findOne(info.getGstarContentsId());
						GstarContents otherContents = contentsRepo.findOne(gstarInfo.getGstarContentsId());
						if(infoContents.getCreateDt().before(otherContents.getCreateDt())){
							info = gstarInfo;
						}
					} else {
					
						info = gstarInfo;
					}
				} 
			}
			
		}
		
		// 우승 이력 저장.
		victoryRepo.save(new GstarVictory(gstarRoom.getId(), info.getGstarContentsId()));
		
		// 우승 횟수 증가.
		short victoryCnt = (short)(info.getVictoryCnt() + 1);
		info.setVictoryCnt(victoryCnt);
		infoRepo.save(info);
		
		
		if (victoryCnt < 5) {
			//5회 우승 이하일때는
			
			if(info.getGstarContents().getId() != gstarRoom.getMasterContentsId()) {
				//우승자가 도전자라면 방장으로 교체.
				changeRoomMaster(gstarRoom, info.getGstarContents());
			}
			
			// 새로운 배틀차수 시작.
			roomService.startBattle(gstarRoom, gstarRoom.getBattleSeq() + 1);
		} else {
			/*
			 * 5회 우승자가 나오면 대결종료.
			 */
			gstarRoom.setBattleStatusCd(GemmyConstant.CODE_BATTLE_STATUS_FINISHED);
			roomService.save(gstarRoom);
		}
		
	}
	
	@Transactional
	public void changeRoomMaster(GstarRoom gstarRoom, GstarContents newMaster) {
		GstarContents oldMaster = gstarRoom.getMasterContents();
		
		oldMaster.setMemberTypeCd(GemmyConstant.CODE_MEMBER_TYPE_CHALLENGER);
		newMaster.setMemberTypeCd(GemmyConstant.CODE_MEMBER_TYPE_MASTER);
		
		contentsRepo.save(oldMaster);
		contentsRepo.save(newMaster);
		
		gstarRoom.setMasterContentsId(newMaster.getId());
		gstarRoom.setMasterContents(newMaster);
		
		roomService.save(gstarRoom);
	}

}
//end of BattleJudgementScheduleTask.java