package com.yitianyike.calendar.pullserver.common;

import java.util.HashMap;
import java.util.Map;

import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

public class EnumConstants {

	/**
	 * content type
	 */
	public final static String PLAIN_CONTENT_TYPE = "text/plain; charset=UTF-8";
	public final static String HTML_CONTENT_TYPE = "text/html; charset=UTF-8";

	/**
	 * all request error id and info
	 */
	public static final int MICROCHAT_ERROR_EXCEPTION_SERVER = 1;
	public static final int MICROCHAT_ERROR_SERVER = 2;
	public static final int MICROCHAT_ERROR_USER = 3;
	public static final int MICROCHAT_ERROR_PARAM = 4;
	public static final int MICROCHAT_ERROR_NO_USER = 5;
	public static final int MICROCHAT_ERROR_NO_FRIEND = 6;
	public static final int MICROCHAT_WARN_NO_MESSAGE = 7;

	/**
	 * message is read or not 0 : not read 1 : have read
	 */
	public static final int MESSAGE_NOT_READ = 0;
	public static final int MESSAGE_HAVE_READ = 1;

	/**
	 * minimum file size to compression.
	 */
	public static final int COMPRESSION_STANDARD = PropertiesUtil.getIntValue("compression.standard");

	/**
	 * 消息已读、未读状态 0： 未读 1： 已读
	 */
	public static final byte MSG_UNREAD = 0;
	public static final byte MSG_READED = 1;

	public static final int USER_STATUS_OFFLINE = 0;
	public static final int USER_STATUS_ONLINE = 1;

	public static final int ATTACH_PIC = 1;
	public static final int ATTACH_AUDIO = 2;
	public static final int ATTACH_VIDEO = 3;

	public static final int USER_TYPE_CUSTOMER = 0;
	public static final int USER_TYPE_SHOP = 1;

	public static final String USER_HEAD_IMG_URL = PropertiesUtil.getValue("user.head.img.url"); // 用户头像URL
	public static final String SHOP_HEAD_IMG_URL = PropertiesUtil.getValue("shop.head.img.url"); // 商户头像URL

	public static final String SHOP_TYPE_FOR_PUSH_SERVICE = "1";
	public static final String USER_TYPE_FOR_PUSH_SERVICE = "2";

	// message type
	public static final int MESSAGE_TYPE_SINGLE_CHAT = 0;
	public static final int MESSAGE_TYPE_GROUP_CHAT = 1;
	public static final int MESSAGE_TYPE_SYSTEM_MSG = 2;
	public static final int MESSAGE_TYPE_ADD_FRIEND = 10;
	public static final int MESSAGE_TYPE_VERIFY_FRIEND = 11;
	public static final int MESSAGE_TYPE_DEL_FRIEND = 12;
	public static final int MESSAGE_TYPE_CREATE_GROUP = 13;
	public static final int MESSAGE_TYPE_ADD_GROUP = 14;
	public static final int MESSAGE_TYPE_EXIT_GROUP = 15;
	public static final int MESSAGE_TYPE_USER_INFO = 16;

	public static Map<String, Map<String, Map<String, String>>> CHANNEL = new HashMap<String, Map<String, Map<String, String>>>();

	public static final String MESSAGE_VERIFY = "我通过了你的好友验证请求，现在我们可以开始聊天了";

	// 类型码=========================================================

	// 黄历
	public static final int ALMANAC = 6;
	// 节日
	public static final int FESTIVAL = 7;
	// 彩票
	public static final int LOTTERY = 9;
	// 电影
	public static final int FILM = 8;
	// 壹天美图
	public static final int PICTURE = 10;
	public static final String COLOR_NONE = "\033[0m";
	public static final String COLOR_BLACK = "\033[0;30m";
	public static final String COLOR_L_BLACK = "\033[1;30m";
	public static final String COLOR_RED = "\033[0;31m";
	public static final String COLOR_L_RED = "\033[1;31m";
	public static final String COLOR_GREEN = "\033[0;32m";
	public static final String COLOR_L_GREEN = "\033[1;32m";
	public static final String COLOR_BROWN = "\033[0;33m";
	public static final String COLOR_YELLOW = "\033[1;33m";
	public static final String COLOR_BLUE = "\033[0;34m";
	public static final String COLOR_L_BLUE = "\033[1;34m";
	public static final String COLOR_PURPLE = "\033[0;35m";
	public static final String COLOR_L_PURPLE = "\033[1;35m";
	public static final String COLOR_CYAN = "\033[0;36m";
	public static final String COLOR_L_CYAN = "\033[1;36m";
	public static final String COLOR_GRAY = "\033[0;37m";
	public static final String COLOR_WHITE = "\033[1;37m";
}
