package com.gemmystar.api.room.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
}