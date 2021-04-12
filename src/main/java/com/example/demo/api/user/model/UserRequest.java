package com.example.demo.api.user.model;

import com.example.demo.config.mybatis.model.PageInfo;
import lombok.Data;

@Data
public class UserRequest extends PageInfo {

  private String id;
  
  private String name;
  
}
