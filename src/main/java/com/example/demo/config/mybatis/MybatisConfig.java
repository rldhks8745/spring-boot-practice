package com.example.demo.config.mybatis;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
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
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisProperties properties)
      throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setConfiguration(properties.getConfiguration());
    // sqlSessionFactoryBean.setTypeHandlersPackage("com.example.demo.config.mybatis.handler.*");
    sqlSessionFactoryBean.setPlugins(new StatementInterceptor(), new QueryInterceptor());
    return sqlSessionFactoryBean.getObject();
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
