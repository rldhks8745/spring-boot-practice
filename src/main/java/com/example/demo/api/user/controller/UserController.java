package com.example.demo.api.user.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.api.user.model.User;
import com.example.demo.api.user.model.UserSearch;
import com.example.demo.api.user.service.UserService;
import com.example.demo.config.mybatis.model.PagableResponse;

@RestController
@RequestMapping(path = "/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public PagableResponse<User> selectListUser(@ModelAttribute @Valid UserSearch userSearch) {
    return userService.selectListUser(userSearch);
  }
  
  @GetMapping("/{num}")
  public User selectOneUser(@PathVariable(value = "num") Long num) {
    return userService.selectOneUser(num);
  }
  
  @PostMapping
  public Long insertUser(@ModelAttribute User user) {
    return userService.insertUser(user);
  }
  
  @PutMapping
  public void updateUser(@ModelAttribute User user) throws Exception {
    userService.updateUser(user);
  }
}
