package com.app.vpk.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.vpk.entity.CountryLanguage;
import com.app.vpk.repository.CountryLanguageRepository;
import com.app.vpk.service.UserService;

@RestController
public class TestController {
	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@Value("${app.download.path}")
    private String appName;

	@Autowired
	EntityManager manager;

	@Autowired
	UserService userService;
	
	@Autowired
	CountryLanguageRepository countryLanguageRepository;
	
	@GetMapping("/getcnlang1")
	public List<CountryLanguage> getcnlang() {
		return countryLanguageRepository.findAll();
	}
	
	@GetMapping("/getpropname1")
	public String getpropname() {
		logger.info(appName);
		return appName;
	}
}
