package com.example.demo.config.mybatis.model;

import lombok.Data;

/**
 * @Package : com.example.demo.config.mybatis.model
 * @FileName : PageInfo.java
 * @CreateDate : 2021. 4. 12. 
 * @author : Morian
 * @Description : 페이징 처리 상속 모델
 */
 
@Data
public class PageInfo {
  
  protected Integer page = 1;

  protected Integer size = 10;

  protected Integer resultCount = 0;

  protected Integer totalCount = 0;

}
