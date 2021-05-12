package com.example.demo.api.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.api.scheduler.entity.JobDetailsEntity;
import com.example.demo.api.scheduler.entity.id.JobDetailPK;

@Repository
public interface JobDetailsRepository extends JpaRepository<JobDetailsEntity, JobDetailPK> {

}
