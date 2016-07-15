package com.gemmystar.api.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.domain.GstarInfo;

/**
 * GstarInfoRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarInfoRepository extends JpaRepository<GstarInfo, Long> {

	
}