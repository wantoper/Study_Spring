package com.wantoper.mapper;

import com.wantoper.pojo.User;

import java.util.List;

public interface UserMapper {
    public List<User> selectuser();

    public void adduser(User u);

    public int deleteuser(int id);
}
