package com.yitianyike.calendar.pullserver.model.responseCardData;

public class ChanneRelevance {

	private String channel_code;
	private int tree_id;
	private String alias_name;
	private int data_type;
	private int show_number;
	private int data_style;
	private String tree_name;

	public String getTree_name() {
		return tree_name;
	}

	public void setTree_name(String tree_name) {
		this.tree_name = tree_name;
	}

	public int getData_style() {
		return data_style;
	}

	public void setData_style(int data_style) {
		this.data_style = data_style;
	}

	public int getShow_number() {
		return show_number;
	}

	public void setShow_number(int show_number) {
		this.show_number = show_number;
	}

	public int getData_type() {
		return data_type;
	}

	public void setData_type(int data_type) {
		this.data_type = data_type;
	}

	public String getChannel_code() {
		return channel_code;
	}

	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}

	public int getTree_id() {
		return tree_id;
	}

	public void setTree_id(int tree_id) {
		this.tree_id = tree_id;
	}

	public String getAlias_name() {
		return alias_name;
	}

	public void setAlias_name(String alias_name) {
		this.alias_name = alias_name;
	}

}
