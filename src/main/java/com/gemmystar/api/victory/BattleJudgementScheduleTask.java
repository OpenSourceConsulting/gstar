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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.common.mail.MailSender;
import com.gemmystar.api.common.push.AndroidFcmSender;
import com.gemmystar.api.common.util.DateUtil;
import com.gemmystar.api.contents.GstarContentsService;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.contents.domain.GstarContentsRepository;
import com.gemmystar.api.contents.domain.GstarInfo;
import com.gemmystar.api.contents.domain.GstarInfoRepository;
import com.gemmystar.api.contents.specs.GstarContentsSpecs;
import com.gemmystar.api.contents.specs.GstarInfoSpecs;
import com.gemmystar.api.room.GstarRoomService;
import com.gemmystar.api.room.GstarWeekBattleService;
import com.gemmystar.api.room.domain.GstarRoom;
import com.gemmystar.api.room.domain.GstarRoomRepository;
import com.gemmystar.api.room.domain.GstarWeekBattle;
import com.gemmystar.api.user.domain.GstarUser;
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
	
	@Autowired
	private GstarWeekBattleService weekBattleService;
	
	@Autowired
	private AndroidFcmSender fcmSender;
	
	@Autowired
	@Qualifier("gemmyMailSender")
	private MailSender mailSender;
	
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
		
		Date now = DateUtil.getCurrentDate();
		
		LOGGER.debug("==== check {}.", dateFormat.format(now) );
		
		List<GstarRoom> rooms = roomRepo.getJudgementRooms(now);
		
		Integer gstarWeekBattleId = null;
		
		for (GstarRoom gstarRoom : rooms) {
			
			try {
			
				doJudge(gstarRoom);
				decideHonoraryWinner(gstarRoom.getId());
				
				if (gstarRoom.getGstarWeekBattleId() != null) {
					gstarWeekBattleId = gstarRoom.getGstarWeekBattleId();
				}
			
			} catch (Exception e) {
				LOGGER.error("judge fail. room.id="+ gstarRoom.getId(), e);
			}
		}
		
		if (gstarWeekBattleId != null) {
			// 주간배틀 우승자들이 있으면
			createWeekBattleRooms();
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
		
		LOGGER.info("judge start. room.id={}", gstarRoom.getId());
		
		Specifications<GstarInfo> infoSpec = Specifications.where(GstarInfoSpecs.topGstarInfos(gstarRoom.getId()));
		//List<GstarInfo> gstarInfos = infoRepo.getTopGstarInfos(gstarRoom.getId());
		List<GstarInfo> gstarInfos = infoRepo.findAll(infoSpec);
		
		GstarInfo winnerInfo = null;
		if (gstarInfos.size() == 1) {
			
			winnerInfo = gstarInfos.get(0);
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
					
					if (winnerInfo != null) {
						/*
						 * 조회수까지 같으면 최근 등록한 영상이 우승.
						 */
						GstarContents infoContents = contentsRepo.findOne(winnerInfo.getGstarContentsId());
						GstarContents otherContents = contentsRepo.findOne(gstarInfo.getGstarContentsId());
						if(infoContents.getCreateDt().before(otherContents.getCreateDt())){
							winnerInfo = gstarInfo;
						}
					} else {
					
						winnerInfo = gstarInfo;
					}
				} 
			}
			
		}
		
		// 우승 이력 저장.
		victoryRepo.save(new GstarVictory(gstarRoom.getId(), winnerInfo.getGstarContentsId()));
		LOGGER.info("judged victory room.id={}, contents.id={}", gstarRoom.getId(), winnerInfo.getGstarContentsId());
		
		// 우승 횟수 증가.
		short victoryCnt = (short)(winnerInfo.getVictoryCnt() + 1);
		winnerInfo.setVictoryCnt(victoryCnt);
		infoRepo.save(winnerInfo);
		
		if(winnerInfo.getGstarContents().getId() != gstarRoom.getMasterContentsId()) {
			//우승자가 도전자라면 방장으로 교체.
			changeRoomMaster(gstarRoom, winnerInfo.getGstarContents());
		}
		
		if (victoryCnt < gstarRoom.getBattleMax()) {
			
			// 새로운 배틀차수 시작.
			roomService.startBattle(gstarRoom, gstarRoom.getBattleSeq() + 1);
		} else {
			/*
			 * 대결종료.
			 */
			gstarRoom.setBattleStatusCd(GemmyConstant.CODE_BATTLE_STATUS_FINISHED);
			roomService.save(gstarRoom);
		}
		
		
		sendPushMessage(gstarRoom, winnerInfo.getGstarContents().getGstarUser());
		
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
	
	/**
	 * <pre>
	 * 주간배틀 우승자들끼리 1:1 신규 대결방 생성.
	 * </pre>
	 */
	public void createWeekBattleRooms() {
		
		// 1. 진행중인 주간배틀정보 가져오기
		GstarWeekBattle weekBattle = weekBattleService.getCurrentWeekBattle();
		
		
		if (weekBattle.getBattleSeq() < 5) {
			// 2. 주배틀의 우승자들 목록 random 으로 가져오기
			List<GstarContents> winners = weekBattleService.getWeekBattleWinners(weekBattle.getId(), weekBattle.getBattleSeq());
			
			// 3. 1:1 의 새로운 주간배틀 room 생성하기.
			weekBattleService.createWeekBattleRooms(weekBattle, weekBattle.getBattleSeq() + 1, winners);
		} else {
			/*
			 * 5주차이면 종료처리. room 종료는 doJudge(..) 에서 처리.
			 */
			weekBattle.setEndDt(new Date());
			weekBattle.setStatusCd(GemmyConstant.CODE_WEEK_BATTLE_STATUS_FINISHED);
			
			weekBattleService.save(weekBattle);
		}
	}
	
	protected void sendPushMessage(GstarRoom room, GstarUser user) {
		
		if (user.getFcmToken() != null && user.isMobileNoti()) {
			try {
				fcmSender.sendPushMessage(makeVictoryMessage(room, user), user.getFcmToken());
				
				LOGGER.info("fcm send ok. room.id={}, user.id={}", room.getId(), user.getId());
			} catch (IOException e) {
				LOGGER.error("fcm fail. room.id={}, user.id={}", room.getId(), user.getId());
				LOGGER.error(e.toString(), e);
			}
			
		} else if (user.getEmail() != null && user.isEmailNoti()) {
			mailSender.sendMail("배틀 우승을 축하합니다.", makeVictoryMessage(room, user), user.getEmail());
			LOGGER.info("mail send ok. room.id={}, user.id={}", room.getId(), user.getId());
		}
	}
	
	private String makeVictoryMessage(GstarRoom room, GstarUser user) {
		return "축하드립니다. "+ user.getName() + "님. 귀하의 동영상이 '" + room.getSubject() + "'배틀의 "+(room.getBattleSeq() -1)+"회차 우승자로 선정되었습니다.";
	}

}
//end of BattleJudgementScheduleTask.java