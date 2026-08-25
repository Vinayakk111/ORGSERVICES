package com.app.vpk.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.vpk.dto.LoginRequest;
import com.app.vpk.dto.LoginResponse;
import com.app.vpk.dto.RefreshRequest;
import com.app.vpk.dto.RefreshResponse;
import com.app.vpk.services.AuthService;
import com.app.vpk.services.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	private final RefreshTokenService refreshTokenService;

	public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {

		this.authService = authService;
		this.refreshTokenService = refreshTokenService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/refresh")
	public ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshRequest request) throws Exception {

		return ResponseEntity.ok(refreshTokenService.refresh(request.getRefreshToken()));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestBody RefreshRequest request) {
		refreshTokenService.revokeRefreshToken(request.getRefreshToken());
		return ResponseEntity.noContent().build();
	}
}