package com.gemmystar.api.tag.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.tag.domain.GstarContentsTags;

/**
 * GstarContentsTagsRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarContentsTagsRepository extends JpaRepository<GstarContentsTags, Long> {

	
}