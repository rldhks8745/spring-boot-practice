package com.example.demo.api.user.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.api.user.mapper.UserMapper;
import com.example.demo.api.user.model.User;
import com.example.demo.api.user.model.UserRequest;
import com.example.demo.config.mybatis.model.PagableResponse;

@Service
public class UserService {

  @Autowired
  private UserMapper userMapper;

  public PagableResponse<User> selectUserList(UserRequest userRequest) {
    PagableResponse<User> p = userMapper.selectUserList(userRequest);
    return p;
  }

  public List<User> selectUserList2(UserRequest userRequest) {
    return userMapper.selectUserList2(userRequest);
  }

  public User selectOneUser(String id) {
    User user = new User();
    user.setId(id);
    return userMapper.selectOneUser(id, 1);
  }
}
