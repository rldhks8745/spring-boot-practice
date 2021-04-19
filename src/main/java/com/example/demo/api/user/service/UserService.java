package com.example.demo.api.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.api.user.mapper.UserMapper;
import com.example.demo.api.user.model.User;
import com.example.demo.api.user.model.UserSearch;
import com.example.demo.config.mybatis.model.PagableResponse;

@Service
public class UserService {

  @Autowired
  private UserMapper userMapper;

  public PagableResponse<User> selectUserList(UserSearch userSearch) {
    PagableResponse<User> p = userMapper.selectUserList(userSearch);
    return p;
  }
}
