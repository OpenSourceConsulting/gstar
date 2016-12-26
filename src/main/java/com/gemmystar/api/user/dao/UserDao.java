package com.gemmystar.api.user.dao;

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
}