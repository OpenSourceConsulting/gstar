package com.gemmystar.api.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * GstarAccountRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarAccountRepository extends JpaRepository<GstarAccount, Long> {

	GstarAccount findByLoginId(String loginId); 
	
}