package com.example.demo.api.user.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
  public PagableResponse<User> selectUserList(@ModelAttribute @Valid UserSearch userSearch) {
    return userService.selectUserList(userSearch);
  }
}
