package com.gemmystar.api.tag.domain;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	
	@Query(value = "SELECT id, tag, (select count(1) from gstar_contents_tags ct where ct.gstar_hash_tag_id = ht.id) cnt "
			+ " FROM gstar.gstar_hash_tag ht order by cnt desc limit 15", nativeQuery = true)
	List<GstarHashTag> findMainTags();
	
	
}