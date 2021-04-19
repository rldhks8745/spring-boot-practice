package com.example.demo.config.mybatis.interceptor;

import java.sql.Connection;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import com.example.demo.config.mybatis.model.PageInfo;
import lombok.extern.slf4j.Slf4j;


/**
 * @Package : com.example.demo.config.mybatis.interceptor
 * @FileName : StatementInterceptor.java
 * @CreateDate : 2021. 4. 19. 
 * @author : Morian
 * @Description : Mybatis가 Interceptor를 상속받은 @Component에 대해서 AutoConfiguration을 해준다.
 */
 
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare",
    args = {Connection.class, Integer.class})})
public class StatementInterceptor implements Interceptor {

  private static final DefaultObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
  private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY =
      new DefaultObjectWrapperFactory();
  private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

  
  /**
   * @Method : attachLimitQuery
   * @CreateDate : 2021. 4. 19. 
   * @param originalSql
   * @param pageInfo
   * @return
   * @Description : Query에 LIMIT 붙이기
   */
  private String attachLimitQuery(String originalSql, PageInfo pageInfo) {
    StringBuilder sb = new StringBuilder(originalSql);
    sb.append(" LIMIT ");
    sb.append((pageInfo.getPage() - 1) * pageInfo.getSize());
    sb.append(", ");
    sb.append(pageInfo.getSize());

    String attachLimitQuery = sb.toString();
    return attachLimitQuery;
  }

  
  /**
   * @Method : attachCountQuery
   * @CreateDate : 2021. 4. 19. 
   * @param originalSql
   * @param pageInfo
   * @return
   * @Description : Query에 COUNT 구하는 Wrapper Query 붙이기
   */
  private String attachCountQuery(String originalSql, PageInfo pageInfo) {
    StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ( ");
    sb.append(originalSql);
    sb.append(" ) COUNT_TABLE ");

    String attachedCountQuery = sb.toString();
    return attachedCountQuery;
  }
  
  
  /**
   * @Method : isNullPageInfo
   * @CreateDate : 2021. 4. 19. 
   * @param pageInfo
   * @return
   * @Description : page, size가 없는지 체크
   */
  private boolean isNullPageInfo(PageInfo pageInfo) {
    return pageInfo.getPage() == null || pageInfo.getSize() == null;
  }
  
  
  /**
   * @Method : attachQuery
   * @CreateDate : 2021. 4. 19. 
   * @param originalSql
   * @param pageInfo
   * @return
   * @Description : 조건에 따라 Query 붙이기
   */
  private String attachQuery(String originalSql, PageInfo pageInfo) {
    if (pageInfo.getTotalCount() == -1)
      return attachCountQuery(originalSql, pageInfo);
    
    if (!isNullPageInfo(pageInfo))
      return attachLimitQuery(originalSql, pageInfo);
    
    return originalSql;
  }

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    try {
      StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
      MetaObject metaStatementHandler = MetaObject.forObject(statementHandler,
          DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);

      PageInfo pageInfo = (PageInfo) metaStatementHandler.getValue("delegate.rowBounds");
      String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");

      // limit 추가된 query
      metaStatementHandler.setValue("delegate.boundSql.sql", attachQuery(originalSql, pageInfo));
    } catch (ClassCastException e) {}

    return invocation.proceed();
  }
}
