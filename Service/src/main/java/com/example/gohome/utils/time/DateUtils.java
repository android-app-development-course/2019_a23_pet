package com.example.gohome.utils.time;

import com.example.gohome.utils.NullUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间工具类
 */
public final class DateUtils {
	
	private static Logger LoggerUtil = LoggerFactory.getLogger("DateUtils");

	public static final String YYYYMMDDHHMMSS_SSS = "yyyyMMddHHmmssSSS";
	public static final String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String YYYYMMDDhhmmss = "YYYYMMDDhhmmss";
	public static final String YYYY_MM_DD_HHMM = "yyyy-MM-dd HH:mm";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_T = "yyyy-MM-dd %T";
	public static final String YYYY_MM = "yyyy-MM";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYMMDD = "yyMMdd";
	public static final String YYYYMM = "yyyyMM";
	public static final String YYYY = "yyyy";
	public static final String MM = "MM";
	public static final String HHMMSS = "HH:mm:ss";
	public static final String HHMM = "HH:mm";
	public static final String MM_DD_HHMMSS = "MM-dd HH:mm:ss";
	public static final String MM_DD_HHMM = "MM-dd HH:mm";
	public static final String MM_DD = "MM-dd";

	public static final String YYYY_MM_DD_T_HHMMSS_Z = "yyyy-MM-dd'T'HH:mm:ssZ";

	public final static long MINUTE = 60 * 1000L; // 分钟
	public final static long HOUR = 60 * 60 * 1000L; // 小时
	public final static long DAY = 24 * 60 * 60 * 1000L; // 天

	/**
	 * 日期转为字符串
	 * 
	 * @return String
	 */
	public final static String getDate(Date oDate, String sDateFormat) {
		try {
			if (NullUtil.isNotNull(oDate)) {
				return new SimpleDateFormat(sDateFormat).format(oDate);
			}
		} catch (Exception oException) {
			LoggerUtil.error(oException.getMessage(), oException);
		}
		return null;
	}

	/**
	 * 字符串转为日期字符串
	 * 
	 * @return String
	 */
	public final static String getDateStr(String strDate) {
		try {
			if (NullUtil.isNotNull(strDate)) {
				Date date = isDate(strDate);
				if (date != null) {
					return getDate(date, getFormat(strDate));
				}
			}
		} catch (Exception oException) {
			LoggerUtil.error(oException.getMessage(), oException);
		}
		return null;
	}

	/**
	 * 字符串转日期
	 * 
	 * @return Date
	 */
	public final static Date getDate(String dateStr, String sDateFormat) {
		try {
			if (NullUtil.isNotNull(dateStr)) {
				return new SimpleDateFormat(sDateFormat).parse(dateStr);
			}
		} catch (Exception oException) {
			LoggerUtil.error(oException.getMessage(), oException);
		}
		return null;
	}

	/**
	 * 字符串（时间戳）转日期
	 * 
	 * @return Date
	 */
	public final static String getDateOfTime(String timeStr, String sDateFormat) {
		try {
			return getDateOfTime(Long.parseLong(timeStr), sDateFormat);
		} catch (Exception oException) {
			LoggerUtil.error(oException.getMessage(), oException);
		}
		return null;
	}

	/**
	 * 时间戳转日期
	 * 
	 * @return Date
	 */
	public final static String getDateOfTime(Long time, String sDateFormat) {
		try {
			return new SimpleDateFormat(sDateFormat).format(new Date(time));
		} catch (Exception oException) {
			LoggerUtil.error(oException.getMessage(), oException);
		}
		return null;
	}

	/**
	 * 获取指定格式的当前时间
	 * 
	 * @return Date
	 */
	public final static String getDate(String sDateFormat) {
		try {
			return new SimpleDateFormat(sDateFormat).format(new Date());
		} catch (Exception oException) {
			LoggerUtil.error(oException.getMessage(), oException);
		}
		return null;
	}

	/**
	 * 获取指定格式的当前时间
	 */
	public final static String getDate_ymdhms() {
		try {
			return new SimpleDateFormat(YYYY_MM_DD_HHMMSS).format(new Date());
		} catch (Exception oException) {
			LoggerUtil.error(oException.getMessage(), oException);
		}
		return null;
	}

	/**
	 * 获取指定格式的当前时间
	 */
	public final static String getDate_ymdhm() {
		try {
			return new SimpleDateFormat(YYYY_MM_DD_HHMM).format(new Date());
		} catch (Exception oException) {
			LoggerUtil.error(oException.getMessage(), oException);
		}
		return null;
	}

