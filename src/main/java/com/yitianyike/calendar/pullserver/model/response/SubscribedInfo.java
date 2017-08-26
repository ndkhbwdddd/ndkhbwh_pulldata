package com.yitianyike.calendar.pullserver.model.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class SubscribedInfo {

	private int id;
	private int subed_column_style;
	private String column_name;
	private String icon;
	private String channel_code;
	private String version;
	private String cachekey;
	private String savaJson;

	public String getSavaJson() {
		return savaJson;
	}

	public void setSavaJson(int id, String column_name, int subed_column_style, String icon, int little_type, int type,
			int skip_action, String h5_url) {
		Map<String, Object> toJsonMap = new HashMap<String, Object>();
		Map<String, Object> toJsonPreference = new HashMap<String, Object>();
		Map<String, Object> toJsonAction = new HashMap<String, Object>();
		toJsonPreference.put("icon", icon);
		toJsonPreference.put("name", column_name);
		toJsonMap.put("preference", toJsonPreference);
		toJsonMap.put("param", new ArrayList<Integer>());
		toJsonMap.put("data", new ArrayList<Integer>());
		toJsonAction.put("list_skip_action", skip_action);
		toJsonAction.put("list_skip_url", h5_url);
		toJsonMap.put("list_action", toJsonAction);
		toJsonMap.put("dataStyle", subed_column_style);
		toJsonMap.put("aid", id);
		toJsonMap.put("type", type);
		toJsonMap.put("little_type", little_type);
		this.savaJson = JSONObject.fromObject(toJsonMap).toString();
	}

	public String getCachekey() {
		return cachekey;
	}

	public void setCachekey(String cachekey) {
		this.cachekey = cachekey;
	}

	public String getChannel_code() {
		return channel_code;
	}

	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSubed_column_style() {
		return subed_column_style;
	}

	public void setSubed_column_style(int subed_column_style) {
		this.subed_column_style = subed_column_style;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
