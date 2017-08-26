package com.yitianyike.calendar.pullserver.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class PropertiesUtil {

	private static Properties config = null;

	static {
		InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
		config = new Properties();
		try {
			config.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int httpPort = getIntValue("http.port");
	public static int dbRegisterTables = getIntValue("db.register.tables");
	public static int dbNo = getIntValue("db.no");
	public static int dbUserNo = getIntValue("db.user.no");
	public static int dbSubscribeNo = getIntValue("db.subscribe.no");
	public static int redisNo = getIntValue("redis.no");
	public static int redisCount = getIntValue("redis.count");
	public static String channel_code = getValue("channel_code");
	public static String version = getValue("version");
	public static String video_extend = getValue("video_extend");

	public static String getValue(String key) {
		return StringUtils.trim(config.getProperty(key));
	}

	public static int getIntValue(String key) {
		return NumberUtils.toInt(config.getProperty(key));
	}

	public static boolean getBooleanValue(String key) {
		return BooleanUtils.toBoolean(config.getProperty(key));
	}

}
