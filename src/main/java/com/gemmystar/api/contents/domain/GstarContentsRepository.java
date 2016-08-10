package com.gemmystar.api.contents.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.user.domain.GstarUser;

/**
 * GstarContentsRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarContentsRepository extends JpaRepository<GstarContents, Long>, JpaSpecificationExecutor<GstarContents> {

	List<GstarContents> findByGstarRoomId(Long roomId);
	
	@Query(value = "select gm from GstarContents gm where gm.gstarRoomId = ?1 and gm.memberTypeCd = '2' and gm.statusCd != '3'")
	List<GstarContents> findChallengerContents(Long roomId);
	
	@Query(value = "SELECT DISTINCT gc FROM GstarContents gc INNER JOIN gc.gstarInfo gi INNER JOIN gc.gstarHashTags ht WHERE gc.gstarUser = ?1")
	Page<GstarContents> getMyContents(GstarUser gstarUser, Pageable pageable);
	
}