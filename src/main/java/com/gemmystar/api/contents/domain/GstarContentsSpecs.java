
package com.gemmystar.api.contents.domain;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.gemmystar.api.user.domain.GstarUser;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
public class GstarContentsSpecs {
	
	public static Specification<GstarContents> eqMaster() {
		
		return new Specification<GstarContents>() {

			@Override
			public Predicate toPredicate(Root<GstarContents> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.equal(root.<String>get("memberTypeCd"), "1");
			}
			
		};
	}

	public static Specification<GstarContents> likeSubject(final String search) {
		
		return new Specification<GstarContents>() {

			@Override
			public Predicate toPredicate(Root<GstarContents> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.like(root.<String>get("subject"), search +"%");
			}
			
		};
	}
	
	public static Specification<GstarContents> myContents(final Long userId) {
		
		return new Specification<GstarContents>() {

			@Override
			public Predicate toPredicate(Root<GstarContents> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				root.join("gstarInfo");
		
				return cb.equal(root.<GstarUser>get("gstarUser").get("id"), userId);
			}
			
		};
	}

}
//end of GstarContentsSpecs.java