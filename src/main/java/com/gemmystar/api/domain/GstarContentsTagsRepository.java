package com.gemmystar.api.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * GstarContentsTagsRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarContentsTagsRepository extends JpaRepository<GstarContentsTags, Long> {

	
}