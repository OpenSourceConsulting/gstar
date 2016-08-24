/* 
 * Copyright (C) 2012-2015 Open Source Consulting, Inc. All rights reserved by Open Source Consulting, Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * Revision History
 * Author			Date				Description
 * ---------------	----------------	------------
 * BongJin Kwon		2016. 8. 24.		First Draft.
 */
package com.gemmystar.api.point.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.gemmystar.api.point.domain.GstarUserPoint;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
public class GstarUserPointSpecs {

	public static Specification<GstarUserPoint> myPonts(final Long gstarUserId) {
		
		return new Specification<GstarUserPoint>() {

			@Override
			public Predicate toPredicate(Root<GstarUserPoint> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.equal(root.<Long>get("gstarUserId"), gstarUserId);
			}
			
		};
	}
	
	public static Specification<GstarUserPoint> eqStatus(final String pcStatusCd) {
		
		return new Specification<GstarUserPoint>() {

			@Override
			public Predicate toPredicate(Root<GstarUserPoint> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.equal(root.<String>get("pcStatusCd"), pcStatusCd);
			}
			
		};
	}

}
//end of GstarUserPointSpecs.java