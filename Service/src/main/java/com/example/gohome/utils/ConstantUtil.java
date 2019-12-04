package com.example.gohome.utils;

import java.util.*;


/**
 * 常量/静态变量
 * @author chencaihui
 */
public final class ConstantUtil {
	
	public static final String TREE_ROOT = "000000000";//树根id
	public static final String SYSTEM_ID = "333333333", 
								adminName = "admin", 
								adminpwd = "123456", 
								adminRoleName = "超级管理员",
								permissionName = "系统管理";
	public static final String SYSTEM_USER = "system";//系统用户
	public static final String SYSTEM_USER_NAME = "系统";//系统用户
	public static final String DEFAULT_VALUE = "default";//默认
	public static final String PARENT_ID = "parentId";//父id
	public static final String PARENT_IDS = "parentIds";//父id集
	public static final String AES_KEY = "CMIChemanuoche07";//用于aes加密解密的key
	public static final String AES_IV = "CMIChemanuoche07";
	//公共参数配置
	public static String HTTP_ADDRESS_ROOT = "https://nuoche.51hutui.com";
//	public static String HTTP_ADDRESS_ROOT = "https://wxpub.zhongce.net.cn";
//	public static String HTTP_ADDRESS_ROOT = "http://127.0.0.1:8888";
//	public static String HTTP_ADDRESS_ROOT = "http://www.ch3home.cn";
//	public static String HTTP_ADDRESS_ROOT = "http://112.74.187.49";
//	public static String HTTP_ADDRESS_ROOT = "http://192.168.2.131:3333";
	public static String HTTP_ROOT_PATH = null;
	public static String HTTP_HTML_PATH = null;
	public static String PROJECT_CONTEXT = null;
//	public static String PROJECT_CONTEXT = "/opt/application/nuoche/images/";
	public static String PROJECT_CONTEXT_WXPUB = null;//公众号
	public static void setHttpRoot(final String contextPath) {
		ConstantUtil.PROJECT_CONTEXT = contextPath;
		ConstantUtil.HTTP_ROOT_PATH = ConstantUtil.HTTP_ADDRESS_ROOT+contextPath;
	}
	public static final String getHttpRoot() {
		return ConstantUtil.HTTP_ROOT_PATH;
	}
	public static String PROJECT_ROOT_PATH = null;//项目实际根目录,程序启动时初始化
	public static String CLASSES_ROOT_PATH = null;//项目的classes目录
	public static final int SYS_STATE = 1;//本系统数据表示，其他值有其他系统自定义
	//public static String PROJECT_NAME = null;//项目命名空间
	public static final int DEFAULT_PAGE_SIZE = 10;//默认分页数量
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static int REDIS_DATASOURCE_DEFAULT = 0;//wx默认的缓存库、主数据6/xiaohao3/test8
	public static int REDIS_DATASOURCE_DATAS = 1;//wx数据队列的缓存库7/xiaohao4/test9
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String DELETE = "DELETE";
	public static final String PUT = "PUT";
	public static final String ADD = "add";
	public static final String UPDATE = "modify";
	public static final String REMOVE = "remove";
	public static final String OK = "OK";
	public static int inlineCount = 0;//处理数
	public static int requestCount = 0;//请求数
	public static int doingMaxCount = 0;//服务器的最大处理数
	public static int requestMaxCount = 0;//服务器最大请求数
	public static int sameIpMaxCount = 0;//相同ip的最大请求数
	public static int concurrentCycle = 0;//并发周期（多少秒算一次并发）
	
	//编码
	public static final String UTF8 = "UTF-8";
	public static final String GBK = "GBK";
	public static final String GB2312 = "GB2312";
	public static final String ISO_8859_1 = "ISO-8859-1";
	//布尔值
	public static final String Y = "Y";
	public static final String N = "N";
	public static final String DESC = "DESC";
	public static final String ASC = "ASC";
	public static final String IS_NULL = "isNull";
	public static final String IS_NOT_NULL = "isNotNull";
	public static final int INT_0 = 0;
	public static final int INT_1 = 1;
	public static final int INT_2 = 2;
	public static final int INT_3 = 3;
	public static final int INT_4 = 4;
	public static final String STRING_0 = "0";
	public static final String STRING_1 = "1";
	public static final String STRING_2 = "2";
	public static final String STRING_3 = "3";
	public static final String STRING_4 = "4";
	public static final String STRING_5 = "5";
	public static final String STRING_6 = "6";
	public static final String IS_USED_TRUE = "1";
	public static final String IS_USED_FALSE = "0";
	public static final String IS_CLOSED_TRUE = "1";
	public static final String IS_CLOSED_FALSE = "0";
	public static final String IS_CONNECT_TRUE = "1";
	public static final String IS_CONNECT_FALSE = "0";
	
	//EHCACHE
	public static final String EHCACHE_BASE_NAME = "base-ehcache";
	//date
	public static final long SECOND = 1000;
	public static final long MINUE = 60*1000;
	public static final long HOUR = 60*60*1000;
	public static final long DAY = 24*60*60*1000;
	//其他,静态变量
	public static final int NUMBER_1024 = 1024;//1024
	public static final int NUMBER_THOUSAND = 1000;//一千
	public static final int NUMBER_MILLION = 1000000;//百万
	
	public static final String SPLITE = ":";//分隔符
	public static final String FINAL_DATAS_ADD = ":add";//表示新增数据
	public static final String FINAL_DATAS_UPDATE = ":upd";//表示更新数据
	public static final String FINAL_DATAS_DELETE = ":del";//表示删除数据
	
	public static final String WHITE_LIST_PHONES = "WHITE_LIST_PHONES";//手机号码白名单
	
