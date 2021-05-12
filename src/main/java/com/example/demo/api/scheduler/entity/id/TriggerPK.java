package com.example.demo.api.scheduler.entity.id;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class TriggerPK implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "SCHED_NAME")
  private String schedName;

  @Column(name = "TRIGGER_NAME")
  private String triggerName;

  @Column(name = "TRIGGER_GROUP")
  private String triggerGroup;
}
