package com.example.demo.api.user.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.api.user.model.User;
import com.example.demo.api.user.model.UserRequest;
import com.example.demo.api.user.service.UserService;
import com.example.demo.config.mybatis.model.PagableResponse;

@RestController
@RequestMapping(path = "/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public PagableResponse<User> selectUserList(@ModelAttribute @Valid UserRequest userRequest) {
    return userService.selectUserList(userRequest);
  }

  @GetMapping(value = "/2")
  public List<User> selectUserList2(@ModelAttribute UserRequest userRequest) {
    return userService.selectUserList2(userRequest);
  }

  @GetMapping(value = "/{id}")
  public User selectUserList(@PathVariable(name = "id") String id) {
    return userService.selectOneUser(id);
  }

  @GetMapping(value = "/exception")
  public User exception() throws Exception {
    throw new Exception("exception test");
  }
}
