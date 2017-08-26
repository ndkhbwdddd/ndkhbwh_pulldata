package com.yitianyike.calendar.pullserver.model.responseCardData;

public class ChanneRelevance {

	private String channel_code;
	private int tree_id;
	private String alias_name;
	private int data_type;

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
