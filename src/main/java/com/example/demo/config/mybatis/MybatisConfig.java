package com.example.demo.config.mybatis;

import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.demo.config.mybatis.interceptor.QueryInterceptor;
import com.example.demo.config.mybatis.interceptor.StatementInterceptor;

@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
public class MybatisConfig {
  
  @Bean
  public StatementInterceptor statementInterceptor() {
    return new StatementInterceptor();
  }
  
  @Bean
  public QueryInterceptor queryInterceptor() {
    return new QueryInterceptor();
  }
}
