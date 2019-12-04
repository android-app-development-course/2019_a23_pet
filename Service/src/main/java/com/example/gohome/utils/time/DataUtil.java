package com.example.gohome.utils.time;

import com.example.gohome.utils.NullUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtil {

	private static Logger log = LoggerFactory.getLogger(DataUtil.class);
	private static final String starthms = "00:00:00";
	private static final String endhms = "23:59:59";

	/**
	 * 返回String或空指针
	 */
	public static String getString(Object obj) {
		try {
			return obj == null ? null : obj.toString();
		} catch (Exception e) {
			log.error("getString:" + e);
		}
		return null;
	}

	/**
	 * 返回String或默认值
	 */
	public static String getString(Object obj, String defaultValue) {
		try {
			return obj == null ? defaultValue : obj.toString();
		} catch (Exception e) {
			log.error("getString:" + e);
		}
		return null;
	}

	/**
	 * 返回Long或空指针
	 */
	public static Long getLong(Object obj) {
		try {
			return obj == null ? null : Long.parseLong(obj.toString());
		} catch (Exception e) {
			log.error("getLong:" + e);
		}
		return null;
	}

	/**
	 * 返回Long或默认值
	 */
	public static Long getLong(Object obj, Long defaultValue) {
		try {
			return obj == null ? defaultValue : Long.parseLong(obj.toString());
		} catch (Exception e) {
			log.error("getLong:" + e);
		}
		return null;
	}

	/**
	 * 返回Integer或空指针
	 */
	public static Integer getInteger(Object obj) {
		try {
			return obj == null ? null : Integer.parseInt(obj.toString());
		} catch (Exception e) {
			log.error("getInteger:" + e);
		}
		return null;
	}

	/**
	 * 返回Integer或默认值
	 */
	public static Integer getInteger(Object obj, Integer defaultValue) {
		try {
			return obj == null ? defaultValue : Integer.parseInt(obj.toString());
		} catch (Exception e) {
			log.error("getInteger:" + e);
		}
		return null;
	}

	/**
	 * 处理sql条件
	 * 
	 * @param sql
	 * @return
	 */
	public static String dealSql(String sql) {
		if (sql != null) {
			if (sql.indexOf("where") == -1) {
				if (sql.indexOf("and") != -1 && sql.indexOf("AND") != -1) {
					if (sql.indexOf("and") < sql.indexOf("AND")) {
						sql = sql.replaceFirst("and", "where");
					} else {
						sql = sql.replaceFirst("AND", "where");
					}
				} else if (sql.indexOf("and") != -1) {
					sql = sql.replaceFirst("and", "where");
				} else if (sql.indexOf("AND") != -1) {
					sql = sql.replaceFirst("AND", "where");
				}
				log.info("处理sql条件:" + sql);
			}
		}
		return sql;
	}

	/**
	 * 查询一天的sql
	 * 
	 * @param params       已存在的条件值
	 * @param currentTime  查询列
	 * @param currentValue 查询值
	 */
	public static String dealFuncByCurrent(List<Object> params, String currentTime, Object currentValue) {
		StringBuilder sql = new StringBuilder();
		if (NullUtil.isNotNull(currentTime) && NullUtil.isNotNull(currentValue)) {
			if (dateJudgeYMDHMS(currentValue + " " + starthms) && dateJudgeYMDHMS(currentValue + " " + endhms)) {
				sql.append(" and " + currentTime + " >= ? ");
				params.add(DateUtils.getDate(currentValue + " " + starthms, DateUtils.YYYY_MM_DD_HHMMSS));
				sql.append(" and " + currentTime + " <= ? ");
				params.add(DateUtils.getDate(currentValue + " " + endhms, DateUtils.YYYY_MM_DD_HHMMSS));
				log.info("查询一天的sql:" + sql);
			}
		}
		return sql.toString();
	}

	/**
	 * 查询被更新的数据sql
	 * 
	 * @param params      已存在的条件值
	 * @param columnTime  查询列
	 * @param columnValue 查询值
	 */
	public static String dealFuncByUpdate(List<Object> params, String updateTime, Object updateValue) {
		StringBuilder sql = new StringBuilder();
		if (NullUtil.isNotNull(updateTime) && NullUtil.isNotNull(updateValue)) {
			if (dateJudgeYMDHMS(updateValue.toString())) {
				sql.append(" and " + updateTime + " > ? ");
				params.add(DateUtils.getDate(updateValue.toString(), DateUtils.YYYY_MM_DD_HHMMSS));
				log.info("查询被更新的数据sql:" + sql);
			}
		}
		return sql.toString();
	}

	/**
	 * 查询时间范围sql
	 * 
	 * @param params      已存在的条件值
	 * @param columnTime  查询列
	 * @param columnValue 查询值
	 * @param flag        判断符号
	 * @param ymd         校验格式
	 * @return
	 */
	public static String dealFuncByStartEnd(List<Object> params, String columnTime, Object columnValue, String flag,
			String ymd) {
		StringBuilder sql = new StringBuilder();
		if (NullUtil.isNotNull(columnTime) && NullUtil.isNotNull(columnValue)) {
			if (!dateJudge(columnValue.toString(), ymd) && ymd.indexOf(":ss") != -1) {
				ymd = ymd.replace(":ss", "");
			}
			if (dateJudge(columnValue.toString(), ymd)) {
				sql.append(" and " + columnTime + " " + flag + " ? ");
				if (DateUtils.YYYY_MM_DD.equals(ymd)) {
					if (!dateJudge(columnValue.toString(), ymd))
						return "";
					String date = DateUtils.getDate(DateUtils.getDate(columnValue.toString(), ymd), ymd);
					if (flag.indexOf(">") != -1) {
						params.add(DateUtils.getDate(date + " " + starthms, DateUtils.YYYY_MM_DD_HHMMSS));
					} else {
						params.add(DateUtils.getDate(date + " " + endhms, DateUtils.YYYY_MM_DD_HHMMSS));
					}
				} else {
					if (!dateJudge(columnValue.toString(), ymd))
						return "";
					params.add(DateUtils.getDate(columnValue.toString(), ymd));
				}
				log.info("查询时间范围sql:" + sql);
			}
		}
		return sql.toString();
	}

	/**
	 * 查询某个月范围sql
	 * 
	 * @param params      已存在的条件值
	 * @param columnTime  查询列
	 * @param columnValue 查询值
	 * @param flag        判断符号
	 * @param ymd         校验格式
	 * @return
	 */
	public static String dealFuncByYearMonth(List<Object> params, String columnTime, String yearValue,
			String monthValue) {
		StringBuilder sql = new StringBuilder();
		if (NullUtil.isNotNull(columnTime) && NullUtil.isNotNull(yearValue) && NullUtil.isNotNull(monthValue)) {
			try {
				sql.append(" and " + columnTime + " >= ? ");
				params.add(DateUtils.getDate(yearValue + "-" + monthValue + "-01 " + starthms,
						DateUtils.YYYY_MM_DD_HHMMSS));
				sql.append(" and " + columnTime + " <= ? ");
				int maxDay = DateUtils.getMaxDay(Integer.parseInt(yearValue), Integer.parseInt(monthValue));
				params.add(DateUtils.getDate(yearValue + "-" + monthValue + "-" + maxDay + " " + endhms,
						DateUtils.YYYY_MM_DD_HHMMSS));
				log.info("查询某个月范围sql:" + sql);
			} catch (Exception e) {
			}
		}
		return sql.toString();
	}

	/**
	 * 切割字符串id集，构建in条件
	 * 
	 * @param splitIds  id集
	 * @param splitChar 切割字符
	 */
	public static String splitIdsByInSelect(String splitIds, String splitChar) {
		try {
			if (NullUtil.isNotNull(splitIds) && NullUtil.isNotNull(splitChar)) {
				StringBuilder idssb = new StringBuilder();
				if (splitIds.indexOf(splitChar) != -1) {
					String[] ids = splitIds.split(splitChar);
					for (String id : ids) {
						if (idssb.length() == 0) {
							idssb.append("'" + id + "'");
						} else {
							idssb.append(",'" + id + "'");
						}
					}
					return idssb.toString();
				} else {
					return "'" + splitIds + "'";
				}
			}
		} catch (Exception e) {
			log.error("切割id集失败：" + e);
		}
		return null;
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param validateStr 指定的字符串
	 * @return 字符串的长度
	 */
	public static int getZnLength(String validateStr) {
		if (NullUtil.isNull(validateStr))
			return 0;
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < validateStr.length(); i++) {
			/* 获取一个字符 */
			String temp = validateStr.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 利用正则表达式将每个中文字符转换为"**" 匹配中文字符的正则表达式： [\u4e00-\u9fa5]
	 * 匹配双字节字符(包括汉字在内)：[^\x00-\xff]
	 * 
	 * @param validateStr
	 * @return 总长度
	 */
	public static int getLength(String validateStr) {
		if (NullUtil.isNull(validateStr))
			return 0;
		// String temp = validateStr.replaceAll("[\u4e00-\u9fa5]", "**");
		String temp = validateStr.replaceAll("[^\\x00-\\xff]", "**");
		return temp.length();
	}

	/**
	 * 校验值在match之内
	 * 
	 * @param value
	 * @param match
	 * @return
	 */
	public static boolean enumJudge(Object value, Object[] match) {
		if (NullUtil.isNotNull(value) && NullUtil.isNotNull(match)) {
			for (Object obj : match) {
				if (value.toString().equals(obj.toString())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断时间是否有误-年月日时分秒
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean dateJudgeYMDHMS(String dateStr) {
		if (NullUtil.isNotNull(dateStr)) {
			try {
				new SimpleDateFormat(DateUtils.YYYY_MM_DD_HHMMSS).parse(dateStr);
				return true;
			} catch (ParseException e) {
			}
		}
		return false;
	}

	/**
	 * 判断时间是否有误-自定义格式
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static boolean dateJudge(String dateStr, String format) {
		try {
			new SimpleDateFormat(format).parse(dateStr);
			return true;
		} catch (ParseException e) {
		}
		return false;
	}

	/**
	 * 判断时间是否有误-年月日时分
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean dateJudgeYMDHM(String dateStr) {
		if (NullUtil.isNotNull(dateStr)) {
			try {
				new SimpleDateFormat(DateUtils.YYYY_MM_DD_HHMM).parse(dateStr);
				return true;
			} catch (ParseException e) {
			}
		}
		return false;
	}

	/**
	 * 判断时间是否有误-年月日
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean dateJudgeYMD(String dateStr) {
		if (NullUtil.isNotNull(dateStr)) {
			try {
				new SimpleDateFormat(DateUtils.YYYY_MM_DD).parse(dateStr);
				return true;
			} catch (ParseException e) {
			}
		}
		return false;
	}

	/**
	 * 判断时间是否有误-时分秒
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean dateJudgeHMS(String dateStr) {
		if (NullUtil.isNotNull(dateStr)) {
			try {
				new SimpleDateFormat(DateUtils.HHMMSS).parse(dateStr);
				return true;
			} catch (ParseException e) {
			}
		}
		return false;
	}

	/**
	 * 正则表达式校验
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年6月20日 上午10:13:23
	 * @param rexp
	 * @param s
	 * @return
	 */
	public static boolean isPattern(String rexp, String s) {
		if (NullUtil.isNull(s)) {
			return false;
		}
		Matcher m = Pattern.compile(rexp).matcher(s);
		return m.matches();
	}

	public static boolean getBoolean(String value) {
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			return false;
		}
	}

	public static int getInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String[] getSplit(String str) {
		if (NullUtil.isNotNull(str)) {
			str = str.replace("；", ";").replace("，", ";").replace(",", ";");
			return str.split(";");
		}
		return new String[] {};
	}

	public static String getNull(Object object) {
		try {
			return NullUtil.isNull(object) ? null : object.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getEmpty(Object object) {
		try {
			return NullUtil.isNull(object) ? "" : object.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean equals(Object value1, Object value2) {
		if (NullUtil.isNotNull(value1) && NullUtil.isNotNull(value2)) {
			return value1.toString().equals(value2.toString());
		}
		return false;
	}

	public static boolean equalsIgnoreCase(Object value1, Object value2) {
		if (NullUtil.isNotNull(value1) && NullUtil.isNotNull(value2)) {
			return value1.toString().equalsIgnoreCase(value2.toString());
		}
		return false;
	}

	/**
	 * 构建固定长度数值，不够在前面加0
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2018年6月1日 上午9:49:59
	 * @param source 需要处理的值
	 * @param len    返回长度
	 * @return
	 */
	public static String getFixedLen(long source, int len) {
		String str = String.valueOf(source);
		StringBuilder sBuffer = new StringBuilder();
		int i = 0, max = len - str.length();
		while (i < max) {
			sBuffer.append("0");
			i += 1;
		}
		sBuffer.append(str);
		return sBuffer.toString();
	}

}
