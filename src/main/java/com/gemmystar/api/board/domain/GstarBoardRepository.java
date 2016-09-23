package com.gemmystar.api.board.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * GstarBoardRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarBoardRepository extends JpaRepository<GstarBoard, Integer>, JpaSpecificationExecutor<GstarBoard> {

	@Query(value = "SELECT new GstarBoard(b.id, b.subject, b.gstarUserId, b.createDt, b.writer) FROM GstarBoard b LEFT JOIN b.writer u")
	Page<GstarBoard> findList(Pageable pageable);
	
}