package com.tb.manager.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zeting
 */
public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);

	public static final String TIME_PATTERN = "HH:mm";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String LONG_DATE = "yyyy��MM��dd��";

	private DateUtil() {
	}

	public static String getDateTimePattern() {
		return DateUtil.DATE_PATTERN + " HH:mm:ss.S";
	}

	public static String getDate(Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(DATE_PATTERN);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	public static String getTimeNow(Date theTime) {
		return getDateTime(TIME_PATTERN, theTime);
	}

	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static String convertDateToString(Date aDate) {
		return getDateTime(DATE_PATTERN, aDate);
	}
	
	public static String convertDateToLong(Date aDate) {
		return getDateTime(LONG_DATE, aDate);
	}

	public static Date convertStringToDate(String strDate)
			throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + DATE_PATTERN);
			}

			aDate = convertStringToDate(DATE_PATTERN, strDate.trim());
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return aDate;
	}
	
	public static int getCurrentYear(){
		Calendar cal = new GregorianCalendar();
		return cal.get(Calendar.YEAR);
	}
}
