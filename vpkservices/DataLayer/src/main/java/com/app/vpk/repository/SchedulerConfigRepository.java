package com.app.vpk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.vpk.entity.SchedulerConfig;

public interface SchedulerConfigRepository extends JpaRepository<SchedulerConfig, Long> {
    SchedulerConfig findByTaskName(String taskName);
}
