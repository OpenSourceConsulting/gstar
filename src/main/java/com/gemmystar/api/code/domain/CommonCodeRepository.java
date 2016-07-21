package com.gemmystar.api.code.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CommonCodeRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodePK> {

	List<CommonCode> findByGroupCd(String groupCd);
	
}