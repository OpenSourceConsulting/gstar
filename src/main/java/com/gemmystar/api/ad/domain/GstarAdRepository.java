package com.gemmystar.api.ad.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * GstarAdRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarAdRepository extends JpaRepository<GstarAd, Integer>, JpaSpecificationExecutor<GstarAd> {

	@Query("select ad from GstarAd ad where NOW() BETWEEN ad.startDt AND ad.endDt")
	Page<GstarAd> findCurrentAds(Pageable pageable);
}