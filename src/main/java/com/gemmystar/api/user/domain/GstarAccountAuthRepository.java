package com.gemmystar.api.user.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.user.domain.GstarAccountAuth;

/**
 * GstarAccountAuthRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarAccountAuthRepository extends JpaRepository<GstarAccountAuth, Integer>, JpaSpecificationExecutor<GstarAccountAuth> {

	
}