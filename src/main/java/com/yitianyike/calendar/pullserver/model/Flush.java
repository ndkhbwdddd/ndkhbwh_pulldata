package com.yitianyike.calendar.pullserver.model;

public class Flush {
	private	Long	id              ;
	private	Integer	anchorId           ;
	private	String	channelCode    ;
	private	String	version         ;
	private	String	frequence       ;
	private	Long	updateTime     ;
	private	Long	createTime     ;
	
	private Integer type;
	private Integer column_id;
	
	
	
	public Integer getColumn_id() {
		return column_id;
	}
	public void setColumn_id(Integer column_id) {
		this.column_id = column_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getAnchorId() {
		return anchorId;
	}
	public void setAnchorId(Integer anchorId) {
		this.anchorId = anchorId;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFrequence() {
		return frequence;
	}
	public void setFrequence(String frequence) {
		this.frequence = frequence;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	
}
