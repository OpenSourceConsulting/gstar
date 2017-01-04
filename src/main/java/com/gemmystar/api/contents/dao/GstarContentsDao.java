package com.gemmystar.api.contents.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.tag.domain.GstarHashTag;
import com.gemmystar.api.user.domain.GstarUser;

@Repository
public class GstarContentsDao {

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