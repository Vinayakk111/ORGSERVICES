package com.app.vpk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.vpk.entity.SchedulerConfig;
import com.app.vpk.repository.SchedulerConfigRepository;

@Component
public class SchedulerComponent
{
	 private long lastDelay = 5000; // Default delay

	@Autowired
	private SchedulerConfigRepository repository;
	
	public Long getFixedDelayForTask(String taskName) {
		SchedulerConfig config = repository.findByTaskName(taskName);
		return (config != null) ? config.getFixedDelay() : 5000; // Default 5 seconds
	}
	
	 public long getUpdatedDelay() {
	        lastDelay = getFixedDelayForTask("myTask");
	        return lastDelay;
	    }
	
}
