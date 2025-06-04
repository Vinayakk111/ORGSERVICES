package com.app.vpk.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

	public static void main(String[] args) throws IOException {
		System.out.println(new CommonOrgUtils().computeMD5Checksum(new File("E:\\data\\dev\\table - Copy.pdf")));
//		e5wkXcTi6UpzDhbv7DgSjw==
	}

}
