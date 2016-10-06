package com.gemmystar.api.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * GstarPassresetTokenRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarPassresetTokenRepository extends JpaRepository<GstarPassresetToken, Long> {

	GstarPassresetToken findByToken(String token);
	
	@Modifying
	@Query("update GstarPassresetToken pt set pt.expireDt = NOW() where pt.id = ?1")
	int updateExpireDt(Long accountId);
	
}