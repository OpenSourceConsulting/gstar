package com.gemmystar.api.victory.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.contents.domain.GstarInfo;
import com.gemmystar.api.victory.domain.GstarVictory;

/**
 * GstarVictoryRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarVictoryRepository extends JpaRepository<GstarVictory, Integer> {
	/*
	@Query(value = "SELECT gi from GstarContents gc join GstarInfo gi "
			+ "where gc.gstarRoomId = ?1 "
			+ "AND gi.pointCnt = (SELECT max(b.pointCnt) from GstarContents a join GstarInfo b where a.gstarRoomId = ?1)")
	List<GstarInfo> getTopGstarInfos(Long gstarRoomId);
	*/
}