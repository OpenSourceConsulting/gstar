package com.gemmystar.api.room;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.common.util.DateUtil;
import com.gemmystar.api.contents.S3Service;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.contents.domain.GstarContentsRepository;
import com.gemmystar.api.contents.domain.GstarInfo;
import com.gemmystar.api.contents.domain.GstarInfoRepository;
import com.gemmystar.api.contents.specs.GstarContentsSpecs;
import com.gemmystar.api.room.domain.GstarRoom;
import com.gemmystar.api.room.domain.GstarRoomContents;
import com.gemmystar.api.room.domain.GstarRoomContentsRepository;
import com.gemmystar.api.room.domain.GstarRoomContentsWeek;
import com.gemmystar.api.room.domain.GstarRoomContentsWeekRepository;
import com.gemmystar.api.room.domain.GstarRoomRepository;
import com.gemmystar.api.room.domain.GstarRoomSpecs;
import com.gemmystar.api.room.domain.GstarRoomWeek;
import com.gemmystar.api.room.domain.GstarRoomWeekRepository;
import com.gemmystar.api.room.domain.GstarWeekBattle;
import com.gemmystar.api.tag.GstarContentsTagsService;
import com.gemmystar.api.tag.GstarHashTagService;
import com.gemmystar.api.tag.domain.GstarContentsTags;
import com.gemmystar.api.tag.domain.GstarHashTag;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarRoomService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GstarRoomService.class);
	
	@Autowired
	private GstarRoomRepository repository;
	
	@Autowired
	private GstarContentsRepository contentsRepo;
	
	@Autowired
	private GstarHashTagService hashTagService;
	
	@Autowired
	private GstarContentsTagsService contentsTagsService;
	
	@Autowired
	private GstarRoomContentsRepository roomContentsRepo;
	
	@Autowired
	private GstarRoomWeekRepository roomWeekRepo;
	
	@Autowired
	private GstarRoomContentsWeekRepository roomContentsWeekRepo;
	
	@Autowired
	private GstarInfoRepository infoRepo;
	
	@Autowired
	private S3Service s3Service;
	
	
	public GstarRoomService() {
		
	}
	
	@Transactional
	//@CacheEvict(cacheNames="rooms", allEntries=true, condition = "#contents.gstarRoomId == null")
	public void saveWithContents(GstarRoom gstarRoom, GstarContents contents, String[] tags){
		
		if (contents.getGstarRoomId() != null) {
			contents.setMemberTypeCd(GemmyConstant.CODE_MEMBER_TYPE_CHALLENGER);
		}
		
		contentsRepo.save(contents);
		
		if (contents.getGstarRoomId() == null) {
			/*
			 * 방장 영상 등록.
			 */
			if (gstarRoom.getSubject() == null) {
				throw new RuntimeException("필수항목 누락: 대결방 제목(rSubject)이 누락되었습니다.");
			}
			
			gstarRoom.setMasterContentsId(contents.getId());
			repository.save(gstarRoom);
			
			contents.setGstarRoomId(gstarRoom.getId());
			contentsRepo.save(contents);// update gstar_room_id
		} else {
			/*
			 * 도전자 영상 등록.
			 */
			gstarRoom = repository.findOne(contents.getGstarRoomId());
			
			if (gstarRoom.getBattleSeq() == 1 && GemmyConstant.CODE_BATTLE_STATUS_READY.equals(gstarRoom.getBattleStatusCd())) {
				/*
				 * 최초 1주차 배틀: 최초 도전자가 등록되면서 시작.
				 */
				startBattle(gstarRoom, gstarRoom.getBattleSeq());
			}
		}
		
		roomContentsRepo.save(new GstarRoomContents(contents.getGstarRoomId(), contents.getId()));
		
		for (int i = 0; i < tags.length; i++) {
			GstarHashTag tag = hashTagService.save(new GstarHashTag(tags[i]));
			
			contentsTagsService.save(new GstarContentsTags(contents.getId(), tag.getId()));
		}
		
	}
	
	public void saveChallenger(Long gstarRoomId, GstarContents contents, String[] tags) {
		
		contents.setGstarRoomId(gstarRoomId);
		contents.setMemberTypeCd(GemmyConstant.CODE_MEMBER_TYPE_CHALLENGER);
		
		GstarRoom gstarRoom = repository.findOne(contents.getGstarRoomId());
		
		if (gstarRoom.getBattleSeq() == 1 && GemmyConstant.CODE_BATTLE_STATUS_READY.equals(gstarRoom.getBattleStatusCd())) {
			/*
			 * 최초 1주차 배틀: 최초 도전자가 등록되면서 시작.
			 */
			startBattle(gstarRoom, gstarRoom.getBattleSeq());
		}
		
		roomContentsRepo.save(new GstarRoomContents(contents.getGstarRoomId(), contents.getId()));
		
		for (int i = 0; i < tags.length; i++) {
			GstarHashTag tag = hashTagService.save(new GstarHashTag(tags[i]));
			
			contentsTagsService.save(new GstarContentsTags(contents.getId(), tag.getId()));
		}
	}
	
	/**
	 * <pre>
	 * 대결 시작.
	 * </pre>
	 * @param gstarRoom
	 * @param battleSeq 시작할 주차수.
	 */
	@Transactional
	public void startBattle(GstarRoom gstarRoom, int battleSeq) {
		
		Date startDt = new Date();
		
		if (battleSeq > 1) {
			// 이전주차 종료처리 
			roomWeekRepo.updateEndDt(gstarRoom.getId(), battleSeq -1);
			
			// info 백업 및 초기화
			initInfo(gstarRoom.getId(), battleSeq -1);
		}
		
		
		if (gstarRoom.getGstarWeekBattleId() == null) {
			/*
			 * 주간배틀 아님.
			 */
			// 새로운 주차 등록.
			roomWeekRepo.save(new GstarRoomWeek(gstarRoom.getId(), battleSeq, startDt));
			
			// room 배틀정보 업데이트.
			gstarRoom.setBattleSeq(battleSeq);
			gstarRoom.setStartDt(startDt);
			gstarRoom.setEndDt(DateUtil.addDay(startDt, gstarRoom.getBattleTerm()));
			gstarRoom.setBattleStatusCd(GemmyConstant.CODE_BATTLE_STATUS_ING);
			
		} else {
			// 주간배틀.
			// 주간배틀 room은 무조건 finished.
			gstarRoom.setBattleStatusCd(GemmyConstant.CODE_BATTLE_STATUS_FINISHED);
		}
		
		repository.save(gstarRoom);
		
	}
	
	
	/**
	 * <pre>
	 * 이전 주차의 배틀 정보(포인트수, 조회수)를 백업하고 초기화 한다.
	 * ----- 2016-10-20 : 초기화하지 않음.
	 * </pre>
	 * @param gstarRoomId
	 * @param preBattleSeq 이전 배틀 주차수
	 */
	@Transactional
	protected void initInfo(Long gstarRoomId, int preBattleSeq) {
		List<GstarInfo> gstarInfos = infoRepo.findByGstarRoomId(gstarRoomId);
		for (GstarInfo gstarInfo : gstarInfos) {
			
			// 이전 주차 백업.
			roomContentsWeekRepo.save(new GstarRoomContentsWeek(gstarRoomId, gstarInfo.getGstarContentsId(), preBattleSeq, gstarInfo.getPointCnt(), gstarInfo.getViewCnt()));
			
			/*
			 * info 초기화. 
			
			gstarInfo.setPointCnt(0L);
			gstarInfo.setViewCnt(0L);
			infoRepo.save(gstarInfo);
			 */
		}
	}
	
	
	public void save(GstarRoom gstarRoom){
		repository.save(gstarRoom);
	}
	
	//@Cacheable("rooms")
	public Page<GstarRoom> getGstarRoomList(Pageable pageable, String search){
		
		Page<GstarRoom> page = null;

		if (search != null) {
			Specifications<GstarRoom> spec = Specifications.where(GstarRoomSpecs.likeSubject(search)).or(GstarRoomSpecs.likeTag(search));
			//Specifications<GstarRoom> spec = Specifications.where(GstarRoomSpecs.likeSubject(search));
			
			page = repository.findAll(spec, pageable);
		} else {
			page = repository.findAll(pageable);
		}
		
		setTopChallenger(page);
		
		return page;
	}
	
	public Page<GstarRoom> getGstarRoomListByTag(Pageable pageable, String tag){
		
		Specifications<GstarRoom> spec = Specifications.where(GstarRoomSpecs.eqTag(tag));
		
		Page<GstarRoom> page = repository.findAll(spec, pageable);
		
		return page;
	}
	
	public Page<GstarRoom> getGstarRoomListByTabMenu(Pageable pageable, Integer tabMenuId){
		
		Specifications<GstarRoom> spec = Specifications.where(GstarRoomSpecs.eqTabMenuId(tabMenuId));
		
		Page<GstarRoom> page = repository.findAll(spec, pageable);
		
		return page;
	}
	
	public Page<GstarRoom> getUserGstarRoomList(Pageable pageable, Long gstarUserId){
		
		Specifications<GstarRoom> spec = Specifications.where(GstarRoomSpecs.myRoom(gstarUserId));
		
		Page<GstarRoom> page = repository.findAll(spec, pageable);
		
		return page;
	}
	
	public Page<GstarRoom> getGstarRoomBestList(int size){
		
		Sort sort = new Sort(Sort.Direction.DESC, "pointSum", "createDt");
		PageRequest pageable = new PageRequest(0, size, sort);
		
		Page<GstarRoom> page = repository.findAll(pageable);
		
		setTopChallenger(page);
		
		return page;
	}
	
	public GstarRoom getGstarRoom(Long roomId){
		GstarRoom room = repository.findOne(roomId);
		
		return room;
	}
	
	public GstarRoom getGstarRoomWithChallengers(Long roomId){
		GstarRoom room = repository.findOne(roomId);
		
		//room.setChallengerContentsList(getChallengerContentsList(roomId));// 별도 api 로 분리. 2016.09.30
		
		return room;
	}
	
	public Page<GstarContents> getChallengerContentsList(Long roomId, Pageable pageable) {
		Specifications<GstarContents> spec = Specifications.where(GstarContentsSpecs.challengers(roomId)).and(GstarContentsSpecs.notDeteled());
		
		return contentsRepo.findAll(spec, pageable);
	}
	
	public GstarRoom getBestTopGstarRoom() {
		GstarRoom room = repository.findTopByOrderByPointSumDescCreateDtDesc();
		
		//room.setChallengerContentsList(getChallengerContentsList(room.getId())); 별도 api 로 분리. 2016.09.30
		
		return room;
	}
	
	
	public void deleteGstarRoom(Long roomId){
		repository.delete(roomId);
	}
	
	private void setTopChallenger(Page<GstarRoom> page) {
		//if (page.isFirst()) {
			
			Sort sort = new Sort(Sort.Direction.DESC, "gstarInfo.pointCnt", "createDt");
			PageRequest pageable = new PageRequest(0, 1, sort);
			
			for (GstarRoom gstarRoom : page) {
				Page<GstarContents> challengers = getChallengerContentsList(gstarRoom.getId(), pageable);
				
				if (challengers.getTotalElements() > 0) {
					gstarRoom.setTopChallenger(challengers.getContent().get(0));
				}
			}
			
		//}
	}

}
//end of GstarRoomService.java