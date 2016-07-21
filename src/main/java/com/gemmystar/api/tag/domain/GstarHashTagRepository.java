package com.gemmystar.api.tag.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.tag.domain.GstarHashTag;

/**
 * GstarHashTagRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarHashTagRepository extends JpaRepository<GstarHashTag, Long> {

	GstarHashTag findByTag(String tag);
	
}