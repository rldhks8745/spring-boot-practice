package com.example.demo.api.scheduler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "QRTZ_TRIGGERS")
public class TriggersEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "SEQ")
  private Long seq;
  
  @Column(name = "SCHED_NAME")
  private String schedName;

  @Column(name = "TRIGGER_NAME")
  private String triggerName;

  @Column(name = "TRIGGER_GROUP")
  private String triggerGroup;
  
  @Column(name = "JOB_NAME")
  private String jobName;

  @Column(name = "JOB_GROUP")
  private String jobGroup;
  
  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "NEXT_FIRE_TIME")
  private Long nextFireTime;

  @Column(name = "PREV_FIRE_TIME")
  private Long prevFireTime;

  @Column(name = "PRIORITY")
  private Integer priority;

  @Column(name = "TRIGGER_STATE")
  private String triggerState;

  @Column(name = "TRIGGER_TYPE")
  private String triggerType;

  @Column(name = "START_TIME")
  private Long startTime;

  @Column(name = "END_TIME")
  private Long endTime;

  @Column(name = "CALENDAR_NAME")
  private String calendarName;

  @Column(name = "MISFIRE_INSTR")
  private Integer misfireInstr;
  
  @Column(name = "JOB_DATA")
  private String jobData;
  
}
