package com.yitianyike.calendar.pullserver.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yitianyike.calendar.pullserver.model.responseCardData.YMD;

public class DateUtil {
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private static final DateFormat timeFormat = new SimpleDateFormat("hhmmss");
	private static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String getCurrentDate() {
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	public static String getyyyyMMddDate(Date date) {
		return dateFormat.format(date);
	}

	public static String getCurrentTime() {
		return timeFormat.format(Calendar.getInstance().getTime());
	}

	public static String getCurrentDateTime() {
		return dateTimeFormat.format(new Date());
	}

	public static String getDecreaseDate(String befDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String decreaseDate = null;
		try {
			Date parse = sdf.parse(befDate);
			Calendar date = Calendar.getInstance();
			date.setTime(parse);
			date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);
			decreaseDate = sdf.format(date.getTime());
		} catch (ParseException e) {
			return decreaseDate;
		}
		return decreaseDate;
	}

	public static List<String> findStringDates() {
		List<String> responseDates = new ArrayList<String>();
		Calendar calB = Calendar.getInstance();
		calB.add(Calendar.DAY_OF_MONTH, -10);
		Calendar calE = Calendar.getInstance();
		calE.add(Calendar.DAY_OF_MONTH, 10);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Date> lDate = findDates(calB.getTime(), calE.getTime());
		for (Date date : lDate) {
			responseDates.add(sdf.format(date));
		}
		return responseDates;
	}

	public static List<Date> findStringTenDates() {
		Calendar calB = Calendar.getInstance();
		calB.add(Calendar.DAY_OF_MONTH, -10);
		Calendar calE = Calendar.getInstance();
		calE.add(Calendar.DAY_OF_MONTH, 10);
		List<Date> lDate = findDates(calB.getTime(), calE.getTime());
		return lDate;
	}

	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}

	public static YMD getYearMonthDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		YMD ymd = new YMD();
		try {
			Date parse = sdf.parse(date);
			Calendar cdate = Calendar.getInstance();
			cdate.setTime(parse);
			int y = cdate.get(Calendar.YEAR);
			int m = cdate.get(Calendar.MONTH);
			int d = cdate.get(Calendar.DATE);
			ymd.setY(Integer.toString(y));
			ymd.setM(Integer.toString(m));
			ymd.setD(Integer.toString(d));

		} catch (ParseException e) {
			return null;
		}
		return ymd;
	}

	public static List<Date> findDates(String dBegins, String dEnds) {
		Date dBegin = null;
		Date dEnd = null;
		List<Date> lDate = new ArrayList<Date>();
		try {
			dBegin = new Date(Long.parseLong(dBegins));//
			// dBegin = dateTimeFormat.parse(dBegins);
			dEnd = new Date(Long.parseLong(dEnds));
			lDate.add(dBegin);
			Calendar calBegin = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calBegin.setTime(dBegin);
			Calendar calEnd = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calEnd.setTime(dEnd);
			// 测试此日期是否在指定日期之后
			while (dEnd.after(calBegin.getTime())) {
				// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
				calBegin.add(Calendar.DAY_OF_MONTH, 1);
				lDate.add(calBegin.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return lDate;
		}
		return lDate;
	}

	public static void main(String[] args) {
		YMD yearMonthDate = getYearMonthDate("2017-04-13");
		System.out.println("");
		List<Date> findDates = findDates(new Date().getTime() + "", new Date().getTime() + "");
		for (Date date : findDates) {
			System.out.println(date);
		}

	}

	public static String getLongTime(String dateString) {
		Date parse = null;
		try {
			parse = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String dateLong = Long.toString(parse.getTime());
		return dateLong;
	}

}
