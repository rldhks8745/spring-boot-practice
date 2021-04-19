package com.example.demo.config.mybatis.model;

import javax.validation.constraints.Min;
import org.apache.ibatis.session.RowBounds;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Package : com.example.demo.config.mybatis.model
 * @FileName : PageInfo.java
 * @CreateDate : 2021. 4. 12. 
 * @author : Morian
 * @Description : 페이징 처리 상속 모델 
 *  RowBounds는 StatementInterceptor에서 RowBounds를 가져오기위해 사용
 *  직접적인 접근은 할 수 없음
 *  그리고 return 되는 값에도 offset, limit는 제외
 */

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"offset", "limit"})
public class PageInfo extends RowBounds {
  
  protected Integer page;

  @Min(1)
  protected Integer size;

  protected Long totalCount = -1L; // set이 되지않았다는 의미로 -1
}
