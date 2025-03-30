package com.app.vpk.controller;

import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.app.vpk.services.SchedularConfigService;

@Component
//@RefreshScope

public class TestScheduler {

	@Autowired
	SchedularConfigService schedularConfigService;

	@Scheduled(fixedDelayString = "#{@schedularConfigService.getUpdatedDelay()}") // Require restart the server
	public void runTaskFixedDelay() {
		if (true == schedularConfigService.isSchedulerEnabled()) {
			System.out.println("Fixed Delay Task: " + LocalTime.now());
		} else {
			System.out.println("schedular is disables");
		}
	}

	@Scheduled(fixedRate = 5000)
	public void runTaskFixedRate() {
		System.out.println("Fixed Rate Task: " + LocalTime.now());
	}

}