	public static final String SYSTEM_TIME_ = "system_time_";//系统时间前缀
	public static final String JPG = ".jpg";
	
	public static final String QUARTZ_DATA_RUNNING_STATUS = "data_running_status";//判断数据同步定时任务运行状态
	public static final String STATIC_VALIDATE_CODE = "validateCode";//验证码
	public static final String[] INVALID_CHARACTERS = new String[]{//需要过滤的非法字符
        "script","select","insert","document","window","function",  
        "delete","update","prompt","alert","create","alter",  
        "drop","iframe","link","where","replace","function","onabort",  
        "onactivate","onafterprint","onafterupdate","onbeforeactivate",  
        "onbeforecopy","onbeforecut","onbeforedeactivateonfocus",  
        "onkeydown","onkeypress","onkeyup","onload",  
        "expression","applet","layer","ilayeditfocus","onbeforepaste",  
        "onbeforeprint","onbeforeunload","onbeforeupdate",  
        "onblur","onbounce","oncellchange","oncontextmenu",  
        "oncontrolselect","oncopy","oncut","ondataavailable",  
        "ondatasetchanged","ondatasetcomplete","ondeactivate",  
        "ondrag","ondrop","onerror","onfilterchange","onfinish","onhelp",  
        "onlayoutcomplete","onlosecapture","onmouse","ote",  
        "onpropertychange","onreadystatechange","onreset","onresize",  
        "onresizeend","onresizestart","onrow","onscroll",  
        "onselect","onstaronsubmit","onunload","IMgsrc","infarction"
	};
	
	//缓存
	public static final int CACHE_TIMEOUT_DEFAULT = 7200;//缓存超时/秒s
	
	//timer
	public static final String TIMER_DATA_KEY = "TIMER_DATA_KEY";//数据的定时任务key值
	
	//正则表达式
	/** 正则表达式：验证固定电话 */
	public static final String REGEX_TELPHONE = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";//固定电话
	 /** 正则表达式：验证用户名  */
    public static final String REGEX_USERNAME = "[a-zA-Z0-9]{5,20}";
    /** 正则表达式：验证密码 */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9@_]{6,20}$";
    /** 正则表达式：验证手机号 */
    public static final String REGEX_PHONE = "^1[3,4,5,8,7,9]\\d{9}$";
    /**中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700,173,199*/
    public static final String CHINA_TELECOM_PATTERN = "(^1(33|53|77|73|99|8[019])\\d{8}$)|(^1700\\d{7}$)";
    /** 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1709*/
    public static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";
    /**中国移动号码格式验证 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705*/
    public static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478]|9[8])\\d{8}$)|(^1705\\d{7}$)";
    /** 正则表达式：验证邮箱 */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /** * 正则表达式：验证汉字 */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
    /** 正则表达式：验证URL */
    public static final String REGEX_URL = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";
    /** 正则表达式：验证IP地址 */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    /** 正则表达式：验证身份证号码 */
    public static final String REGEX_IDCARD = "idcardno"; 
	/** 正则表达式：验证日期 */
    public static final String REGEX_DATE = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$"; // 日期
    /**车牌号码*/
    public static final String REGEX_CAR_NUMBER = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
    
    /** 正则表达式：验证数字 */
	public static final String REGEX_NUMBER = "^\\d$";
	/** 正则表达式：验证4位数字 */
	public static final String REGEX_NUMBER_4 = "^\\d{4}$";
	/** 正则表达式：验证6位数字 */
	public static final String REGEX_NUMBER_6 = "^\\d{6}$";
	/** 正则表达式：验证8位数字 */
	public static final String REGEX_NUMBER_8 = "^\\d{8}$";
	/** 正则表达式：验证10位数字 */
	public static final String REGEX_NUMBER_10 = "^\\d{10}$";
	
	//集合
	/**对象.属性:字段名#####对象：表名*/
	public static final Map<String, String> OBJECT2TABLE_MAP = new HashMap<String, String>(0);
	/**表名.字段名:属性#####表名：对象*/
	public static final Map<String, String> TABLE2OBJECT_MAP = new HashMap<String, String>(0);
	/**表名:列名（表中顺序）*/
	public static final Map<String, List<String>> TABLE2COLUMNS_MAP = new HashMap<String, List<String>>(0);
	/**表名.字段名:查询注解*/
	public static final Map<String, Select> TABLE2SELECTT_MAP = new HashMap<String, Select>(0);
	/**对象.属性:查询注解*/
	public static final Map<String, Select> OBJECT2SELECTT_MAP = new HashMap<String, Select>(0);
	/**存储自增长值*/
	public static final Map<String, Long> ID_MAP = new HashMap<String, Long>();
	/**表名:最后更新时间列*/
	public static final Map<String, Date> TABLE2UPDATETIME = new HashMap<String, Date>(0);
	/**表名*/
	public static final List<String> TABLE_NAMES = new ArrayList<String>(0);
	/**数据库名*/
	public static String DB_NAME = null;
	
	/**生成实体和映射关系需要忽略的字段*/
	public static final Map<String, String> IGNORE_MAP = new HashMap<String, String>();
	static{
		IGNORE_MAP.put("ID", "ID");
		IGNORE_MAP.put("SORT_NO", "SORT_NO");
		IGNORE_MAP.put("CREATE_TIME", "CREATE_TIME");
		IGNORE_MAP.put("CREATE_ID", "CREATE_ID");
		IGNORE_MAP.put("UPDATE_TIME", "UPDATE_TIME");
		IGNORE_MAP.put("UPDATE_ID", "UPDATE_ID");
		IGNORE_MAP.put("IS_DELETED", "IS_DELETED");
	}
	public static final Map<String, List<String>> PERMISSIONS = new HashMap<String, List<String>>(0);//登陆时候初始化的权限数据
	
}
