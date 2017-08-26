package com.yitianyike.calendar.pullserver.model.responsedata;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class Bcolumn {

	private int tree_id;
	private int data_type;
	private int skip_action;
	private String skip_url;
	private String name;
	private String icon;
	private String values;

	// 已经订阅列表
	private String save_value;

	public String getSave_value() {
		return save_value;
	}

	public void setSave_value(String save_value) {
		this.save_value = save_value;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {

		JSONObject parseObject = JSONObject.parseObject(values);

		this.name = parseObject.getString("name");
		this.icon = parseObject.getString("icon");
	}

	public int getTree_id() {
		return tree_id;
	}

	public void setTree_id(int tree_id) {
		this.tree_id = tree_id;
	}

	public int getData_type() {
		return data_type;
	}

	public void setData_type(int data_type) {
		this.data_type = data_type;
	}

	public int getSkip_action() {
		return skip_action;
	}

	public void setSkip_action(int skip_action) {
		this.skip_action = skip_action;
	}

	public String getSkip_url() {
		return skip_url;
	}

	public void setSkip_url(String skip_url) {
		this.skip_url = skip_url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setSaveValue() {
		Map<String, Object> saveMap = new HashMap<String, Object>();
		Map<String, Object> preferenceMap = new HashMap<String, Object>();
		Map<String, Object> actionMap = new HashMap<String, Object>();
		preferenceMap.put("icon", this.icon);
		preferenceMap.put("name", this.name);
		saveMap.put("preference", preferenceMap);
		actionMap.put("skip_action", this.skip_action);
		actionMap.put("skip_url", this.skip_url);
		saveMap.put("list_action", actionMap);
		saveMap.put("aid", this.tree_id);
		this.save_value = JSONObject.toJSONString(saveMap);
	}

}
