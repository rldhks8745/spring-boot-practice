package com.example.demo.api.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.api.user.model.User;
import com.example.demo.api.user.model.UserSearch;
import com.example.demo.config.mybatis.model.PagableResponse;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@TestMethodOrder(OrderAnnotation.class)
public class UserServiceTests {

  @Autowired
  private UserService userService;
  
  @Test
  @DisplayName("page, size 지정")
  @Order(1)
  void selectUserListTestWithPageAndSize() {
    log.debug("■■■ selectUserListTestWithPageAndSize Start ■■■■");
    UserSearch userSearch = new UserSearch();
    userSearch.setPage(1);
    userSearch.setSize(2);
    
    PagableResponse<User> returnObject = userService.selectListUser(userSearch);
    log.debug("list: "+returnObject.getList().toString());
    log.debug("pageInfo: "+returnObject.getPageInfo().toString());
    
    assertThat(returnObject.getList().size()).isEqualTo(2);
    assertThat(returnObject.getPageInfo().getTotalCount()).isEqualTo(3);
    log.debug("■■■ selectUserListTestWithPageAndSize End ■■■■");
  }
  
  @Test
  @DisplayName("insertUser")
  @Order(2)
  void insertUserTest() {
    User user = new User();
    user.setId("test4");
    user.setName("test4");
    user.setPw("test4");
    
    Long id = userService.insertUser(user);
    
    assertThat(id.equals(4));
  }

  @Test
  @DisplayName("page, size 미지정")
  @Order(3)
  void selectUserListTestWithoutPageAndSize() {
    log.debug("■■■ selectUserListTestWithoutPageAndSize Start ■■■■");
    UserSearch userSearch = new UserSearch();
    
    PagableResponse<User> returnObject = userService.selectListUser(userSearch);
    log.debug("list: "+returnObject.getList().toString());
    log.debug("pageInfo: "+returnObject.getPageInfo().toString());

    assertThat(returnObject.getList().size()).isEqualTo(4);
    assertThat(returnObject.getPageInfo().getPage()).isNull();
    assertThat(returnObject.getPageInfo().getSize()).isNull();
    log.debug("■■■ selectUserListTestWithoutPageAndSize End ■■■■");
  }

  @Test
  @DisplayName("updatetUser Test")
  @Order(4)
  void updateUserTest() throws Exception {
    User user = new User();
    user.setNum(4L);
    user.setId("test4updated");
    user.setName("test4updated");
    
    userService.updateUser(user);
    
    User updatedUser = userService.selectOneUser(user.getNum());
    log.debug(updatedUser.toString());
    
    assertThat(updatedUser.getId().equals("test4updated"));
    assertThat(updatedUser.getPw().equals("test4"));
  }
}
