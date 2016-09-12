package com.gemmystar.api.room.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.room.domain.GstarRoomWeek;

/**
 * GstarRoomWeekRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarRoomWeekRepository extends JpaRepository<GstarRoomWeek, Long> {

	GstarRoomWeek findByGstarRoomIdAndBattleSeq(Long gstarRoomId, int battleSeq);
	
	@Modifying
	@Query(value = "update GstarRoomWeek grw set grw.endDt = NOW() where grw.gstarRoomId = ?1 and grw.battleSeq = ?2")
	int updateEndDt(Long gstarRoomId, int battleSeq);
}