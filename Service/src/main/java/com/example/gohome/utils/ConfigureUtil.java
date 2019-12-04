package com.example.gohome.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置项
 * @author chencaihui
 */
public class ConfigureUtil {

	//系统配置项
	private static Map<String, Object> CONFIGURE_MAP = new HashMap<String, Object>();
	private static IRedisQueue queue = null;
	
	public static boolean isWindow(){
		String os = System.getProperty("os.name");  
		if(os.toLowerCase().startsWith("win")){
		  	return true; 
		}  
		return false;
	}
	
	/**
	 * 初始化所有配置文件数据到内存
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月29日 下午5:03:37
	 */
	public static void init(){
		/*String[] ignores = new String[]{"jedis.properties" };
		File[] files = FileUtil.searchFile(new File(ConstantUtil.CLASSES_ROOT_PATH), ".properties");
		if(NullUtil.isNotNull(files)){
			for (File file : files) {
				boolean isInit = true;
				for (String ignore: ignores) {
					if(file.getName().equals(ignore)){
						isInit=false;
						break;
					}
				}
				if(isInit){
					initResource(file.getName());
				}
			}
		}*/
	}
	
	

	/**
	 *初始化配置文件
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月17日 下午11:25:34
	 */
//	private static void initResource(String propertiesName) {
//		try {
//			Properties properties = PropertiesLoaderUtils.loadAllProperties(propertiesName);
//			Iterator<Map.Entry<Object, Object>> propertiesIts = properties.entrySet().iterator();
//			while(propertiesIts.hasNext()){
//				Map.Entry<Object, Object> propertiesMap = propertiesIts.next();
//				String key = propertiesMap.getKey().toString();
//				String value = propertiesMap.getValue().toString();
//				CONFIGURE_MAP.put(key, value);
//			}
//			properties.clear();
//			LoggerUtil.info(propertiesName+"初始化成功！");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public static boolean add(String code, Object value){
		if(NullUtil.isNotNull(code)){
			CONFIGURE_MAP.put(code, NullUtil.isNotNull(value)?value:"");
			return true;
		}
		return false;
	}
	
	
	
	public static boolean remove(String code){
		if(NullUtil.isNotNull(code) && CONFIGURE_MAP.containsKey(code)){
			CONFIGURE_MAP.remove(code);
			return true;
		}
		return false;
	}
	
	
	
	public static String getString(String code){
		if(NullUtil.isNotNull(code) && CONFIGURE_MAP.containsKey(code)){
			return String.valueOf(CONFIGURE_MAP.get(code));
		}
		return null;
	}
	
	public static Object getValue(String code){
		if(NullUtil.isNotNull(code) && CONFIGURE_MAP.containsKey(code)){
			return CONFIGURE_MAP.get(code);
		}
		return null;
	}
	
	public static Boolean getBoolean(String code){
		if(NullUtil.isNotNull(code) && CONFIGURE_MAP.containsKey(code)){
			Object value = CONFIGURE_MAP.get(code);
			if(NullUtil.isNotNull(value)){
				return Boolean.valueOf(value.toString());
			}
		}
		return Boolean.FALSE;
	}
	
	public static Long getLong(String code){
		if(NullUtil.isNotNull(code) && CONFIGURE_MAP.containsKey(code)){
			Object value = CONFIGURE_MAP.get(code);
			if(NullUtil.isNotNull(value)){
				return Long.valueOf(value.toString());
			}
		}
		return null;
	}
	
	public static Integer getInt(String code){
		if(NullUtil.isNotNull(code) && CONFIGURE_MAP.containsKey(code)){
			Object value = CONFIGURE_MAP.get(code);
			if(NullUtil.isNotNull(value)){
				return Integer.valueOf(value.toString());
			}
		}
		return null;
	}
	public static Integer getInt(String code, int defValue){
		if(NullUtil.isNotNull(code) && CONFIGURE_MAP.containsKey(code)){
			Object value = CONFIGURE_MAP.get(code);
			if(NullUtil.isNotNull(value)){
				return Integer.valueOf(value.toString());
			}
		}
		return defValue;
	}
	
	public static Double getDouble(String code){
		if(NullUtil.isNotNull(code) && CONFIGURE_MAP.containsKey(code)){
			Object value = CONFIGURE_MAP.get(code);
			if(NullUtil.isNotNull(value)){
				return Double.valueOf(value.toString());
			}
		}
		return null;
	}
	
	public static Double getDouble(String code, double defValue){
		if(NullUtil.isNotNull(code) && CONFIGURE_MAP.containsKey(code)){
			Object value = CONFIGURE_MAP.get(code);
			if(NullUtil.isNotNull(value)){
				return Double.valueOf(value.toString());
			}
		}
		return defValue;
	}
	
	public static String getDefault(String code, String defValue){
		if(NullUtil.isNotNull(code) && CONFIGURE_MAP.containsKey(code)){
			return String.valueOf(CONFIGURE_MAP.get(code));
		}
		return defValue;
	}

	/***
	 * 后台
	 * 系统标识，1后台，2公众号，3小程序，4移动端，5定时任务，6文件服务器
	 *@author chencaihui 
	 *@datetime 创建时间：2018年3月21日 下午2:56:21 
	 * @return
	 */
	public static boolean isManage(){
		return ConstantUtil.IS_USED_TRUE.equals(getString("SYSTEM_FLAG"));
	}
	
	/***
	 * WEB
	 * 系统标识，1后台，2公众号，3小程序，4移动端，5定时任务，6文件服务器
	 *@author chencaihui 
	 *@datetime 创建时间：2018年3月21日 下午2:56:21 
	 * @return
	 */
	public static boolean isWeb(){
		return ConstantUtil.STRING_4.equals(getString("SYSTEM_FLAG"));
	}
	
	/***
	 * 公众号
	 * 系统标识，1后台，2公众号，3小程序，4移动端，5定时任务，6文件服务器
	 *@author chencaihui 
	 *@datetime 创建时间：2018年3月21日 下午2:56:21 
	 * @return
	 */
	public static boolean isWxpub(){
		return ConstantUtil.STRING_2.equals(getString("SYSTEM_FLAG"));
	}
	
	/***
	 * 小程序
	 * 系统标识，1后台，2公众号，3小程序，4移动端，5定时任务，6文件服务器
	 *@author chencaihui 
	 *@datetime 创建时间：2018年3月21日 下午2:56:21 
	 * @return
	 */
	public static boolean isWxbit(){
		return ConstantUtil.STRING_3.equals(getString("SYSTEM_FLAG"));
	}
	
	/**
	 * 文件服务器
	 * 系统标识，1后台，2公众号，3小程序，4移动端，5定时任务，6文件服务器
	 *@author chencaihui 
	 *@datetime 创建时间：2018年4月11日 上午10:15:46 
	 * @return
	 */
	public static boolean isFileServer(){
		return ConstantUtil.STRING_6.equals(getString("SYSTEM_FLAG"));
	}
	
	/**
	 * 获取系统标识
	 * 系统标识，1后台，2公众号，3小程序，4移动端，5定时任务，6文件服务器
	 *@author chencaihui 
	 *@datetime 创建时间：2018年3月21日 下午5:37:10 
	 * @return
	 */
	public static String getSystemFlag(){
		return getString("SYSTEM_FLAG");
	}
	
	/**
	 * 清理配置数据
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月31日 下午3:52:18
	 */
	public static void clear(){
		CONFIGURE_MAP.clear();
	}
}
