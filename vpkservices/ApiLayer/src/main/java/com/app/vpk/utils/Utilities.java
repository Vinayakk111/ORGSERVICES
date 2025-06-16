package com.app.vpk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Utilities {

	private static final Marker IMPORTANT = MarkerManager.getMarker("IMPORTANT");

	public static final BiConsumer<Logger, String> errLogger = (logger, strMsg) -> {
		logger.error(IMPORTANT, strMsg);
	};

	public static final ErrorLogger<Logger, String, Object> logger = (logger, errMsg, trace) -> {
		logger.error(IMPORTANT, errMsg, trace);
	};

	public static final BiConsumer<Logger, String> infoLogger = (logger, strMsg) -> {
		logger.info(IMPORTANT, strMsg);
	};

	public static final InfoLogger<Logger, String, Object> infologger = (logger, infoMsg, infoDetails) -> {
		logger.info(IMPORTANT, infoMsg, infoDetails);
	};

	public static byte[] compress(byte[] data) {
		try {
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			deflater.finish();

			byte[] compressedData = new byte[1024];
			int compressedSize = deflater.deflate(compressedData);
			deflater.end();

			return Arrays.copyOf(compressedData, compressedSize);
		} catch (Exception e) {
			throw new RuntimeException("Compression failed", e);
		}
	}

	public static byte[] decompress(byte[] compressedData) {
		try {
			Inflater inflater = new Inflater();
			inflater.setInput(compressedData);

			byte[] decompressedData = new byte[1024];
			int decompressedSize = inflater.inflate(decompressedData);
			inflater.end();

			return Arrays.copyOf(decompressedData, decompressedSize);
		} catch (Exception e) {
			throw new RuntimeException("Decompression failed", e);
		}
	}

	public File zipPdf(File pdfFile, String name) throws IOException {
		File zipFile = new File(name);
		try (FileOutputStream fos = new FileOutputStream(zipFile);
				ZipOutputStream zipOut = new ZipOutputStream(fos);
				FileInputStream fis = new FileInputStream(pdfFile)) {

			ZipEntry zipEntry = new ZipEntry(pdfFile.getName());
			zipOut.putNextEntry(zipEntry);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
		}
		return zipFile;
	}

	public static void main(String[] args) {
		String originalString = "Hello, Pako in Java!";
		byte[] inputData = originalString.getBytes();

		// Compress
		byte[] compressedData = compress(inputData);
		System.out.println("Compressed Data: " + Arrays.toString(compressedData));

		// Decompress
		byte[] decompressedData = decompress(compressedData);
		String outputString = new String(decompressedData);
		System.out.println("Decompressed String: " + outputString);
	}
}
