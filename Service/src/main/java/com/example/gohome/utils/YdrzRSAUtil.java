package com.example.gohome.utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class YdrzRSAUtil extends YdrzRsaParentUtil{
	
	public static final String KEY_ALGORITHM = "RSA";
	
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	
	private static final String PUBLIC_KEY = "RSAPublicKey";
	
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	
	private static final int MAX_ENCRYPT_BLOCK = 117;
	
	private static final int MAX_DECRYPT_BLOCK = 128;
	
	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = decryptBASE64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);
		return encryptBASE64(signature.sign());
	}
	
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		byte[] keyBytes = decryptBASE64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(decryptBASE64(sign));
	}
	
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
		byte[] keyBytes = decryptBASE64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 瀵规暟鎹垎娈佃В瀵�
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}
	
	
	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
		byte[] keyBytes = decryptBASE64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 瀵规暟鎹垎娈佃В瀵�
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}
	
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = decryptBASE64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 瀵规暟鎹姞瀵�
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 瀵规暟鎹垎娈靛姞瀵�
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}
	
	
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = decryptBASE64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 瀵规暟鎹垎娈靛姞瀵�
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}
	
	
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return encryptBASE64(key.getEncoded());
	}
	
	
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return encryptBASE64(key.getEncoded());
	}
	
	public static String encryptedDataOnJava(String data, String PUBLICKEY) throws Exception {
		data = encryptBASE64(encryptByPublicKey(data.getBytes(), PUBLICKEY));
		return data;
	}
	
	public static String decryptDataOnJava(String data, String PRIVATEKEY) throws Exception {
		String temp = "";
		byte[] rs = decryptBASE64(data);
		temp = new String(YdrzRSAUtil.decryptByPrivateKey(rs, PRIVATEKEY), "UTF-8"); // 浠tf-8鐨勬柟寮忕敓鎴愬瓧绗︿覆
		return temp;
	}

	
	public static void main(String[] args) throws Exception {
//		String str = "fsEQVJpNRVrrVPHeRenz1aAgg8/9jQt3Gmgcd24l2qJFGxkWH3jNe6spxqueT6K5WPsGnstFwQtAUWUx3qfICSCLDmTli339P0UQ/AjXylPMY+mBcdEntisH2sen0YcIkX7Uw5XrXgz3qvjGFWT6hRdIp/LX7WpQRewfnYgbFtA=";
		// String publicKey =
		// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSTVUxoKCloz/LVi+yw4IYlea20v0L8pknjCEmJBl9ycZZdjPEN7VuQoiluM88fW2Wf6heeMFAbbbSbRK8Dm+RFXLB1ehB4qFED3AY1oKSzCO5mZjLTrJ1Ed7uF5PQSFwncyc91GJv7A0VFtC1Lf24J2L/gRiyPoqzkvcFgNXlqwIDAQAB";
		// String privateKey =
		// "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJJNVTGgoKWjP8tWL7LDghiV5rbS/QvymSeMISYkGX3Jxll2M8Q3tW5CiKW4zzx9bZZ/qF54wUBtttJtErwOb5EVcsHV6EHioUQPcBjWgpLMI7mZmMtOsnUR3u4Xk9BIXCdzJz3UYm/sDRUW0LUt/bgnYv+BGLI+irOS9wWA1eWrAgMBAAECgYAIzDe0TUl7zG5Ypu0lXdZj7he6pMNsxYDqPOX9aixSQSD5Xj9MrGDvqXaYKJ3lsFE3vKN+UtqkKjTVQJPg1SlmQ6CAemmFZ6xtHT20Vu3pZlrXO9uncCJKZq7JE3C7UJwtf6KID/8t8hqxPQf8vPH6JCgNMFZK52ETnt6HsS3O4QJBANw8IrDODM9TiDNNDj48/jQDlSPp0kE2YIRowR5OTw+1oKbBuHXqDEvMetoSxyygFwn2k3xvLEpmEJ8KifIeh3ECQQCqD5Myvl5PqSTJ9duWwu2icDKTPykirb0OQvB7p6wi7CeU/D5V4UzfSQZYduFoaGgjUnsxc7jm9TeUntI7BIjbAkEAy76+gT8+zpeCy6Mf6ChZLBmcYisxHq+FvzmCX90me2wWge96DWxHj+BOT21L4lcAuXDqpRXcrb+a5OfFEr93oQJAfarmvm+4p6s5OVjJ/R1sl0XLyc25qxuyAhDrPqVH6cKS+WBw++tyb+m5m7O1m/7TPY7c5E08jMcWXGgEuIIAUwJBAItkiA5t5OX6FccRMwJXgDLckpz+giULb3qg8LwZj1twgM7hU479t6TYuiyn/aJdaADzN5X0qljDnPKYiZ+BDeI=";
		 Map<String,Object> keyMap = genKeyPair();
		 System.out.println("publicKey:"+getPublicKey(keyMap));
		 System.out.println("privateKey:"+getPrivateKey(keyMap));
		// System.out.println("--------");
//		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3ywV4WcGiRviDqq8ondSHRGWw8cpNGrHW8FbPfm8ZTzYu6qXumUlbSM679d0lGz8Ihv74rITmruW1RCMk24PTtlhowzMG8Q4ubDW/f9cL89gL2YnVYL26exg9MKHpuyRhxNkYWWBPf+ULHe0S3YwSWvuQ+imaECHXA+ykaFDzmQIDAQAB";
//		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALfLBXhZwaJG+IOqryid1IdEZbDxyk0asdbwVs9+bxlPNi7qpe6ZSVtIzrv13SUbPwiG/vishOau5bVEIyTbg9O2WGjDMwbxDi5sNb9/1wvz2AvZidVgvbp7GD0woem7JGHE2RhZYE9/5Qsd7RLdjBJa+5D6KZoQIdcD7KRoUPOZAgMBAAECgYB4skyvUlJE3VKKNOa+R5ewFQyq/L4bkHJFcqOvs5oGBuzQNqwtWmDAH7oT2UwW+COW2ABNEg5c53JWbZ6er8GLgYd/e4DE8iB87jB5e8NPlpkMAdhTN5jdGlj0yKjFL8ue4P7KL/kFiV4h8PhF2S9vwBFyRaom5RL/7QsJvNECOQJBAPejpO+G+QhNbwl9752Qu/+FmUdbUTQ5w3OJru8iiCYbXM5LsN/dCpt4HefsTMDCocrUrphils1eCnNHKhY8nLsCQQC9/41PsPP4NbUUScWObHTMW6nodvu1w7bE/uxGoKscYGIMnEF/IfJyjfSOcKe+ds0h5naClddhHQRAirG5K3W7AkEAtgUPl4vDAipAYl6xwEQBGzjsyDlDv7pxgjwCW/0qy6+zEGMsN++1+z5X5P+YDttApuCbEKDurTbhQYheVAfrPwJASrukJ75gmDb33N0Pme/LGT7M3CVA6MPsthvXcAhuhaDbsD4d/RWrOafgk854DJDKKxVtc/vgVdCGBaUINt+DVQJACWyuSCg612G13L6bYW7Neb43z57YVdHS9Qcx60wr5xArO2pssLSyXbEBXybOJK1CMIHlN20oZ7o+zAgKlxfGJg==";
		String date1 = encryptedDataOnJava("13802885063", getPublicKey(keyMap));// 鍏挜鍔犲瘑瀵嗛挜A锛屾澶勪负55555
		System.out.println(date1);
		String date2 = decryptDataOnJava(date1, getPrivateKey(keyMap));
		System.out.println(date2);
		System.out.println("----");

	}
}
