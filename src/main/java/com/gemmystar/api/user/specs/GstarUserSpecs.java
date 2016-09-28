package com.gemmystar.api.user.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.user.domain.GstarUser;

public class GstarUserSpecs {

	public static Specification<GstarUser> members() {
		return new Specification<GstarUser>() {

			@Override
			public Predicate toPredicate(Root<GstarUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Join<GstarUser, GstarAccount> accountJoin = root.join("accounts");
				
				return cb.notEqual(accountJoin.<String>get("accountTypeCd"), GemmyConstant.CODE_ACCOUNT_TYPE_ADMIN);
			}
		};
	}
	
	public static Specification<GstarUser> admins() {
		return new Specification<GstarUser>() {

			@Override
			public Predicate toPredicate(Root<GstarUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Join<GstarUser, GstarAccount> accountJoin = root.join("accounts");
				
				return cb.equal(accountJoin.<String>get("accountTypeCd"), GemmyConstant.CODE_ACCOUNT_TYPE_ADMIN);
			}
		};
	}
	
	public static Specification<GstarUser> search(final String search) {
		return new Specification<GstarUser>() {

			@Override
			public Predicate toPredicate(Root<GstarUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.or(cb.like(root.<String>get("name"), "%" + search + "%"), cb.like(root.<String>get("nickname"), "%" + search +"%"));
			}
			
		};
	}
}
