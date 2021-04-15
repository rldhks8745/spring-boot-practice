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

@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare",
    args = {Connection.class, Integer.class})})
public class StatementInterceptor implements Interceptor {

  private static final DefaultObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
  private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY =
      new DefaultObjectWrapperFactory();
  private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

  private String attachLimitQuery(String originalSql, PageInfo pageInfo) {
    StringBuilder sb = new StringBuilder(originalSql);
    sb.append(" LIMIT ");
    sb.append((pageInfo.getPage() - 1) * pageInfo.getSize());
    sb.append(", ");
    sb.append(pageInfo.getSize());

    String attachLimitQuery = sb.toString();
    log.debug(attachLimitQuery);
    return attachLimitQuery;
  }

  private String attachCountQuery(String originalSql, PageInfo pageInfo) {
    StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ( ");
    sb.append(originalSql);
    sb.append(" ) COUNT ");

    String attachedCountQuery = sb.toString();
    log.debug(attachedCountQuery);
    return attachedCountQuery;
  }

  private String attachQuery(String originalSql, PageInfo pageInfo) {
    if (pageInfo.getTotalCount() != -1)
      return attachLimitQuery(originalSql, pageInfo);
    else
      return attachCountQuery(originalSql, pageInfo);
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
