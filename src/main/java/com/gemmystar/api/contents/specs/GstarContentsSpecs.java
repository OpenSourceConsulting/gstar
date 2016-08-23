
package com.gemmystar.api.contents.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.point.domain.GstarPointHistory;
import com.gemmystar.api.room.domain.GstarRoomContents;
import com.gemmystar.api.tag.domain.GstarHashTag;
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
				
				return cb.equal(root.<String>get(GemmyConstant.CONTENTS_ATTR_MEMBER_TYPE_CD), GemmyConstant.CODE_MEMBER_TYPE_MASTER);
			}
			
		};
	}

	public static Specification<GstarContents> search(final String search) {
		
		return new Specification<GstarContents>() {

			@Override
			public Predicate toPredicate(Root<GstarContents> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Join<GstarContents, GstarHashTag> tagJoin = root.join("gstarHashTags");
				
				return cb.or(cb.like(root.<String>get("subject"), search +"%"), cb.like(tagJoin.<String>get("tag"), search +"%"));
			}
			
		};
	}
	
	public static Specification<GstarContents> recommands() {
		
		return new Specification<GstarContents>() {

			@Override
			public Predicate toPredicate(Root<GstarContents> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.and(cb.equal(root.<String>get(GemmyConstant.CONTENTS_ATTR_DIV_CD), GemmyConstant.CODE_CNTS_DIV_RECOMMAND), 
						cb.notEqual(root.<String>get(GemmyConstant.CONTENTS_ATTR_STATUS_CD), GemmyConstant.CODE_CNTS_STATUS_CLOSED));
			}
			
		};
	}

	/**
	 * <pre>
	 * 일반 동영상
	 * </pre>
	 * @return
	 */
	public static Specification<GstarContents> notMatching() {
		
		return new Specification<GstarContents>() {

			@Override
			public Predicate toPredicate(Root<GstarContents> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.and(cb.isNull(root.<Long>get("gstarRoomId")), 
						cb.notEqual(root.<String>get(GemmyConstant.CONTENTS_ATTR_STATUS_CD), GemmyConstant.CODE_CNTS_STATUS_CLOSED));
			}
			
		};
	}
	
	public static Specification<GstarContents> myContents(final Long userId) {
		
		return new Specification<GstarContents>() {

			@Override
			public Predicate toPredicate(Root<GstarContents> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.equal(root.<GstarUser>get("gstarUser").get("id"), userId);
			}
			
		};
	}
	
	public static Specification<GstarContents> myHeartContents(final Long userId) {
		
		return new Specification<GstarContents>() {

			@Override
			public Predicate toPredicate(Root<GstarContents> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Join<GstarContents, GstarPointHistory> pointJoin = root.join("gstarPointHistories");
				
				return cb.equal(pointJoin.<Long>get("gstarUserId"), userId);
			}
			
		};
	}
	
	/**
	 * <pre>
	 * gstarRoomId 에서 5번 우승자 찾기.
	 * </pre>
	 * @param gstarRoomId
	 * @return
	 */
	public static Specification<GstarContents> fiveWinner(final Long gstarRoomId) {
		
		return new Specification<GstarContents>() {

			@Override
			public Predicate toPredicate(Root<GstarContents> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.and( cb.equal(root.<Long>get("gstarRoomId"), gstarRoomId), cb.equal(root.get("gstarInfo").<Integer>get("victoryCnt"), 5));
			}
			
		};
	}
	
	public static Specification<GstarContents> challengers(final Long gstarRoomId) {
		
		return new Specification<GstarContents>() {

			@Override
			public Predicate toPredicate(Root<GstarContents> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Join<GstarContents, GstarRoomContents> join = root.join("gstarRommContentsList");
				
				return cb.and( cb.equal(root.get("memberTypeCd"), GemmyConstant.CODE_MEMBER_TYPE_CHALLENGER), 
						cb.equal(join.<Long>get("gstarRoomId"), gstarRoomId));
			}
			
		};
	}
	
	/*
	public static Specification<GstarContents> eqCode(final String codeAttributeName, final String code) {
		
		return new Specification<GstarContents>() {

			@Override
			public Predicate toPredicate(Root<GstarContents> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.equal(root.<String>get(codeAttributeName), code);
			}
			
		};
	}
	*/
	
}
//end of GstarContentsSpecs.java