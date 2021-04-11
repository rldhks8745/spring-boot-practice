package com.example.demo.api.user.service;

import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.api.user.mapper.UserMapper;
import com.example.demo.api.user.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public Cursor<User> selectUserList() {
		return userMapper.selectUserList();
	}
	
	public User selectOneUser(String id) {
		User user = new User();
		user.setId(id);
		return userMapper.selectOneUser(id, 1);
	}
}
