package com.example.demo.config.mybatis.model;

import java.util.ArrayList;
import lombok.Data;


/**
 * @Package : com.oneplat.ecommerce.common.model
 * @FileName : AbstractPagableResponse.java
 * @CreateDate : 2021. 4. 9.
 * @author : 82105
 * @param <M>
 * @Description : 페이지 정보를 가지고 있는 model response
 */

@Data
public class PagableResponse<T> extends ArrayList<T> {
  private static final long serialVersionUID = 1L;
  
  private PageInfo pageInfo;
}
