package com.gemmystar.api.room.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * GstarRoomRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarRoomRepository extends JpaRepository<GstarRoom, Long>, JpaSpecificationExecutor<GstarRoom> {

	GstarRoom findTopByOrderByPointSumDescCreateDtDesc();
	
	@Modifying
	@Query(value = "update GstarRoom gr set gr.viewSum = gr.viewSum + 1 where gr.id = ?1")
	int increaseViewSum(Long gstarRoomId);
}