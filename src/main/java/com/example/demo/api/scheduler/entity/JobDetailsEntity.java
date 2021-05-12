package com.example.demo.api.scheduler.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import org.quartz.JobKey;
import com.example.demo.api.scheduler.entity.id.JobDetailPK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "QRTZ_JOB_DETAILS")
public class JobDetailsEntity {
  
  @EmbeddedId
  private JobDetailPK jobDetailPK;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "JOB_CLASS_NAME")
  private String jobClassName;

  @Column(name = "IS_DURABLE")
  private String isDurable;

  @Column(name = "IS_NONCONCURRENT")
  private String isNonConcurrent;

  @Column(name = "IS_UPDATE_DATA")
  private String isUpdateData;

  @Column(name = "REQUESTS_RECOVERY")
  private String requestsRecovery;

  @Column(name = "JOB_DATA")
  private String jobData;
  
  public JobKey createJobKey() {
    return new JobKey(jobDetailPK.getJobName(), jobDetailPK.getJobGroup());
  }
}
