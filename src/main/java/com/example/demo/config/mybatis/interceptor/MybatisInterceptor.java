package com.example.demo.config.mybatis.interceptor;

import java.lang.reflect.Field;
import java.util.List;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import com.example.demo.config.mybatis.model.PageInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "query",
    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisInterceptor implements Interceptor {

  
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    System.out.println("interceptor test");

    MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
    Class<?> cls = ms.getParameterMap().getType();

    if (isInheritedPageInfo(cls)) {
      log.info("{}는 {}를 상속받고 있습니다.", cls, PageInfo.class);

      PageInfo pageInfo = (PageInfo) invocation.getArgs()[1];
      RowBounds limitRowBounds = (RowBounds) invocation.getArgs()[2];
      DynamicSqlSource sqlSource = (DynamicSqlSource) ms.getSqlSource();

      Field field = sqlSource.getClass().getDeclaredField("rootSqlNode");
      field.setAccessible(true);
      MixedSqlNode sqlNode = (MixedSqlNode) field.get(sqlSource);

      field = sqlNode.getClass().getDeclaredField("contents");
      field.setAccessible(true);
      List contents = (List) field.get(sqlNode);

      StaticTextSqlNode additionalPagingQuery = new StaticTextSqlNode(String.format("""
          \nLIMIT %d OFFSET %d
         """, pageInfo.getSize(), (pageInfo.getPage() - 1) * pageInfo.getSize()));
      
      if(!contents.get(contents.size()-1).equals(additionalPagingQuery)) {
        contents.add(additionalPagingQuery); 
      }

      System.out.println("ddd");
    }

    RowBounds limitRowBounds = (RowBounds) invocation.getArgs()[2];

    // invocation.getArgs()[3] = new PagableResponseResulthandler();

    // PagableResponseResulthandler resultHandler = (PagableResponseResulthandler)
    // invocation.getArgs()[3];
    Object returnObject = invocation.proceed();

    try {
      RowBounds totalRowBounds = new RowBounds();

      invocation.getArgs()[2] = totalRowBounds;
      List totalObject = (List) invocation.proceed();
      int totalCount = totalObject.size();
      log.info(totalCount + "");
    } catch (Exception e) {
      // List형식이 아니면 totalRowBounds가 필요없음
    }

    return returnObject;
  }

  private boolean isInheritedPageInfo(Class<?> cls) {
    while (true) {
      Class<?> superCls = cls.getSuperclass();
      if (Object.class.equals(superCls))
        break;

      if (cls.getSuperclass().equals(PageInfo.class))
        return true;
      else
        return isInheritedPageInfo(cls.getSuperclass());
    }

    return false;
  }

}
