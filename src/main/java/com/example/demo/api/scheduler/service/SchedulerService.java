package com.example.demo.api.scheduler.service;

import java.util.List;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.demo.api.scheduler.entity.CronTriggersEntity;
import com.example.demo.api.scheduler.entity.JobDetailsEntity;
import com.example.demo.api.scheduler.entity.TriggersEntity;
import com.example.demo.api.scheduler.model.QuartzJobDto;
import com.example.demo.api.scheduler.repository.CronTriggersRepository;
import com.example.demo.api.scheduler.repository.JobDetailsRepository;
import com.example.demo.api.scheduler.repository.TriggersRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchedulerService {

  static final String jobPackagePath = "com.example.demo.config.quartz.job.";

  @Autowired
  SchedulerFactoryBean schedulerFactory;

  @Autowired
  Scheduler scheduler;

  @Autowired
  JobDetailsRepository jobDetailsRepository;
  
  @Autowired
  CronTriggersRepository cronTriggersRepository;

  @Autowired
  TriggersRepository triggersRepository;

//  public CronTriggersEntity selcetCronTrigger() {
//    return cronTriggersRepository.findById();
//  }
  
  public List<CronTriggersEntity> selectCronTriggers() {
    return cronTriggersRepository.findAll();
  }

  public List<JobDetailsEntity> selectJobDetails() {
    return jobDetailsRepository.findAll();
  }
  
  public List<TriggersEntity> selectTriggers() {
    return triggersRepository.findAll();
  }
  
  /**
   * @Method : createJob
   * @CreateDate : 2021. 5. 11. 
   * @param job
   * @return
   * @throws SchedulerException
   * @throws ClassNotFoundException
   * @Description :
   */
  @SuppressWarnings("unchecked")
  public TriggerKey createJob(@ModelAttribute QuartzJobDto job)
      throws SchedulerException, ClassNotFoundException {
    JobKey jobKey = new JobKey(job.getJobName());
    TriggerKey triggerKey = new TriggerKey(job.getJobName() + "Trigger");

    log.info(String.format("jobKey: %s, triggerKey: %s", jobKey, triggerKey));

    JobDetail jobDetail = JobBuilder.newJob()
        .ofType((Class<? extends Job>) Class.forName(jobPackagePath + job.getJobClassName()))
        .withIdentity(jobKey).build();

    Trigger trigger = TriggerBuilder.newTrigger()
        .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
        .withIdentity(triggerKey).build();

    scheduler.scheduleJob(jobDetail, trigger);

    return triggerKey;
  }

  
  /**
   * @Method : modifyJob
   * @CreateDate : 2021. 5. 11. 
   * @param job
   * @return
   * @throws SchedulerException
   * @Description :
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public TriggerKey modifyJob(@ModelAttribute QuartzJobDto job) throws SchedulerException {
    Trigger trigger = getTrigger(job);
    
    Trigger modifiedTrigger = trigger.getTriggerBuilder().forJob(job.getJobName())
        .withSchedule((ScheduleBuilder) CronScheduleBuilder.cronSchedule(job.getCronExpression()))
        .build();
    
    scheduler.rescheduleJob(modifiedTrigger.getKey(), modifiedTrigger);
    
    return modifiedTrigger.getKey();
  }

  public boolean deleteJob(@ModelAttribute QuartzJobDto job) throws SchedulerException {
    return scheduler.deleteJob(job.createJobKey());
  }

  private Trigger getTrigger(QuartzJobDto job) throws SchedulerException {
    JobKey jobKey = new JobKey(job.getJobName());
    return scheduler.getTriggersOfJob(jobKey).get(0);
  }

  
  /**
   * @Method : resumeJob
   * @CreateDate : 2021. 5. 11. 
   * @param job
   * @return
   * @throws SchedulerException
   * @Description :
   */
  public int resumeJob(@ModelAttribute QuartzJobDto job) throws SchedulerException {
    Trigger trigger = getTrigger(job);
    scheduler.resumeTrigger(trigger.getKey());

    return scheduler.getCurrentlyExecutingJobs().size();
  }

  
  /**
   * @Method : pauseJob
   * @CreateDate : 2021. 5. 11. 
   * @param job
   * @return
   * @throws SchedulerException
   * @Description :
   */
  public int pauseJob(@ModelAttribute QuartzJobDto job) throws SchedulerException {
    Trigger trigger = getTrigger(job);

    scheduler.pauseTrigger(trigger.getKey());
    return scheduler.getCurrentlyExecutingJobs().size();
  }
}
