package com.gemmystar.api.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * DateUtil
 *
 * @author 'intune.kang'
 * @version
 * @see
 */
public class DateUtil {

	/**
	 * DateUtil
	 *
	 * @param
	 * @exception
	 */
	public DateUtil() {
	}

	public static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	/** The Constant YEAR. */
	public static final int YEAR = 1;

	/** The Constant MONTH. */
	public static final int MONTH = 2;

	/** The Constant DATE. */
	public static final int DATE = 3;

	/** The Constant MONTHFIRST. */
	public static final int MONTHFIRST = 4;

	/** The Constant MONTHEND. */
	public static final int MONTHEND = 5;

	/** The Constant log. */
	// private static final Logger log =
	// LoggerFactory.getLogger(DateUtil.class);

	/** 달의 일자. */
	public static final String[] DATES_OF_MONTH = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
		"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

	/** 년의 달들. */
	public static final String[] MONTHES_OF_YEAR = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

	/** Month Names. */
	public static final String[] MONTH_NAMES = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	// 자동 변환용 패턴 설정
	/** The Constant patterns. */
	private static final String[] PATTERNS = { "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss.S", "yyyy-MM-dd HH:mm:ss",
		"yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd", "yyyyMMddHHmmssSSS", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyyMMddHH", "yyyyMMdd",
		"yyMMdd" };

	// private static final String defaultTimePattern =
	// "E MMM d HH:mm:ss z yyyy";
	// private static final String defaultDatePattern = "E MMM d z yyyy";

	/**
	 * Gets the date format.
	 *
	 * @param pattern
	 *            the pattern
	 * @return the date format
	 */
	public static DateFormat getDateFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	/**
	 * <p>
	 * 문자열을 입력받아 패턴에 맞게 날짜형(Date)으로 변환하여 반환한다.<br/>
	 * 기준시간은 UTC 0 로 맞추어 생성.
	 * </p>
	 *
	 * <pre>
	 * toDate(&quot;2008-11-11 06:15:00&quot;, &quot;yyyy-MM-dd HH:mm:ss&quot;) = Date@&quot;2008-11-11 06:15:00&quot;
	 * toDate(&quot;Time is Gold&quot;, &quot;yyyy-MM-dd HH:mm:ss&quot;)        = ParseException
	 * toDate(null, *)                                      = ParseException
	 * </pre>
	 *
	 * @param pattern
	 *            the pattern
	 * @param strDate
	 *            the str date
	 * @return the date
	 * @param hAmmount
	 *            시간(hour) 증가값. 음수 허용.
	 */
	public static Date toDate(String strDate, String pattern, int hAmmount) {
		return toDate(strDate, pattern, TimeZone.getTimeZone("UTC"), hAmmount);
	}

	/**
	 * <p>
	 * 문자열을 입력받아 패턴에 맞게 날짜형(Date)으로 변환하여 반환한다.<br/>
	 * 입력된 TimeZone 의 시간으로 맞추어 생성.
	 * </p>
	 *
	 * <pre>
	 * toDate(&quot;2008-11-11 06:15:00&quot;, &quot;yyyy-MM-dd HH:mm:ss&quot;) = Date@&quot;2008-11-11 06:15:00&quot;
	 * toDate(&quot;Time is Gold&quot;, &quot;yyyy-MM-dd HH:mm:ss&quot;)        = ParseException
	 * toDate(null, *)                                      = ParseException
	 * </pre>
	 *
	 * @param pattern
	 *            the pattern
	 * @param strDate
	 *            the str date
	 * @param timeZone
	 * @param hAmmount
	 *            시간(hour) 증가값. 음수 허용.
	 * @return the date
	 */
	public static Date toDate(String strDate, String pattern, TimeZone timeZone, int hAmmount) {
		Date date = null;

		DateFormat dateFormat = getDateFormat(pattern);
		Assert.notNull(timeZone, "timeZone is required.");

		dateFormat.setTimeZone(timeZone);

		try {
			date = dateFormat.parse(strDate);

			if (date != null && hAmmount != 0) {
				date = addHour(date, hAmmount);
			}
		} catch (ParseException ex) {
			throw new RuntimeException(ex);
		}
		return date;
	}

