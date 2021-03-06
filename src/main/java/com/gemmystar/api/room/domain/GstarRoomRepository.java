package com.gemmystar.api.room.domain;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * GstarRoomRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarRoomRepository extends JpaRepository<GstarRoom, Long>, JpaSpecificationExecutor<GstarRoom> {

	GstarRoom findTopByOrderByPointSumDescCreateDtDesc();
	
	@Modifying
	@Transactional
	@Query(value = "update GstarRoom gr set gr.viewSum = gr.viewSum + 1 where gr.id = ?1")
	int increaseViewSum(Long gstarRoomId);
	
	@Modifying
	@Transactional
	@Query(value = "update GstarRoom gr set gr.pointSum = gr.pointSum + ?2 where gr.id = ?1")
	int increasePointSum(Long gstarRoomId, Long usePoint);
	
	/**
	 * <pre>
	 * 판정 대상 대결room 가져오기.
	 * </pre>
	 * @param aWeekAgoDt
	 * @return
	 */
	@Query(value = "SELECT gr FROM GstarRoom gr WHERE gr.battleStatusCd = '2' and gr.endDt is not null and gr.endDt <= ?1")
	List<GstarRoom> getJudgementRooms(Date now);
}