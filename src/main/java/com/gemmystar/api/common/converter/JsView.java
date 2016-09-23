package com.gemmystar.api.common.converter;

/**
 * json 변환시 노출 항목을 정한다.
 * @see https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring
 * @author Administrator
 *
 */
public class JsView {
	
	/**
	 * 개인정보 제외.
	 * @author Administrator
	 *
	 */
	public interface UserPublic {}
	
	/**
	 * 게시판 목록 조회시 노출 항목
	 * @author Administrator
	 *
	 */
	public interface BordList extends UserPublic {}
	
	public interface BordAll extends BordList {}
}
