package com.juyo.visa.common.msgcrypt;

import java.security.MessageDigest;
import java.util.Formatter;

/**
 * 钉钉jsapi签名工具类
 */

public class SignatureUtil {
	public SignatureUtil() {
	}

	public static String getJsApiSingnature(String url, String nonce, Long timeStamp, String jsTicket) throws Exception {
		String plainTex = "jsapi_ticket=" + jsTicket + "&noncestr=" + nonce + "&timestamp=" + timeStamp + "&url=" + url;
		String signature = "";

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(plainTex.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (Exception var7) {

		}
		return signature;
	}

	private static String byteToHex(byte[] hash) {
		Formatter formatter = new Formatter();
		byte[] var2 = hash;
		int var3 = hash.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			byte b = var2[var4];
			formatter.format("%02x", new Object[] { Byte.valueOf(b) });
		}

		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static void main(String[] args) throws Exception {
		String url = "http://10.62.53.138:3000/jsapi";
		String nonce = "abcdefgh";
		Long timeStamp = Long.valueOf(1437027269927L);
		String tikcet = "zHoQdGJuH0ZDebwo7sLqLzHGUueLmkWCC4RycYgkuvDu3eoROgN5qhwnQLgfzwEXtuR9SDzh6BdhyVngzAjrxV";
		System.err.println(getJsApiSingnature(url, nonce, timeStamp, tikcet));
	}
}