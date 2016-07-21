package com.gemmystar.api.contents.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * GstarContentsRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarContentsRepository extends JpaRepository<GstarContents, Long>, JpaSpecificationExecutor<GstarContents> {

	List<GstarContents> findByGstarRoomId(Long roomId);
	
	@Query(value = "select gm from GstarContents gm where gm.gstarRoomId = ?1 and gm.memberTypeCd = '2'")
	List<GstarContents> findChallengerContents(Long roomId);
	
}