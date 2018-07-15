package com.cnebula.lsp.openapi.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class MD5Util {

	public static String md5(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes("UTF-8"));
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext.toLowerCase();
    }
	
	public static String md5(Map<String, String> params) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		StringBuilder raw = new StringBuilder();
		params.entrySet().stream().sorted(Map.Entry.<String,String>comparingByKey()).forEachOrdered(e -> {
			raw.append(e.getKey()).append(e.getValue());
		});
		return MD5Util.md5(raw.toString());
	}
}
