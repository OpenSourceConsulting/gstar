package com.gemmystar.api.contents.specs;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.contents.domain.QGstarContents;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

public class GstarContentsPredicate {

	public static Predicate recommands(boolean isAll) {
		QGstarContents contents = QGstarContents.gstarContents;
		
		BooleanBuilder predicate = new BooleanBuilder();
		
		predicate.and(contents.divCd.eq(GemmyConstant.CODE_CNTS_DIV_RECOMMAND));
		if (isAll) {
			predicate.and(contents.statusCd.ne(GemmyConstant.CODE_CNTS_STATUS_CLOSED));
		}
		
		return predicate;
	}
}
