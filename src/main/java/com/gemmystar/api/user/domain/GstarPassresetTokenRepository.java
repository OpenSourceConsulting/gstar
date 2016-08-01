package com.gemmystar.api.user.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.user.domain.GstarPassresetToken;

/**
 * GstarPassresetTokenRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarPassresetTokenRepository extends JpaRepository<GstarPassresetToken, Integer> {

	
}