package com.gemmystar.api.domain;

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

	
}