
package com.gemmystar.api.room.domain;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
public class GstarRoomSpecs {
	
	public static Specification<GstarRoom> likeSubject(final String search) {
		
		return new Specification<GstarRoom>() {

			@Override
			public Predicate toPredicate(Root<GstarRoom> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.like(root.<String>get("subject"), search +"%");
			}
			
		};
	}

}
//end of GstarRoomSpecs.java