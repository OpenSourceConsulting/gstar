package com.gemmystar.api.contents.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.contents.domain.GstarInfo;

/**
 * GstarInfoRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarInfoRepository extends JpaRepository<GstarInfo, Long>, JpaSpecificationExecutor<GstarInfo> {

	@Modifying
	@Query(value = "update GstarInfo gi set gi.viewCnt = gi.viewCnt + 1, updateDt = NOW() where gi.gstarContentsId = ?1")
	int increaseViewCnt(Long gstarContentsId);
	
	
	@Modifying
	@Query(value = "update GstarInfo gi set gi.pointCnt = gi.pointCnt + ?2, updateDt = NOW() where gi.gstarContentsId = ?1")
	int increasePointCnt(Long gstarContentsId, Long usePoint);
	
	
	@Query(value = "SELECT gi from GstarInfo gi, GstarRoomContents grc "
			+ "where gi.gstarContentsId = grc.gstarContentsId "
			+ "and grc.gstarRoomId = ?1 ")
	List<GstarInfo> findByGstarRoomId(Long gstarRoomId);
	
}