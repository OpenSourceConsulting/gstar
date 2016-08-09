package com.gemmystar.api.contents.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.contents.domain.GstarContentsWarn;

/**
 * GstarContentsWarnRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarContentsWarnRepository extends JpaRepository<GstarContentsWarn, GstarContentsWarnPK> {

	
}