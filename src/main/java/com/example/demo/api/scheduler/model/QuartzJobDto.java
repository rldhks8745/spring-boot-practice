package com.example.demo.api.scheduler.model;

import org.quartz.JobKey;
import org.quartz.TriggerKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuartzJobDto {
  private String jobName;
  
  private String description;

  private String jobClassName;

  private String cronExpression;
  
  public JobKey createJobKey() {
    return new JobKey(jobName);
  }

  public TriggerKey createTriggerKey() {
    return new TriggerKey(jobName + "Trigger");
  }
}
