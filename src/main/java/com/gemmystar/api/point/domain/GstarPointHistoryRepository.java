package com.gemmystar.api.point.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.point.domain.GstarPointHistory;

/**
 * GstarPointHistoryRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarPointHistoryRepository extends JpaRepository<GstarPointHistory, Integer>, JpaSpecificationExecutor<GstarPointHistory> {

	
}