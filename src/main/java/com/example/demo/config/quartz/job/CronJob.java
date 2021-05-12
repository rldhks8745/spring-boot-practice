package com.example.demo.config.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.example.demo.api.scheduler.repository.TriggersRepository;
import com.example.demo.api.scheduler.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CronJob extends QuartzJobBean {
  
  @Autowired
  SchedulerService schedulerService;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    JobKey jobKey = context.getJobDetail().getKey();

    log.info("{} 크론임 {}", schedulerService.selectTriggers().size(), jobKey);
  }
}
