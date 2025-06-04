package com.app.vpk;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.vpk.services.ExampleJob;

@Configuration
public class QuartzConfig {

	
	// Define JobDetail with concurrency control
	@Bean
	public JobDetail exampleJobDetail() {
		return JobBuilder.newJob(ExampleJob.class).withIdentity("exampleJob")
				.withDescription("Example persistent job with concurrency control").storeDurably().build();
	}

	// Define Cron Trigger with priority and misfire handling
	@Bean
	public Trigger exampleJobTrigger() {
		return TriggerBuilder.newTrigger().forJob(exampleJobDetail()).withIdentity("exampleTrigger")
				.withDescription("Cron trigger every minute with priority and misfire handling")
				.withSchedule(
						CronScheduleBuilder.cronSchedule("0 0/1 * * * ?").withMisfireHandlingInstructionDoNothing())
				.withPriority(10) // high priority
				.build();
	}
}
