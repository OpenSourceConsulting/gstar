package com.gemmystar.api.point.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.point.domain.GstarUserPoint;

/**
 * GstarUserPointRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarUserPointRepository extends JpaRepository<GstarUserPoint, Long>, JpaSpecificationExecutor<GstarUserPoint> {

	@Modifying
	@Query(value = "update GstarUserPoint g set g.cancelReason = ?3, g.cancelReqDt = NOW(), g.pcStatusCd = '2' where g.id = ?1 and g.gstarUserId = ?2 ")
	int updateCancelInfo(Long gstarUserPointId, Long gstarUserId, String cancelReason);

}