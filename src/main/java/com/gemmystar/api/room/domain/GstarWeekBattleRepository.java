package com.gemmystar.api.room.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.room.domain.GstarWeekBattle;

/**
 * GstarWeekBattleRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarWeekBattleRepository extends JpaRepository<GstarWeekBattle, Integer> {

	@Query(value = "select g from GstarWeekBattle g where g.statusCd = '1' and g.endDt is null")
	GstarWeekBattle getCurrentWeekBattle();
	
	
	@Query(value = "select gc from GstarWeekBattle gwb, GstarRoom gr, GstarVictory gv, GstarContents gc " + 
			"where gwb.id = ?1 and gwb.id = gr.gstarWeekBattleId and gr.battleSeq = ?2 and gr.id = gv.gstarRoomId and gv.gstarContentsId = gc.id ORDER BY RAND()")
	List<GstarContents> getWeekBattleWinners(Integer GstarWeekBattleId, int battleSeq);
	
}