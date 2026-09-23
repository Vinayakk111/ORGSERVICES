package com.app.vpk.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.vpk.dto.CustomUserPrincipal;
import com.app.vpk.dto.LoginRequest;
import com.app.vpk.dto.LoginResponse;
import com.app.vpk.entity.User;
import com.app.vpk.repository.UserRepository;
import com.app.vpk.security.JwtTokenProvider;

@Service
@Transactional
public class AuthService {

	private final AuthenticationManager authenticationManager;

	private final RefreshTokenService refreshTokenService;

	private final JwtTokenProvider jwtTokenProvider;

	private final UserRepository userRepository;

	public AuthService(AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService,
			JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {

		this.authenticationManager = authenticationManager;

		this.refreshTokenService = refreshTokenService;

		this.jwtTokenProvider = jwtTokenProvider;

		this.userRepository = userRepository;
	}

	public LoginResponse login(LoginRequest request) {
		String accessToken = "";
		String refreshToken = "";
		CustomUserPrincipal principal = null;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			principal = (CustomUserPrincipal) authentication.getPrincipal();
			accessToken = jwtTokenProvider.generateAccessToken(principal);
			refreshToken = refreshTokenService.createRefreshToken(principal.getId());
			userRepository.updateLastLogin(principal.getId(), LocalDateTime.now());
			User user = userRepository.findByUsername(request.getUsername()).orElse(new User());
			if(ChronoUnit.DAYS.between(user.getLastLoginAt(), LocalDateTime.now()) > 90){
				return new LoginResponse(401,"Account Expired");
			}else {
				return new LoginResponse(accessToken, refreshToken, "Bearer", jwtTokenProvider.getAccessTokenExpiration(),"Success",200);
			}
			
			
		} catch (Exception e) {
			if (!org.apache.commons.lang3.StringUtils.isEmpty(request.getUsername())) {
				User user = userRepository.findByUsername(request.getUsername()).orElse(new User());
				if (!org.apache.commons.lang3.StringUtils.isEmpty(user.getUsername()))
					if (user.getFailedLoginAttempts() > 5) {
						userRepository.updateUserLockdStatus(false, request.getUsername());
						return new LoginResponse(570,"Your Account is Locked");
					}
				userRepository.updateFailedLoginAttempt(request.getUsername(), user.getFailedLoginAttempts() + 1);

				return new LoginResponse(401 ,"Invalid Creds");
			}else {
				return new LoginResponse(401,"Please pass Username");
			}
		}
		

	}
}