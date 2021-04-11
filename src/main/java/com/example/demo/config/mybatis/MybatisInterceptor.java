package com.example.demo.config.mybatis;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class MybatisInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("interceptor test");

		MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
		
		RowBounds limitRowBounds = (RowBounds) invocation.getArgs()[2];
		
		Object returnObject = invocation.proceed();
		
		try {
			RowBounds totalRowBounds = new RowBounds();
			
			invocation.getArgs()[2] = totalRowBounds;
			List<?> totalObject = (List<?>) invocation.proceed();
			int totalCount = totalObject.size(); 
			System.out.println(totalCount);
		} catch (Exception e) {
			// List형식이 아니면 totalRowBounds가 필요없음
		}
		
		return returnObject;
	}

	@Override
	public void setProperties(Properties properties) {
		System.out.println(properties);
	}

}
