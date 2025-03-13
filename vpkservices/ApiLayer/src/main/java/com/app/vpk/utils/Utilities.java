package com.app.vpk.utils;

import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Utilities {
	
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


