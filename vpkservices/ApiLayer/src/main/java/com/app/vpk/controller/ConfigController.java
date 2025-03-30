package com.app.vpk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.vpk.services.SchedularConfigService;

@RestController
@RequestMapping("/config")
public class ConfigController {

	@Autowired
	private SchedularConfigService schedularConfigService;

	@GetMapping("/reload")
	public String reloadConfig() {
		schedularConfigService.loadConfig();
		return "Schedular Configuration reloaded!";
	}
}