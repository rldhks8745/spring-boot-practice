package com.example.demo.api.scheduler.entity.id;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class JobDetailPK implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "SCHED_NAME")
  private String schedName;

  @Column(name = "JOB_NAME")
  private String jobName;

  @Column(name = "JOB_GROUP")
  private String jobGroup;
}
