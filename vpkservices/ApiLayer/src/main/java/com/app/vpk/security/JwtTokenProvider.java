package com.app.vpk.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final SecretKey secretKey;

	private final long accessTokenExpiration;

	private final long refreshTokenExpiration;

	public JwtTokenProvider(@Value("${jwt.secret}") String secret,
			@Value("${jwt.access-token-expiration}") long accessTokenExpiration,
			@Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {

		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

		this.accessTokenExpiration = accessTokenExpiration;

		this.refreshTokenExpiration = refreshTokenExpiration;
	}

	public long getAccessTokenExpiration() {
		return accessTokenExpiration;
	}

	public String generateAccessToken(UserDetails userDetails) {

		Date now = new Date();

		return Jwts.builder().subject(userDetails.getUsername()).claim("type", "access").issuedAt(now)
				.expiration(new Date(now.getTime() + accessTokenExpiration)).signWith(secretKey).compact();
	}

	public String generateRefreshToken(String username) {

		Date now = new Date();

		return Jwts.builder().subject(username).claim("type", "refresh").issuedAt(now)
				.expiration(new Date(now.getTime() + refreshTokenExpiration)).signWith(secretKey).compact();
	}

	public Claims getClaims(String token) {

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
	}

	public String getUsername(String token) {

		return getClaims(token).getSubject();
	}

	public String getTokenType(String token) {

		return getClaims(token).get("type", String.class);
	}

	public boolean validateToken(String token) throws Exception {
		try {
			Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException ex) {
			return false;
		}
	}
}