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
	
	public static final String FIND_BOARDS = "SELECT b FROM GstarBoard b";

	@Query(value = FIND_BOARDS + " WHERE b.boardTypeCd = '1'")
	Page<GstarBoard> findBoardList(Pageable pageable);
	
	@Query(value = FIND_BOARDS + " WHERE b.boardTypeCd = '2' or b.boardTypeCd = '3'")
	Page<GstarBoard> findEventList(Pageable pageable);
	
	@Query(value = FIND_BOARDS + " WHERE (b.boardTypeCd = '2' or b.boardTypeCd = '3') and NOW() BETWEEN b.startDt AND b.endDt")
	Page<GstarBoard> findCurrentEventList(Pageable pageable);
	
}