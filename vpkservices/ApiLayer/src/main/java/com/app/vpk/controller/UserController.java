package com.app.vpk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.vpk.services.FloatRatesSyncService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	
	@Autowired
	FloatRatesSyncService floatRatesSyncService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String admin() {

		return "ADMIN";
	}

	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','SUPPORT')")
	@GetMapping("/dashboard")
	public String dashboard() {

		return "Dashboard";
	}

	@PreAuthorize("hasAuthority('USER_CREATE')")
	@PostMapping("/users")
	public ResponseEntity<?> createUser() {

		return ResponseEntity.ok("User created");
	}

	@PreAuthorize("hasAuthority('REPORT_EXPORT')")
	@GetMapping("/reports/export")
	public ResponseEntity<?> exportReport() {

		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/currency/{from}/{to}")
	public ResponseEntity<?> getCurrencyExchangedData(@PathVariable("from") String from,@PathVariable("to") String to) {
		return ResponseEntity.status(HttpStatus.OK).body(floatRatesSyncService.getCurrency(from, to));
	}	

}
