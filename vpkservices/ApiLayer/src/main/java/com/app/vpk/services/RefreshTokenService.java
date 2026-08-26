package com.app.vpk.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.vpk.dto.CustomUserPrincipal;
import com.app.vpk.dto.RefreshResponse;
import com.app.vpk.entity.RefreshToken;
import com.app.vpk.entity.User;
import com.app.vpk.repository.RefreshTokenRepository;
import com.app.vpk.repository.UserRepository;
import com.app.vpk.security.JwtTokenProvider;

@Service
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	private final UserRepository userRepository;

	private final RefreshTokenGenerator refreshTokenGenerator;

	private final TokenHashService tokenHashService;

	private final JwtTokenProvider jwtTokenProvider;

	private final long refreshTokenExpiration;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository,
			RefreshTokenGenerator refreshTokenGenerator, TokenHashService tokenHashService,
			JwtTokenProvider jwtTokenProvider, @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {

		this.refreshTokenRepository = refreshTokenRepository;

		this.userRepository = userRepository;

		this.refreshTokenGenerator = refreshTokenGenerator;

		this.tokenHashService = tokenHashService;

		this.jwtTokenProvider = jwtTokenProvider;

		this.refreshTokenExpiration = refreshTokenExpiration;
	}

	@Transactional
	public String createRefreshToken(Long userId) {

		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

			validateUser(user);

			String rawToken = refreshTokenGenerator.generate();

			String tokenHash = tokenHashService.hash(rawToken);

			LocalDateTime now = LocalDateTime.now();

			RefreshToken refreshToken = new RefreshToken(tokenHash, user, now,now.plusSeconds(refreshTokenExpiration / 1000),
					false);

			refreshTokenRepository.save(refreshToken);
			
			return rawToken;	
		}catch(Exception e ) {
			e.printStackTrace();
		}
		
		return "";
	}

	private void validateUser(User user) {

		if (!user.isEnabled()) {

			throw new AccountStatusException("User account is disabled") {
			};
		}

		if (!user.isAccountNonLocked()) {

			throw new AccountStatusException("User account is locked") {
			};
		}
		
		if (!user.isAccountNonExpired()) {

			throw new AccountStatusException("User account is Expired") {
			};
		}

//		if (user.getLockedAt() != null && user.getLockedAt().isBefore(LocalDateTime.now())) {
//
//			throw new AccountStatusException("User account has expired") {
//			};
//		}
//
//		if (user.getLockedAt() != null && user.getLockedAt().isBefore(LocalDateTime.now())) {
//
//			throw new AccountStatusException("User credentials have expired") {
//			};
//		}
	}

	@Transactional
	public RefreshToken getValidRefreshToken(String rawToken) throws Exception {

		if (!org.springframework.util.StringUtils.hasText(rawToken)) {

			throw new Exception("Refresh token is missing");
		}

		String tokenHash = tokenHashService.hash(rawToken);

		RefreshToken refreshToken = refreshTokenRepository.findByTokenHashForUpdate(tokenHash)
				.orElseThrow(() -> new Exception("Invalid refresh token"));

		if (refreshToken.isRevoked()) {

			/*
			 * Possible token reuse attack.
			 */
			throw new Exception("Refresh token has already been revoked");
		}

		if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {

			throw new Exception("Refresh token has expired");
		}

		User user = refreshToken.getUser();

		validateUser(user);

		return refreshToken;
	}

	@Transactional
	public RefreshResponse refresh(String rawRefreshToken) throws Exception {

		RefreshToken current = getValidRefreshToken(rawRefreshToken);

		User user = current.getUser();

		/*
		 * Load current roles/permissions.
		 */
		User freshUser = userRepository.findById(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		validateUser(freshUser);

		CustomUserPrincipal principal = new CustomUserPrincipal(freshUser);

		/*
		 * Generate new access JWT.
		 */
		String accessToken = jwtTokenProvider.generateAccessToken(principal);

		/*
		 * Generate new refresh token.
		 */
		String newRefreshToken = refreshTokenGenerator.generate();

		String newHash = tokenHashService.hash(newRefreshToken);

		LocalDateTime now = LocalDateTime.now();

		RefreshToken replacement = new RefreshToken(newHash, freshUser, now, now.plusSeconds(refreshTokenExpiration / 1000));
//				RefreshToken.builder().tokenHash(newHash).user(freshUser).createdAt(now)
//				.expiresAt(now.plusSeconds(refreshTokenExpiration / 1000)).revoked(false).build();

		refreshTokenRepository.save(replacement);

		/*
		 * Revoke old token.
		 */
		current.setRevoked(true);

		current.setRevokedAt(now);

		current.setReplacedByHash(newHash);

		refreshTokenRepository.save(current);
		return new RefreshResponse(accessToken, newRefreshToken, "Bearer", jwtTokenProvider.getAccessTokenExpiration());

//		return RefreshResponse.builder().accessToken(accessToken).refreshToken(newRefreshToken).tokenType("Bearer")
//				.expiresIn(jwtTokenProvider.getAccessTokenExpiration()).build();
	}

	@Transactional
	public void revokeRefreshToken(String rawRefreshToken) {

		String hash = tokenHashService.hash(rawRefreshToken);

		refreshTokenRepository.findByTokenHash(hash).ifPresent(token -> {

			if (!token.isRevoked()) {

				token.setRevoked(true);

				token.setRevokedAt(LocalDateTime.now());

				refreshTokenRepository.save(token);
			}
		});
	}
}