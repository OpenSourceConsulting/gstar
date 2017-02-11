package com.gemmystar.api.contents.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.contents.GstarContentsController;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.contents.domain.GstarInfo;
import com.gemmystar.api.tag.domain.GstarHashTag;
import com.gemmystar.api.user.domain.GstarUser;

@Repository
public class GstarContentsDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GstarContentsDao.class);

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession;

    public String getCurrentDateTime() {

        return sqlSession.selectOne("com.gemmystar.api.user.UserMapper.getCurrentDateTime");
    }

	public Page<GstarContents> getUserLikeGstarContentsList(Pageable pageable, Long gstarUserId) {
		List<GstarContents> list = sqlSession.selectList("likeList", gstarUserId);
		
		for(GstarContents content : list) {
			List<GstarHashTag> tagList = getHashTagByContentsId(content.getId());
			content.setGstarHashTags(tagList);
			
			content.setGstarUser(getContentsCreator(content.getUserId()));
		}
		Page<GstarContents> page = new PageImpl<GstarContents>(list);
		
		return page;
	}
	
	public Page<GstarContents> getSearchContentsList(Pageable pageable, String search) {
		
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("search", search);
		
		if( pageable.getPageNumber() == 0) {
			params.put("start", 0);
			params.put("end", pageable.getPageSize());
		} else {
			params.put("start", pageable.getPageNumber() * pageable.getPageSize());
			params.put("end", (pageable.getPageNumber() + 1) * pageable.getPageSize());
		}
		
		int count = getSearchContentCount(search);

		List<GstarContents> list = sqlSession.selectList("searchList", params);
		
		for(GstarContents content : list) {
			List<GstarHashTag> tagList = getHashTagByContentsId(content.getId());
			content.setGstarHashTags(tagList);
			
			content.setGstarUser(getContentsCreator(content.getUserId()));
			
			content.setGstarInfo(getGstarInfo(content.getId()));
		}
		Page<GstarContents> page = new PageImpl<GstarContents>(list, pageable, count);
		
		return page;
	}
	
	public int getSearchContentCount(String search) {
		Map<String, Object> params = new HashMap<String, Object>(); 
		params.put("search", search);
		return sqlSession.selectOne("searchListCount", params);
	}
	
	public GstarInfo getGstarInfo(Long gstarContentsId) {
		return sqlSession.selectOne("getGstarInfo", gstarContentsId);
	}

	/**
	 * gstar_content_id에 대응하는 hash tag의 이름을 반환한다
	 * @param gstarContentsId
	 * @return
	 */
	public List<GstarHashTag> getHashTagByContentsId(Long gstarContentsId) {
		List<GstarHashTag> list = sqlSession.selectList("hashTagList", gstarContentsId);
		return list;
	}
	
	public GstarUser getContentsCreator(Long gstarUserId) {
		GstarUser user = sqlSession.selectOne("getContentsCreator", gstarUserId);
		return user;		
	}
}