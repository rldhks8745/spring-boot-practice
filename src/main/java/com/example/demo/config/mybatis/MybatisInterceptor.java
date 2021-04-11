package com.example.demo.config.mybatis;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("interceptor test");
		return invocation.proceed();
	}

}
