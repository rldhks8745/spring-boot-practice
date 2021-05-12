package com.example.demo.config.quartz;

import java.util.Properties;
import javax.sql.DataSource;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import com.example.demo.config.quartz.bean.AutowiringSpringBeanJobFactory;

@Configuration
public class QuartzConfig {

  @Autowired
  QuartzProperties quartzProperties;
  
  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext, DataSource dataSource) {
    SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    
    schedulerFactoryBean.setJobFactory(jobFactory);
    schedulerFactoryBean.setApplicationContext(applicationContext);
    schedulerFactoryBean.setDataSource(dataSource);

    Properties properties = new Properties();
    properties.putAll(quartzProperties.getProperties());

//    schedulerFactoryBean.setGlobalTriggerListeners(triggersListener);
//    schedulerFactoryBean.setGlobalJobListeners(jobsListener);
    
    schedulerFactoryBean.setQuartzProperties(properties);
    return schedulerFactoryBean;
  }
  
  @Bean Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
    return schedulerFactoryBean.getScheduler();
  }
}
