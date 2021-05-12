package com.example.demo.api.scheduler.controller;

import java.util.List;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.api.scheduler.entity.CronTriggersEntity;
import com.example.demo.api.scheduler.entity.TriggersEntity;
import com.example.demo.api.scheduler.model.QuartzJobDto;
import com.example.demo.api.scheduler.service.SchedulerService;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

  @Autowired
  SchedulerService schedulerService;

  @GetMapping("/triggers")
  public List<CronTriggersEntity> selcetCronTriggers() {
    return schedulerService.selectCronTriggers();
  }

  @GetMapping("/jobs")
  public List<TriggersEntity> selcetJobDetails() {
    return schedulerService.selectTriggers();
  } 
  
  @PostMapping
  public TriggerKey createJob(@ModelAttribute QuartzJobDto job) throws SchedulerException, ClassNotFoundException {
    return schedulerService.createJob(job);
  }
  
  @PutMapping
  public TriggerKey modifyJob(@ModelAttribute QuartzJobDto job) throws SchedulerException {
    return schedulerService.modifyJob(job);
  }
  
  @DeleteMapping
  public boolean deleteJob(@ModelAttribute QuartzJobDto job) throws SchedulerException {
    return schedulerService.deleteJob(job);
  }

  @PostMapping("/resume")
  public int resumeJob(@ModelAttribute QuartzJobDto job) throws SchedulerException {
    return schedulerService.resumeJob(job);
  }

  @PostMapping("/pause")
  public int pauseJob(@ModelAttribute QuartzJobDto job) throws SchedulerException {
    return schedulerService.pauseJob(job);
  }
}
