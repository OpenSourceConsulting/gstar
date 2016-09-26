package com.gemmystar.api.tabmenu.domain;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * GstarTabMenuRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarTabMenuRepository extends JpaRepository<GstarTabMenu, Integer>, JpaSpecificationExecutor<GstarTabMenu> {

	List<GstarTabMenu> findByHiddenOrderByOrderSeqAsc(boolean hidden);
}