package com.app.vpk.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * @author vinay
 *
 */
public class CommonOrgUtils {
	
	
	/**
	 * Method for Getting Checksum for duplicate file
	 * @param file
	 * @return String
	 * @throws IOException
	 */
	public String computeMD5Checksum(File file) throws IOException {
		try (InputStream fis = new FileInputStream(file)) {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int numRead;
			while ((numRead = fis.read(buffer)) > 0) {
				md.update(buffer, 0, numRead);
			}
			byte[] digest = md.digest();
			return Base64.getEncoder().encodeToString(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public OffsetDateTime getCurrnetUTCTimeStamp() {
		return OffsetDateTime.now(ZoneOffset.UTC);
	}
	
	private ZonedDateTime getUTCTime(String inputTimeString,String zone) {
//		String inputTimeString = "2026-08-26 14:30:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(inputTimeString, formatter);
        ZoneId originalZone = ZoneId.of(zone); // Use "America/New_York" for US EST eg.Asia/Dubai
        ZonedDateTime originalZonedDateTime = localDateTime.atZone(originalZone);
        ZonedDateTime utcDateTime = originalZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcDateTime;
	}
	

	public static void main(String[] args) throws IOException {
//		System.out.println(new CommonOrgUtils().computeMD5Checksum(new File("E:\\data\\dev\\table - Copy.pdf")));
//		e5wkXcTi6UpzDhbv7DgSjw==
		System.out.println(new CommonOrgUtils().getUTCTime("2026-08-26 14:30:00","Asia/Dubai"));
		
	}

}
