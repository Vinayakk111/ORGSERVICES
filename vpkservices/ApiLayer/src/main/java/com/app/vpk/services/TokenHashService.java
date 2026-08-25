package com.app.vpk.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class TokenHashService {

	public String hash(String token) {

		try {

			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));

			StringBuilder hex = new StringBuilder(hash.length * 2);

			for (byte b : hash) {

				hex.append(String.format("%02x", b & 0xff));
			}

			return hex.toString();

		} catch (NoSuchAlgorithmException ex) {

			throw new IllegalStateException("SHA-256 algorithm unavailable", ex);
		}
	}
}