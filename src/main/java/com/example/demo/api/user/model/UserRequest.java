package com.example.demo.api.user.model;

import com.example.demo.config.mybatis.model.PageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserRequest extends PageInfo {

  private String id;
  
  private String name;

}
