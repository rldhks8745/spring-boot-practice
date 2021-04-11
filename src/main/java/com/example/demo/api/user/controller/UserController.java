package com.example.demo.api.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.user.model.User;
import com.example.demo.api.user.service.UserService;

@RestController
@RequestMapping(name = "/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<User> selectUserList() {
		return userService.selectUserList();
	}
}
