package com.gemmystar.api.contents.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * GstarContentsRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarContentsQRepository extends JpaRepository<GstarContents, Long>, QueryDslPredicateExecutor<GstarContents> {

	
	
}