	/**
	 * 获取指定格式的当前时间
	 */
	public final static String getDate_ymd() {
		try {
			return new SimpleDateFormat(YYYY_MM_DD).format(new Date());
		} catch (Exception oException) {
			LoggerUtil.error(oException.getMessage(), oException);
		}
		return null;
	}

	/**
	 * 获取指定格式的当前时间
	 */
	public final static String getDate_ym() {
		try {
			return new SimpleDateFormat(YYYY_MM).format(new Date());
		} catch (Exception oException) {
			LoggerUtil.error(oException.getMessage(), oException);
		}
		return null;
	}

	/**
	 * 获取指定格式的当前时间
	 */
	public final static String getDate_y() {
		try {
			return new SimpleDateFormat(YYYY).format(new Date());
		} catch (Exception oException) {
			LoggerUtil.error(oException.getMessage(), oException);
		}
		return null;
	}

	/**
	 * 根据日期返回星期几,1-7
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day == 1) {
			day = 7;
		} else {
			day = day - 1;
		}
		return day;
	}

	/**
	 * 根据日期返回星期几
	 * 
	 * @param dt
	 * @return
	 */
	public static String getDayOfWeekStr(Date date) {
		final String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		return weekDays[getDayOfWeek(date)];
	}

	/**
	 * 第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMathFirst(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		return cal.getTime();
	}

	/**
	 * 最后一天
	 * 
	 * @param date
	 */
	public static Date getMathLast(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * 获取一个月份的最大天数
	 */
	public static int getMaxDay(Date currentDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取某个月的最大天数
	 * 
	 * @param year
	 * @param month
	 */
	public static int getMaxDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		return a.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取下一天的时间
	 */
	public static Date getNextDay(Date currentDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DAY_OF_YEAR, +1);
		return cal.getTime();
	}

	/**
	 * 对时间加减crud秒
	 */
	public static Date getCrudSecond(Date currentDate, int crud) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.SECOND, crud);
		return cal.getTime();
	}

	/**
	 * 对时间加减crud小时
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2018年4月26日 下午3:48:04
	 * @param currentDate
	 * @param crud
	 * @return
	 */
	public static Date getCrudHour(Date currentDate, int crud) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.HOUR, crud);
		return cal.getTime();
	}

	/**
	 * 对时间加减crud天
	 */
	public static Date getCrudDay(Date currentDate, int crud) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DAY_OF_YEAR, crud);
		return cal.getTime();
	}

	/**
	 * 获取上一天的时间
	 */
	public static Date getYesterday(Date currentDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return cal.getTime();
	}

	/***
	 * 获取GMT8(北京时间)时间
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getFormatDateGMT8(String dateStr) {
		try {
			return getDate(getDate(dateStr, YYYY_MM_DD_HHMMSS), YYYY_MM_DD_T_HHMMSS_Z);
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage(), e);
		}
		return null;
	}

	/***
	 * 获取GMT8（北京时间）时间
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getFormatDateGMT8(Date date) {
		try {
			return getDate(date, YYYY_MM_DD_T_HHMMSS_Z);
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 得到当前UTC(世界标准时间)时间
	 */
	public static Date getCurrentUTCDate() {
		// 1、取得本地时间：
		final Calendar cal = Calendar.getInstance(Locale.CHINA);
		// 2、取得时间偏移量：
		final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		final int dstOffset = cal.get(Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return cal.getTime();
	}

	/**
	 * 得到当前UTC(世界标准时间)时间
	 */
	public static Date getUTCDate(Date localDate) {
		// 1、取得本地时间：
		final Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(localDate);
		// 2、取得时间偏移量：
		final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		final int dstOffset = cal.get(Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return cal.getTime();
	}

	/**
	 * 得到当前UTC(世界标准时间)时间
	 */
	public static Date getUTCDate(String localDateStr, String format) {
		return getUTCDate(getDate(localDateStr, format));
	}

	/**
	 * utc转换为本地时间
	 * 
	 * @param utcDateStr
	 * @param datefmt
	 * @return
	 */
	public static String utc2Local(String utcDateStr, String datefmt) {
		SimpleDateFormat utcsdf = new SimpleDateFormat(datefmt);
		utcsdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat localsdf = new SimpleDateFormat(datefmt);
		localsdf.setTimeZone(TimeZone.getTimeZone("GMT-8"));
		try {
			return localsdf.format(utcsdf.parse(utcDateStr).getTime());
		} catch (ParseException e) {
			LoggerUtil.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * utc转换为本地时间
	 * 
	 * @param utcDate
	 * @param datefmt
	 * @return
	 */
	public static String utc2Local(Date utcDate, String datefmt) {
		SimpleDateFormat localsdf = new SimpleDateFormat(datefmt);
		localsdf.setTimeZone(TimeZone.getTimeZone("GMT-8"));
		return localsdf.format(utcDate.getTime());
	}

	/**
	 * 本地时间转换为utc时间
	 * 
	 * @param localTime
	 * @param datefmt
	 * @return
	 */
	public static String local2UTC(String localTime, String datefmt) {
		return getDate(getUTCDate(localTime, datefmt), datefmt);
	}

	/**
	 * 本地时间转换为utc时间
	 * 
	 * @param localDate
	 * @param datefmt
	 */
	public static String local2UTC(Date localDate, String datefmt) {
		return local2UTC(getDate(localDate, datefmt), datefmt);
	}

	/**
	 * 判断是否符合日期格式，是的话并返回
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年3月27日 下午2:41:38
	 * @param dateStr
	 * @return
	 */
	public static Date isDate(String dateStr) {
		Date formatDate = null;
		if (isDateOfYmd(dateStr)) {// 先校验yyyy-mm-dd结构是否正确
			try {
				formatDate = new SimpleDateFormat(YYYY_MM_DD_HHMMSS).parse(dateStr);
			} catch (ParseException e) {
				try {
					formatDate = new SimpleDateFormat(YYYY_MM_DD_HHMM).parse(dateStr);
				} catch (ParseException e1) {
					try {
						formatDate = new SimpleDateFormat(YYYY_MM_DD).parse(dateStr);
					} catch (ParseException e2) {
					}
				}
			}
		}
		return formatDate;
	}

	/**
	 * 判断是否符合日期格式，是的话并返回可以转换的格式
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年3月27日 下午2:41:38
	 * @param dateStr
	 * @return
	 */
	public static String getFormat(String dateStr) {
		String format = null;
		if (isDateOfYmd(dateStr)) {
			try {
				new SimpleDateFormat(YYYY_MM_DD_HHMMSS).parse(dateStr);
				format = YYYY_MM_DD_HHMMSS;
			} catch (ParseException e) {
				try {
					new SimpleDateFormat(YYYY_MM_DD_HHMM).parse(dateStr);
					format = YYYY_MM_DD_HHMM;
				} catch (ParseException e1) {
					try {
						new SimpleDateFormat(YYYY_MM_DD).parse(dateStr);
						format = YYYY_MM_DD;
					} catch (ParseException e2) {
					}
				}
			}
		}
		return format;
	}

	/**
	 * 校验日期是否合法 yyyy-mm-dd
	 */
	public static boolean isDateOfYmd(String value) {
		if (NullUtil.isNotNull(value) && value.length() >= 10) {
			String dateStr = value.substring(0, 10);
			String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
			Pattern pat = Pattern.compile(rexp);
			Matcher mat = pat.matcher(dateStr);
			return mat.matches();
		}
		return false;
	}

	/**
	 * 判断是否大于当前时间
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年6月21日 下午2:40:04
	 * @param dateStr
	 * @return
	 */
	public static boolean isAfterNow(String dateStr) {
		Date date = isDate(dateStr);
		if (date != null) {
			return date.getTime() > System.currentTimeMillis();
		}
		return false;
	}

	/**
	 * 获取当天剩余的毫秒数,千分之一秒
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2018年2月9日 下午3:58:14
	 * @return
	 */
	public static long getSurplusMills() {
		String dateStr = DateUtils.getDate(getCrudDay(new Date(), 1), DateUtils.YYYY_MM_DD);
		return (DateUtils.getDate(dateStr, DateUtils.YYYY_MM_DD).getTime() - System.currentTimeMillis());
	}

	/**
	 * 获取两个日期之间的天数<br/>
	 * (如果日期一样==1)
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2018年4月8日 下午2:30:31
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDay(String startDate, String endDate) {
		return getDay(DateUtils.getDate(startDate, DateUtils.YYYY_MM_DD),
				DateUtils.getDate(endDate, DateUtils.YYYY_MM_DD));
	}

	/**
	 * 获取两个日期之间的天数<br/>
	 * (如果日期一样==1)
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2018年4月8日 下午2:30:49
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDay(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			startDate = DateUtils.getDate(DateUtils.getDate(startDate, DateUtils.YYYY_MM_DD), DateUtils.YYYY_MM_DD);
			endDate = DateUtils.getDate(DateUtils.getDate(endDate, DateUtils.YYYY_MM_DD), DateUtils.YYYY_MM_DD);
			long difference = (endDate.getTime() - startDate.getTime()) / 86400000;
			return (int) Math.abs(difference);
		}
		return -1;
	}

}
