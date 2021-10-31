package com.wantoper.mapper;

import com.wantoper.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper{
    @Override
    public List<User> selectuser() {
        return getSqlSession().getMapper(UserMapper.class).selectuser();
    }

    @Override
    public void adduser(User u) {
        getSqlSession().getMapper(UserMapper.class).adduser(u);
    }

    @Override
    public int deleteuser(int id) {
        return getSqlSession().getMapper(UserMapper.class).deleteuser(id);
    }


}
