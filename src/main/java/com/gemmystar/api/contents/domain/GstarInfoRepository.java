package com.gemmystar.api.contents.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.contents.domain.GstarInfo;

/**
 * GstarInfoRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarInfoRepository extends JpaRepository<GstarInfo, Long> {

	@Modifying
	@Query(value = "update GstarInfo gi set gi.viewCnt = gi.viewCnt + 1 where gi.gstarContents = ?1")
	int increaseViewCnt(GstarContents gstarContents);
	
}