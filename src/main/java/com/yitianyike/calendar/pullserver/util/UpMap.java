package com.yitianyike.calendar.pullserver.util;


import java.util.HashMap;
import java.util.Map;

public class UpMap {

	public UpMap() {
		super();
	}

	private String public_base_url = "http://oio60a1hv.bkt.clouddn.com/";// 公开库，缩略图

	private String private_base_url = "http://ogzgftayt.bkt.clouddn.com/";// 原始图,私有库

	private String public_bucket_name = "lock-cms-v2-public";// 缩略图存放空间

	private String private_bucket_name = "lock-cms-v2";// 原始图、多分辨率图存放的图片

	private String queue_name = "lock-cms-v2-queue";// 处理队列

	private String persistentOpfs;

	private Map<String, Boolean> keysMap = new HashMap<String, Boolean>();

	private Map<String, String> sourceKeyMap = new HashMap<String, String>();

	public Map<String, String> getSourceKeyMap() {
		return sourceKeyMap;
	}

	public void setSourceKeyMap(Map<String, String> sourceKeyMap) {
		this.sourceKeyMap = sourceKeyMap;
	}

	public String getPersistentOpfs() {
		return persistentOpfs;
	}

	public void setPersistentOpfs(String persistentOpfs) {
		this.persistentOpfs = persistentOpfs;
	}

	public Map<String, Boolean> getKeysMap() {
		return keysMap;
	}

	public void setKeysMap(Map<String, Boolean> keysMap) {
		this.keysMap = keysMap;
	}

	public String getPublic_base_url() {
		return public_base_url;
	}

	public void setPublic_base_url(String public_base_url) {
		this.public_base_url = public_base_url;
	}

	public String getPrivate_base_url() {
		return private_base_url;
	}

	public void setPrivate_base_url(String private_base_url) {
		this.private_base_url = private_base_url;
	}

	public String getPublic_bucket_name() {
		return public_bucket_name;
	}

	public void setPublic_bucket_name(String public_bucket_name) {
		this.public_bucket_name = public_bucket_name;
	}

	public String getPrivate_bucket_name() {
		return private_bucket_name;
	}

	public void setPrivate_bucket_name(String private_bucket_name) {
		this.private_bucket_name = private_bucket_name;
	}

	public String getQueue_name() {
		return queue_name;
	}

	public void setQueue_name(String queue_name) {
		this.queue_name = queue_name;
	}

}