	/**
	 * <p>
	 * 문자열을 입력받아 날짜형(Date)으로 변환한다. 기본 UTC 0 의 시간으로 변환. - 포맷 자동 인식함.
	 * </p>
	 *
	 * <pre>
	 * DateUtils.toDate(null)                  = null
	 * DateUtils.toDate(&quot;2008-11-11&quot;)          = Date@&quot;2008-11-11 00:00:00&quot;
	 * DateUtils.toDate(&quot;2008-11-11 06:15:00&quot;) = Date@&quot;2008-11-11 06:15:00&quot;
	 * </pre>
	 *
	 * <h4>지원하는형식</h4>
	 * <ul>
	 * <li>yyyy-MM-dd HH:mm:ss.SSS
	 * <li>yyyy-MM-dd HH:mm:ss.S
	 * <li>yyyy-MM-dd HH:mm:ss
	 * <li>yyyy-MM-dd HH:mm
	 * <li>yyyy-MM-dd HH
	 * <li>yyyy-MM-dd
	 * <li>yyyyMMddHHmmssSSS
	 * <li>yyyyMMddHHmmss
	 * <li>yyyyMMddHHmm
	 * <li>yyyyMMddHH
	 * <li>yyyyMMdd
	 * </ul>
	 *
	 * @param strDate
	 *            the str date
	 * @return the date
	 * @param hAmmount
	 *            시간(hour) 증가값. 음수 허용.
	 * @throws IllegalArgumentException
	 *             지원하지 않는 형식일 경우
	 */

	public static Date toDate(String strDate, int hAmmount) {
		return toDate(strDate, TimeZone.getTimeZone("UTC"), hAmmount);
	}

	/**
	 * <p>
	 * 문자열을 입력받아 날짜형(Date)으로 변환한다. 입력받은 TimeZone 의 시간으로 변환.
	 * </p>
	 *
	 * @param strDate
	 * @param timeZone
	 * @param hAmmount
	 *            시간(hour) 증가값. 음수 허용.
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Date toDate(String strDate, TimeZone timeZone, int hAmmount) {

		if (strDate == null) {
			return null;
		}

		strDate = strDate.trim();
		int lenStrDate = strDate.length();
		Date date = null;

		for (String pattern : PATTERNS) {

			if (lenStrDate == pattern.length()) {

				DateFormat dateFormat = getDateFormat(pattern);
				dateFormat.setTimeZone(timeZone);

				try {
					date = dateFormat.parse(strDate);

					if (date != null && hAmmount != 0) {
						date = addHour(date, hAmmount);
					}

					if (!strDate.equals(dateFormat.format(date))) {
						date = null;
					}
				} catch (ParseException ex) {
					date = null;
				}
			}
		}

		return date;
	}

	/**
	 * <pre>
	 * 시간 증감 시키기.
	 * </pre>
	 *
	 * @param date
	 *            증감할 date instance
	 * @param i
	 *            증감값 (음수값 허용)
	 * @return
	 */
	public static Date addHour(Date date, int ammount) {
		// date.setDate(date.getDate() + ammount);
		// return date;

		Calendar cldr = Calendar.getInstance();
		cldr.setTime(date);
		cldr.add(Calendar.HOUR, ammount);

		return cldr.getTime();
	}
	
	public static Date addDay(Date date, int ammount) {
		// date.setDate(date.getDate() + ammount);
		// return date;

		Calendar cldr = Calendar.getInstance();
		cldr.setTime(date);
		cldr.add(Calendar.DATE, ammount);

		return cldr.getTime();
	}

	/**
	 * <pre>
	 * java.util.Date 를 특정지역(TimeZone)의 문자열로 변환한다.
	 * </pre>
	 *
	 * @param date
	 *            변환할 Date instance.
	 * @param pattern
	 *            변환할 포맷
	 * @param zone
	 *            the given new time zone.
	 * @param hAmmount
	 *            시간(hour) 증가값. 음수 허용.
	 * @return
	 */
	public static String toDateStr(Date date, String pattern, TimeZone zone, int hAmmount) {
		DateFormat df = getDateFormat(pattern);
		df.setTimeZone(zone);

		if (date != null && hAmmount != 0) {
			date = addHour(date, hAmmount);
		}

		return df.format(date);
	}

	/**
	 * <pre>
	 * java.util.Date 를 특정국가(Locale)의 특정스타일로 문자열로 변환한다.
	 * - 년월일 요일 까지 출력한다.
	 * </pre>
	 *
	 * @param date
	 *            변환할 Date instance.
	 * @param style
	 *            출력포맷스타일 DateFormat.FULL, LONG, MEDIUM, SHORT
	 * @param aLcale
	 *            the given locale.
	 * @param zone
	 *            the given new time zone.
	 * @param hAmmount
	 *            시간(hour) 증가값. 음수 허용.
	 * @return
	 */
	public static String toDateStr(Date date, int style, Locale aLcale, TimeZone zone, int hAmmount) {
		// DateFormat df = DateFormat.getDateInstance(DateFormat.LONG,
		// Locale.FRANCE);
		DateFormat df = DateFormat.getDateInstance(style, aLcale);
		df.setTimeZone(zone);
		if (date != null && hAmmount != 0) {
			date = addHour(date, hAmmount);
		}
		return df.format(date);
	}

