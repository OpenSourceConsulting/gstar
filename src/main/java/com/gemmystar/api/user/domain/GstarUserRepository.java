package com.gemmystar.api.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * GstarUserRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarUserRepository extends JpaRepository<GstarUser, Long> {

	@Modifying
	@Query(value = "update GstarAccount g set g.enabled = 0 where g.gstarUser.id = ?1")
	int widthdraw(Long gstarUserId);
}