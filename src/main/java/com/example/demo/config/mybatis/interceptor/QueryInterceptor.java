package com.example.demo.config.mybatis.interceptor;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import com.example.demo.config.mybatis.model.PagableResponse;
import com.example.demo.config.mybatis.model.PageInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "query",
    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class QueryInterceptor implements Interceptor {

  private List<ResultMap> createCountResultMaps(MappedStatement ms) {
    List<ResultMap> countResultMaps = new ArrayList<>();
    
    ResultMap countResultMap =
        new ResultMap.Builder(ms.getConfiguration(), ms.getId()+"-Long", Long.class, new ArrayList<>())
            .build();
    countResultMaps.add(countResultMap);
    
    return countResultMaps;
  }

  private MappedStatement createCountMappedStatement(MappedStatement ms) {
    List<ResultMap> countResultMaps = createCountResultMaps(ms);
    
     return new MappedStatement.Builder(ms.getConfiguration(), ms.getId()+"-Long",
       ms.getSqlSource(), ms.getSqlCommandType())
       .resource(ms.getResource())
       .parameterMap(ms.getParameterMap())
       .resultMaps(countResultMaps) 
       .fetchSize(ms.getFetchSize())
       .timeout(ms.getTimeout())
       .statementType(ms.getStatementType())
       .resultSetType(ms.getResultSetType())
       .cache(ms.getCache())
       .flushCacheRequired(ms.isFlushCacheRequired())
       .useCache(false) // Custom하게 만든거라 cache는 false
       .resultOrdered(ms.isResultOrdered())
       .keyGenerator(ms.getKeyGenerator())
       .keyColumn(ms.getKeyColumns() != null ? String.join(",", ms.getKeyColumns()) : null)
       .keyProperty(ms.getKeyProperties() != null ? String.join(",", ms.getKeyProperties()): null)
       .databaseId(ms.getDatabaseId())
       .lang(ms.getLang())
       .resultSets(ms.getResultSets() != null ? String.join(",", ms.getResultSets()): null)
     .build();
  }
  
  private PagableResponse<Object> createPagableResponse(List<Object> list, PageInfo pageInfo) {
    PagableResponse<Object> pagableResponse = new PagableResponse<>();
    pagableResponse.setList(list);
    pagableResponse.getPageInfo().setPage(pageInfo.getPage());
    pagableResponse.getPageInfo().setSize(pageInfo.getSize());
    pagableResponse.getPageInfo().setTotalCount(pageInfo.getTotalCount());
    
    return pagableResponse;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    try {
      PageInfo pageInfo = (PageInfo) invocation.getArgs()[2];
      
      log.debug("■■ QueryInterceptor intercept: Request Parameter가 PageInfo.class를 상속■■");

      MappedStatement oldMappedStatement = (MappedStatement) invocation.getArgs()[0];
      MappedStatement newMappedStatement = createCountMappedStatement(oldMappedStatement);
      
      // COUNT 구하기
      invocation.getArgs()[0] = newMappedStatement;
      List<Number> totalCount = (List<Number>) invocation.proceed();
      pageInfo.setTotalCount((Long) totalCount.get(0));

      // LIST 구하기
      invocation.getArgs()[0] = oldMappedStatement;
      List<Object> list = (List<Object>) invocation.proceed();

      return createPagableResponse(list, pageInfo);
    } catch (ClassCastException e) {}

    return invocation.proceed();
  }

}
