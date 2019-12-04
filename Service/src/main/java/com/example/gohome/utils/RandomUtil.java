package com.example.gohome.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * 随机数工具类
 * @Author chencaihui
 */
public final class RandomUtil {
	
	/**
	 * 用当前时间加上自定义位数的随机a-zA-Z0-9字符
	 * @param ramStrLen  取多少位的随机a-zA-Z0-9字符
	 * @return
	 */
	public static String getRamdomStringWithTimeStamp(int ramStrLen) {
		long curTime = System.currentTimeMillis();
		StringBuffer resultBuf = new StringBuffer();
		resultBuf.append(String.valueOf(curTime)).append(RandomUtil.getRandomString(ramStrLen));
		return resultBuf.toString();
	}

	/**
	 * 生成随机数，包括a-zA-Z0-9
	 * 
	 * @param length
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 生成随机数，包括A-Z0-9
	 * @param length
	 */
	public static String getRandomUpperString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString().toUpperCase();
	}
	
	/**
	 * 生成随机数，包括a-z0-9
	 * 
	 * @param length
	 */
	public static String getRandomLowerString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString().toLowerCase();
	}
	
	/**
	 * 生成随机数，包括A-Za-z
	 * @param length
	 */
	public static String getRandomEnglish(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 生成随机数，包括a-zA-Z0-9(!@#$%^&*~_+<>?/.,|)
	 */
	public static String getKey(int maxLength) {// length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz(!@#$%^&*~_+<>?/.,|)ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		final String currentTimeMills = String.valueOf(System.currentTimeMillis());
		int length = maxLength - currentTimeMills.length();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString() + currentTimeMills;
	}

	/** 生成随机数,0-9*/
	public static String getRandomNumber(int length) {
		StringBuilder sizesb = new StringBuilder("1");
		for (int i = 1; i < length; i++) {
			sizesb.append("0");
		}
		return String.valueOf((long)((Math.random() * length + 1) * Long.parseLong(sizesb.toString())));
	}

	public static String getSerialVersionUID() {
		return getRandomNumber(6) + System.currentTimeMillis();
	}
	
	/**
	 * 用星星代替中间数值
	 *@author chencaihui 
	 *@datetime 创建时间：2017年10月9日 下午5:31:29 
	 * @param str
	 * @param firstLen 左边保留多少位
	 * @param lashLen 右边保留多少位
	 * @return
	 */
	public static String getStarString(String str, int firstLen, int lastLen) {
		try {
			int strLen = str.length();
			if(firstLen<0 || lastLen<0 || (firstLen+lastLen)>strLen){
				return str;
			}
			if(firstLen>0 && lastLen>0){
				return str.substring(0, firstLen)+"****"+str.substring(strLen-lastLen);
			}else if(firstLen>0 && lastLen<=0){
				return str.substring(0, firstLen)+"****";
			}else if(firstLen<=0 && lastLen>0){
				return "****"+str.substring(strLen-lastLen);
			}
			return str;
		} catch (Exception e) {
			return str;
		}
	}
	
	/**
	 * 抽奖
	 *@author chencaihui 
	 *@datetime 创建时间：2018年7月11日 下午4:40:22 
	 * @param luckList 各个奖项跟概率集合
	 * @return 返回抽中的奖项
	 */
	public synchronized static String luck(Map<String, Double> luckMap){
		if(NullUtil.isNotNull(luckMap)){
			int xsLength = 0;
			double totalValue = 0;
			Iterator<String> its = luckMap.keySet().iterator();
			while(its.hasNext()){
				String itemKey = its.next();
				Double itemValue = luckMap.get(itemKey);
				String[] probaes = String.valueOf(itemValue).split("\\.");
				if(probaes.length==2 && probaes[1].length() > xsLength){
					xsLength = probaes[1].length();
				}
				totalValue += itemValue.doubleValue();
			}
			int xsBase = RandomUtil.getNumber(xsLength);
			if(totalValue<100){
				totalValue = 100;
			}
			int base = (int)(totalValue*xsBase);//抽奖概率计算基数
			int randomNumber = RandomUtil.getRangeNumber(base);//根据基数获取随机数
			int upItemNumber = 0;
			its = luckMap.keySet().iterator();
			while(its.hasNext()){
				String itemKey = its.next();
				Double itemValue = luckMap.get(itemKey);
				int glz = (int)(itemValue.doubleValue()*xsBase);
				if(randomNumber>upItemNumber && randomNumber<=(upItemNumber+glz)){
					return itemKey;
				}
				upItemNumber+=glz;
			}
		}
		return null;
	}
	
	/**
	 * 返回多少1+多少0
	 *@author chencaihui 
	 *@datetime 创建时间：2017年9月20日 下午3:40:09 
	 * @param zeroSize 多少个0
	 * @return
	 */
	public static int getNumber(int zeroSize){
		StringBuilder sizesb = new StringBuilder("1");
		for (int i = 0; i < zeroSize; i++) {
			sizesb.append("0");
		}
		return Integer.parseInt(sizesb.toString());
	}
	
	/**
	 * 获取0到max长度之间的随机数
	 *@author chencaihui 
	 *@datetime 创建时间：2017年9月20日 上午10:28:20 
	 * @param length
	 * @return
	 */
	public static int getRangeNumber(int max) {
		return new Random().nextInt(max);
	}
	
	/*public static void main(String[] args) throws InterruptedException {
		Map<String, BigDecimal> luckMap = new HashMap<String, BigDecimal>();
		luckMap.put("1", new BigDecimal("60"));
		luckMap.put("2", new BigDecimal("39"));
		luckMap.put("3", new BigDecimal("0.995"));
		luckMap.put("4", new BigDecimal("0.0039"));
		luckMap.put("5", new BigDecimal("0.0011"));
		while(true){
			System.out.println(RandomUtil.luck(luckMap));
			Thread.sleep(300);
		}
	}*/
}
