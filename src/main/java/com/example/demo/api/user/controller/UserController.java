package com.example.demo.api.user.controller;

import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.user.model.User;
import com.example.demo.api.user.service.UserService;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public Cursor<User> selectUserList() {
		return userService.selectUserList();
	}

	@GetMapping(value = "/{id}")
	public User selectUserList(@PathVariable(name = "id") String id) {
		return userService.selectOneUser(id);
	}
}
