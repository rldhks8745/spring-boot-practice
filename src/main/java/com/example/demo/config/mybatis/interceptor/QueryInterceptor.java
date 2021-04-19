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


/**
 * @Package : com.example.demo.config.mybatis.interceptor
 * @FileName : QueryInterceptor.java
 * @CreateDate : 2021. 4. 19. 
 * @author : Morian
 * @Description : Mybatis가 Interceptor를 상속받은 @Component에 대해서 AutoConfiguration을 해준다.
 * 
 */
 
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "query",
    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class QueryInterceptor implements Interceptor {

  private static String COUNT_ID_SUFFIX = "-Long";
  
  /**
   * @Method : createCountResultMaps
   * @CreateDate : 2021. 4. 19. 
   * @param ms
   * @return
   * @Description : COUNT QUERY 결과를 받기위한 ResultMaps 생성
   */
  private List<ResultMap> createCountResultMaps(MappedStatement ms) {
    List<ResultMap> countResultMaps = new ArrayList<>();
    
    ResultMap countResultMap =
        new ResultMap.Builder(ms.getConfiguration(), ms.getId() + COUNT_ID_SUFFIX, Long.class, new ArrayList<>())
            .build();
    countResultMaps.add(countResultMap);
    
    return countResultMaps;
  }

  
  /**
   * @Method : createCountMappedStatement
   * @CreateDate : 2021. 4. 19. 
   * @param ms
   * @return
   * @Description : COUNT QUERY 결과를 받기위한 MappedStatement 생성
   *                속도문제로 개선필요 시 간단히 Map으로 캐싱처리해도 될듯
   */
  private MappedStatement createCountMappedStatement(MappedStatement ms) {
    List<ResultMap> countResultMaps = createCountResultMaps(ms);
    
     return new MappedStatement.Builder(ms.getConfiguration(), ms.getId() + COUNT_ID_SUFFIX,
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
       .useCache(true)
       .resultOrdered(ms.isResultOrdered())
       .keyGenerator(ms.getKeyGenerator())
       .keyColumn(ms.getKeyColumns() != null ? String.join(",", ms.getKeyColumns()) : null)
       .keyProperty(ms.getKeyProperties() != null ? String.join(",", ms.getKeyProperties()): null)
       .databaseId(ms.getDatabaseId())
       .lang(ms.getLang())
       .resultSets(ms.getResultSets() != null ? String.join(",", ms.getResultSets()): null)
     .build();
  }
  
  
  /**
   * @Method : createPagableResponse
   * @CreateDate : 2021. 4. 19. 
   * @param list
   * @param pageInfo
   * @return
   * @Description : 최종적으로 return할 PagableResponse 생성
   */
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
      
      log.debug("■■ QueryInterceptor intercept: Request Parameter가 PageInfo.class를 상속 ■■");

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
