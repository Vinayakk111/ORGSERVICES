package com.app.vpk.services;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.vpk.entity.FeatureFlag;
import com.app.vpk.entity.SchedulerConfig;
import com.app.vpk.repository.FeatureFlagRepository;
import com.app.vpk.repository.SchedulerConfigRepository;

@Service
public class SchedularConfigService {

	@Autowired
	private FeatureFlagRepository configRepository;

	@Autowired
	private SchedulerConfigRepository schedulerConfigRepository;

	private boolean schedulerEnabled = true; // Default value
	private String schedulerDelay = "5000"; // Default delay in ms

	@PostConstruct
	public void loadConfig() {
		schedulerEnabled = getBooleanConfig("scheduler.enabled", true);
		schedulerDelay = getStringSchedularConfig("myTask", "5000");
	}

	private boolean getBooleanConfig(String key, boolean defaultValue) {
		Optional<FeatureFlag> config = configRepository.findById(key);
		return config.isPresent() ? config.get().isEnable() : defaultValue;
	}

	private String getStringSchedularConfig(String key, String defaultValue) {
		SchedulerConfig config = schedulerConfigRepository.findByTaskName(key);
		return null != config ? Long.toString(config.getFixedDelay()) : defaultValue;
	}

	public boolean isSchedulerEnabled() {
		return schedulerEnabled;
	}

	public String getUpdatedDelay() {
		return schedulerDelay;
	}

	public void setSchedulerEnabled(boolean schedulerEnabled) {
		this.schedulerEnabled = schedulerEnabled;
	}

	public void setSchedulerDelay(String schedulerDelay) {
		this.schedulerDelay = schedulerDelay;
	}

}
