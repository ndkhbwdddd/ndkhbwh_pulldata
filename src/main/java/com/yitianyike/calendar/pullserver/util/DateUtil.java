package com.yitianyike.calendar.pullserver.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtil {
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private static final DateFormat timeFormat = new SimpleDateFormat("hhmmss");
	private static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getCurrentDate(){
		return dateFormat.format(Calendar.getInstance().getTime());
	}
	
	public static String getCurrentTime(){
		return timeFormat.format(Calendar.getInstance().getTime());
	}
	
	public static String getCurrentDateTime(){
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
	
	public static void main(String[] args) {
		System.out.println(getCurrentDate());
		System.out.println(getCurrentTime());
		System.out.println(getCurrentDateTime());
	}
}
