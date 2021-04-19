package com.example.demo.api.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.api.user.mapper.UserMapper;
import com.example.demo.api.user.model.User;
import com.example.demo.api.user.model.UserSearch;
import com.example.demo.config.mybatis.model.PagableResponse;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class UserServiceTests {

  @Autowired
  private UserMapper userMapper;
  
  @Test
  @DisplayName("page, size 지정")
  void selectUserListTestWithPageAndSize() {
    log.debug("■■■ selectUserListTestWithPageAndSize Start ■■■■");
    UserSearch userSearch = new UserSearch();
    userSearch.setPage(1);
    userSearch.setSize(2);
    
    PagableResponse<User> returnObject = userMapper.selectUserList(userSearch);
    log.debug("list: "+returnObject.getList().toString());
    log.debug("pageInfo: "+returnObject.getPageInfo().toString());
    
    assertThat(returnObject.getList().size()).isEqualTo(2);
    assertThat(returnObject.getPageInfo().getTotalCount()).isEqualTo(3);
    log.debug("■■■ selectUserListTestWithPageAndSize End ■■■■");
  }

  @Test
  @DisplayName("page, size 미지정")
  void selectUserListTestWithoutPageAndSize() {
    log.debug("■■■ selectUserListTestWithoutPageAndSize Start ■■■■");
    UserSearch userSearch = new UserSearch();
    
    PagableResponse<User> returnObject = userMapper.selectUserList(userSearch);
    log.debug("list: "+returnObject.getList().toString());
    log.debug("pageInfo: "+returnObject.getPageInfo().toString());

    assertThat(returnObject.getList().size()).isEqualTo(3);
    assertThat(returnObject.getPageInfo().getPage()).isNull();
    assertThat(returnObject.getPageInfo().getSize()).isNull();
    log.debug("■■■ selectUserListTestWithoutPageAndSize End ■■■■");
  }
}
