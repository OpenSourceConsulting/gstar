package com.gemmystar.api.user.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession;

    public String getCurrentDateTime() {

        return sqlSession.selectOne("com.gemmystar.api.user.UserMapper.getCurrentDateTime");
    }
    
    public String updateProfileImageUrl(Long gstarUserId, String imgUrl) {
    	Map<String, Object> parameters = new HashMap<String, Object>();
    	parameters.put("gstarUserId", gstarUserId);
    	parameters.put("imgUrl", imgUrl);
    	sqlSession.update("updateProfileImg", parameters);
    	return imgUrl;
    }
}