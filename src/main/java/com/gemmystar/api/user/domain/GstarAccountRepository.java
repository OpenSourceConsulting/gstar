package com.gemmystar.api.user.domain;

import java.util.List;

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
	
	List<GstarAccount> findByGstarUserId(Long gstarUserId);
	
}