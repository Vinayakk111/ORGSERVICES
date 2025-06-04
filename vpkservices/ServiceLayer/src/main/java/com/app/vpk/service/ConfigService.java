package com.app.vpk.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.vpk.entity.ConfigMaster;
import com.app.vpk.repository.ConfigMasterRepository;

@Service
public class ConfigService {

	@Autowired
	private ConfigMasterRepository configRepo;

	@Cacheable(value = "configCache", key = "#key")
	public String getConfigValue(String key) {
		return configRepo.findByConfigKeyAndIsActiveTrue(key).map(ConfigMaster::getConfigValue).orElse(null);
	}

	public int getIntConfig(String key, int defaultValue) {
		String val = getConfigValue(key);
		return (val != null) ? Integer.parseInt(val) : defaultValue;
	}

	public boolean getBooleanConfig(String key, boolean defaultValue) {
		String val = getConfigValue(key);
		return (val != null) ? Boolean.parseBoolean(val) : defaultValue;
	}

	public String getStringConfig(String key, String defaultValue) {
		return (getConfigValue(key) != null) ? getConfigValue(key) : defaultValue;
	}

	@CacheEvict(value = "configCache", key = "#key")
	public void updateConfig(String key, String newValue) {
		configRepo.findByConfigKeyAndIsActiveTrue(key).ifPresent(config -> {
			config.setConfigValue(newValue);
			config.setUpdatedAt(LocalDateTime.now());
			configRepo.save(config);
		});
	}

}
