package com.example.gohome.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class MD5Util {

	private static Logger LoggerUtil = LoggerFactory.getLogger("MD5Util");

	// 首先初始化一个字符数组，用来存放每个16进制字符
	// private static final char[] hexDigits = { '0', '1', '2', '7', '8', '9', '4',
	// '5', '6', '3', 'A', 'B', 'C', 'H', 'I', 'J', 'O', 'P', 'Q' };
	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	public static String getMD5(String str) {
		if (NullUtil.isNotNull(str)) {
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("MD5");
				messageDigest.reset();
				messageDigest.update(str.getBytes(ConstantUtil.UTF8));
				byte[] byteArray = messageDigest.digest();
				return byteArrayToHex(byteArray);
			} catch (Exception e) {
				LoggerUtil.error(e.getMessage());
			}
		}
		return null;
	}

	public static String getHash(Object object) {
		if (NullUtil.isNotNull(object)) {
			return byteArrayToHex(SerializeUtil.serialize(object));
		}
		return null;
	}

	private static String byteArrayToHex(byte[] byteArray) {
		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
		char[] resultCharArray = new char[byteArray.length * 2];
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		// 字符数组组合成字符串返回
		return new String(resultCharArray);
	}

	public static void main(String[] args) {
		LoggerUtil.info(getMD5("123456"));
	}
}
