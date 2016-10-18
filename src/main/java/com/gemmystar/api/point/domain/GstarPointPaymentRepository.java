package com.gemmystar.api.point.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * GstarPointPaymentRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarPointPaymentRepository extends JpaRepository<GstarPointPayment, Integer>, JpaSpecificationExecutor<GstarPointPayment> {

	
}