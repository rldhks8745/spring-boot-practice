package com.example.demo.api.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.api.user.mapper.UserMapper;
import com.example.demo.api.user.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public List<User> selectUserList() {
		return userMapper.selectUserList();
	}
}
