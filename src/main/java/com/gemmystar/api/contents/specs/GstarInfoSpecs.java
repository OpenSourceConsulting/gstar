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
 * BongJin Kwon		2016. 8. 12.		First Draft.
 */
package com.gemmystar.api.contents.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.contents.domain.GstarInfo;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
public class GstarInfoSpecs {


	/**
	 * <pre>
	 * 포인트가 가장 높은 GstarInfo
	 * </pre>
	 * @param gstarRoomId
	 * @return
	 */
	public static Specification<GstarInfo> topGstarInfos(final Long gstarRoomId) {
			
		return new Specification<GstarInfo>() {

			@Override
			public Predicate toPredicate(Root<GstarInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				//Join<GstarInfo, GstarContents> contentsJoin = root.join("gstarContents");
				
				Subquery<Long> maxSubquery = query.subquery(Long.class);
				Root<GstarInfo> subRoot = maxSubquery.from(GstarInfo.class);
				
				Join<GstarInfo, GstarContents> subJoin = subRoot.join("gstarContents");
				maxSubquery.select(cb.max(subRoot.<Long>get("pointCnt")));
				maxSubquery.where(cb.equal(subJoin.get("gstarRoomId"), gstarRoomId));
				
				
				return cb.and(cb.equal(root.get("gstarContents").get("gstarRoomId"), gstarRoomId), cb.equal(root.get("pointCnt"), maxSubquery));// cross join
				//return cb.and(cb.equal(contentsJoin.get("gstarRoomId"), gstarRoomId), cb.equal(root.get("pointCnt"), maxSubquery)); // inner join
			}
			
		};
	}

}
//end of GstarInfoSpecs.java