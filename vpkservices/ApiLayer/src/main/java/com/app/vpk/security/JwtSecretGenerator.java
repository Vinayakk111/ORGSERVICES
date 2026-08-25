package com.app.vpk.security;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.jsonwebtoken.Jwts;

public class JwtSecretGenerator {
	public static void main(String[] args) {

		SecretKey key = Jwts.SIG.HS256.key().build();

		String encoded = Base64.getEncoder().encodeToString(key.getEncoded());

//		System.out.println(encoded);
		
		System.out.println(new BCryptPasswordEncoder(12).encode("Password12!@"));
	}
}