	/**
	 *
	 * <pre>
	 * java.util.Date 를 특정국가(Locale)의 특정스타일로 문자열로 변환한다.
	 * - 년월일 요일 시분초까지 출력한다.
	 * </pre>
	 *
	 * @param date
	 *            date 변환할 Date instance.
	 * @param dateStyle
	 *            출력포맷스타일 DateFormat.FULL, LONG, MEDIUM, SHORT
	 * @param timeStyle
	 *            출력포맷스타일 DateFormat.FULL, LONG, MEDIUM, SHORT
	 * @param aLcalethe
	 *            given locale.
	 * @param zone
	 *            the given new time zone.
	 * @param hAmmount
	 *            시간(hour) 증가값. 음수 허용.
	 * @return
	 */
	public static String toDateTimeStr(Date date, int dateStyle, int timeStyle, Locale aLcale, TimeZone zone, int hAmmount) {

		DateFormat df = DateFormat.getDateTimeInstance(dateStyle, timeStyle, aLcale);
		df.setTimeZone(zone);
		if (date != null && hAmmount != 0) {
			date = addHour(date, hAmmount);
		}
		return df.format(date);
	}

	/**
	 * GMT의 시각 형태로 표현하기 위한 Formatter.
	 *
	 * @return DateFormat / GMT Time formatter
	 */
	public static DateFormat getGMTDateFormat() {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));

		return format;
	}

	/**
	 * yyyyMMddHHmmss의 문자열을 GMT의 Date형 객체로 반환하는 메소드.
	 *
	 * @param date
	 *            the date
	 * @return Date / GMT 시간으로 설정된 Date 객체
	 */
	public static Date getGMTDate(String date) {
		DateFormat format = getGMTDateFormat();
		try {
			return format.parse(date);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 현재 날짜를 반환한다.
	 *
	 * @return the current date
	 */
	public static Date getCurrentDate() {
		//return new Date(System.currentTimeMillis());
		return Calendar.getInstance().getTime();
	}

	/**
	 * 현재 날짜를 yyyyMMddHHmmss의형식으로 반환한다.
	 *
	 * @return the current date
	 */
	public static String getCurrentString() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(getCurrentDate());
	}


	/**
	 * startDate 와 endDate 사이에 있는지 확인
	 *
	 * @param targetDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isBetweenDate(Date targetDate, Date startDate, Date endDate) {
		boolean isBetweenDate = false;
		if (targetDate != null && targetDate.compareTo(startDate) >= 0 && targetDate.compareTo(endDate) <= 0) {
			isBetweenDate = true;
		} else {
			isBetweenDate = false;
		}
		return isBetweenDate;
	}

	/**
	 * Convert date into milliseconds
	 *
	 * @param strDate
	 * @return
	 */
	public static long dateToMills(String strDate, String format) {
		long mills = -999999;
		Date date = new Date();
		try {
			date = new SimpleDateFormat(format).parse(strDate);
		} catch (ParseException ex) {
			mills = -999999;
		}
		mills = date.getTime();

		return mills;
	}

	/**
	 *
	 *
	 * @param strDate
	 * @return
	 */
	public static Date getDateFromString(String strDate) {
		return getDateFromString(strDate, true);
	}

	public static Date getDateFromString(String strDate, boolean timezone) {
		Date date = null;
		String time = "";
		if (timezone) {
			time = "+00:00";
		}

		try {
			date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss" + time).parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static Date subtractDays(int days){
		Date date = new Date();
		Date daysAgo = new DateTime(date).minusDays(days).toDate();
		return daysAgo;
	}

	public static String toFormattedString(Date date){
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		return sm.format(date);
	}
	
	public static String toFormattedString(Date date, SimpleDateFormat sm){
		return sm.format(date);
	}
	/*
	public static void main(String[] args) {
		Date now = DateUtil.getCurrentDate();
		System.out.println(DateUtil.toFormattedString(now));
		
		Date after = DateUtil.addDay(now, 7);
		System.out.println(DateUtil.toFormattedString(after));
	}*/
}
