package com.example.demo.config.mybatis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.demo.config.mybatis.interceptor.PreparetInterceptor;
import com.example.demo.config.mybatis.interceptor.QueryInterceptor;

@Configuration
public class MybatisConfig {
  
  @Bean
  public PreparetInterceptor preparetInterceptor() {
    return new PreparetInterceptor();
  }
  
  @Bean
  public QueryInterceptor queryInterceptor() {
    return new QueryInterceptor();
  }
}
