package com.example.demo.api.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.api.scheduler.entity.TriggersEntity;
import com.example.demo.api.scheduler.entity.id.TriggerPK;

@Repository
public interface TriggersRepository extends JpaRepository<TriggersEntity, TriggerPK> {

}
