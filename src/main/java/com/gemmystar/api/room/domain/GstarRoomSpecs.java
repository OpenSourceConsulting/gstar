
package com.gemmystar.api.room.domain;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.tag.domain.GstarContentsTags;
import com.gemmystar.api.tag.domain.GstarHashTag;
import com.gemmystar.api.user.domain.GstarUser;

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
	
	public static Specification<GstarRoom> likeTag(final String search) {
		
		return new Specification<GstarRoom>() {

			@Override
			public Predicate toPredicate(Root<GstarRoom> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				//http://stackoverflow.com/questions/31841471/spring-data-jpa-specification-for-a-manytomany-unidirectional-relationship
				//Subquery<GstarHashTag> tagSubquery = query.subquery(GstarHashTag.class);
				//Root<GstarHashTag> hashTag = tagSubquery.from(GstarHashTag.class);
				//Expression<Collection<GstarContents>> contents = hashTag.get("gstarContentsList");
				
				Join<GstarRoom, GstarContents> contentsJoin = root.join("masterContents");
				Join<GstarContents, GstarHashTag> tagJoin = contentsJoin.join("gstarHashTags");
				
				return cb.like(tagJoin.<String>get("tag"), search +"%");
			}
			
		};
	}
	
	public static Specification<GstarRoom> myRoom(final Long userId) {
		
		return new Specification<GstarRoom>() {

			@Override
			public Predicate toPredicate(Root<GstarRoom> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Subquery<Long> contentsSubquery = query.subquery(Long.class);
				Root<GstarContents> contents = contentsSubquery.from(GstarContents.class);
				contentsSubquery.select(contents.<Long>get("gstarRoomId"));
				contentsSubquery.where(cb.equal(contents.<GstarUser>get("gstarUser").get("id"), userId));
				
				
				return cb.exists(contentsSubquery);
			}
			
		};
	}

}
//end of GstarRoomSpecs.java