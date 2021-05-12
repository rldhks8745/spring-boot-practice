package com.example.demo.api.scheduler.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import com.example.demo.api.scheduler.entity.id.TriggerPK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "QRTZ_CRON_TRIGGERS")
public class CronTriggersEntity {

  @EmbeddedId
  private TriggerPK cronTriggerPK;

  @Column(name = "CRON_EXPRESSION")
  private String cronExpression;

  @Column(name = "TIME_ZONE_ID")
  private String timeZoneId;
  
}
