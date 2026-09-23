package com.app.vpk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateScheduler {

	@Autowired
	private FloatRatesSyncService syncService;

	@Scheduled(cron = "${fx.scheduler.cron}")
	public void synchronize() {

//		log.info("Starting FloatRates synchronization");

		try {

			syncService.sync();

		} catch (Exception ex) {

//			log.error("FloatRates synchronization failed", ex);
		}
	}
}