package com.gemmystar.api.ad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.ad.GstarAd;

/**
 * GstarAdRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarAdRepository extends JpaRepository<GstarAd, Integer>, JpaSpecificationExecutor<GstarAd> {

	
}