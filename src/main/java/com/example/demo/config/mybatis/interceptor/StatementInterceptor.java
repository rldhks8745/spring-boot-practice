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
import org.apache.ibatis.session.RowBounds;
import com.example.demo.config.mybatis.model.PageInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Intercepts({
  @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class StatementInterceptor implements Interceptor {

  private static final DefaultObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
  private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY =
      new DefaultObjectWrapperFactory();
  private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    
    try {
      StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
      MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
          DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
      String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
      PageInfo pageInfo = (PageInfo) metaStatementHandler.getValue("delegate.rowBounds");

      // 원래 Query에 LIMIT 문을 붙여준다.
      StringBuilder sb = new StringBuilder(originalSql);
      sb.append(" LIMIT ");
      sb.append((pageInfo.getPage() - 1) * pageInfo.getSize());
      sb.append(", ");
      sb.append(pageInfo.getSize());

      log.debug("sql = {}", sb.toString());

      // RowBounds 정보 제거
      metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
      metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
      
      // 변경된 쿼리로 바꿔치기
      metaStatementHandler.setValue("delegate.boundSql.sql", sb.toString());
    } catch (ClassCastException e) {
      log.error("매개변수가 PageInfo.class를 상속받지 않았습니다.");
    }

    return invocation.proceed();
  }
}
