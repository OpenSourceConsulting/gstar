package com.gemmystar.api.board.domain;

import java.util.Date;

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
	
	public static final String FIND_BOARDS = "SELECT new GstarBoard(b.id, b.subject, b.gstarUserId, b.createDt, b.writer, b.startDt, b.endDt) FROM GstarBoard b LEFT JOIN b.writer u";

	@Query(value = FIND_BOARDS + " WHERE b.boardTypeCd = '1'")
	Page<GstarBoard> findList(Pageable pageable);
	
	@Query(value = FIND_BOARDS + " WHERE b.boardTypeCd = '2' or b.boardTypeCd = '3'")
	Page<GstarBoard> findEventList(Pageable pageable);
	
}