package com.gemmystar.api.room.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.room.domain.GstarWeekBattle;

/**
 * GstarWeekBattleRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarWeekBattleRepository extends JpaRepository<GstarWeekBattle, Integer> {

	@Query(value = "select g from GstarWeekBattle g where g.statusCd = '1' and NOW() BETWEEN g.startDt and g.endDt")
	GstarWeekBattle getCurrentWeekBattle();
	
}