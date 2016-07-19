package com.gemmystar.api.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * GstarContentsRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarContentsRepository extends JpaRepository<GstarContents, Long> {

	List<GstarContents> findByGstarRoomId(Long roomId);
	
}