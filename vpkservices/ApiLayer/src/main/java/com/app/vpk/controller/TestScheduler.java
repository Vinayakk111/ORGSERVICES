package com.app.vpk.controller;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.vpk.services.SchedulerComponent;

@Component
public class TestScheduler {

	@Autowired
	SchedulerComponent schedulerComponent;

	private String lastDelay = "2000"; // Default delay

	@Scheduled(fixedRate = 5000)
	public void runTaskFixedRate() {
		System.out.println("Fixed Rate Task: " + LocalTime.now());
	}

	@Scheduled(fixedDelayString = "#{@testScheduler.getUpdatedDelay()}") // Require restart the server
	public void runTaskFixedDelay() {
		System.out.println("Fixed Delay Task: " + LocalTime.now());
	}

	public String getUpdatedDelay() {
		lastDelay = String.valueOf(schedulerComponent.getFixedDelayForTask("myTask"));
		return lastDelay;
	}

}
