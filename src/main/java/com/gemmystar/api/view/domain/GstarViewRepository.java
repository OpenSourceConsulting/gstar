package com.gemmystar.api.view.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gemmystar.api.view.domain.GstarView;

/**
 * GstarLikeRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarViewRepository extends JpaRepository<GstarView, GstarViewPK> {

	@Modifying
	@Transactional
	@Query(value = "update GstarView gw set gw.viewCnt = gw.viewCnt + 1 where gw.gstarUserId = ?1 and gw.gstarContentsId = ?2")
	int increaseViewCnt(Long gstarUserId, Long gstarContentsId);
}