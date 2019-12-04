package com.example.gohome.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

public class YdrzRsaParentUtil {
	private static final String KEY_SHA = "SHA";
	private static final String KEY_MD5 = "MD5";
	private static String hexString = "0123456789ABCDEF";
	
	public static final String KEY_MAC = "HmacMD5";
	
	
	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64Ext.decode(key.getBytes(), Base64Ext.NO_WRAP);
		//return Base64.decodeBase64(key.getBytes());
		//return (new BASE64Decoder()).decodeBuffer(key);
	}
	
	
	public static String encryptBASE64(byte[] key) throws Exception {
		return new String(Base64Ext.encode(key, Base64Ext.NO_WRAP));
		//return new String(Base64.encodeBase64(key));
		//return (new BASE64Encoder()).encodeBuffer(key);
	}
	
	
	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();

	}
	
	public static byte[] encryptSHA(byte[] data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();

	}
	
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}
	
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);

	}
	
	
	public static String bytesToString(byte[] src) throws Exception{
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		String bytes =  stringBuilder.toString();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 灏嗘瘡2浣�16杩涘埗鏁存暟缁勮鎴愪竴涓瓧鑺�
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}
}