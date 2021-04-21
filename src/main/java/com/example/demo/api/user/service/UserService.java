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

  public PagableResponse<User> selectListUser(UserSearch userSearch) {
    return userMapper.selectListUser(userSearch);
  }
  
  public User selectOneUser(Long num) {
    return userMapper.selectOneUser(num);
  }

  public Long insertUser(User user) {
    return userMapper.insertUser(user);
  }

  public void updateUser(User user) throws Exception {
    Long updatedCount = userMapper.updateUser(user);
    if(updatedCount <= 0)
      throw new Exception("is not updated");
  }
}
