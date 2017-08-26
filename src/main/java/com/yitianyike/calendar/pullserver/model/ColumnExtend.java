package com.yitianyike.calendar.pullserver.model;

/**
 * 栏目扩展
 * 
 * @author shiminghang
 * 
 */
public class ColumnExtend {

	private Integer id;
	private Integer columnId;
	private Integer anchorId;
	private Long startTime;
	private Long endTime;
	private String createName;
	private Long createTime;
	private String updateName;
	private Long updateTime;
	
	private Integer cardStyle;
	private String quoteName;
	private Integer littleType;
	private Integer type;
	private String frequence;
	private Long expDateTime;

	
	
	public String getQuoteName() {
		return quoteName;
	}

	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}

	public Integer getLittleType() {
		return littleType;
	}

	public void setLittleType(Integer littleType) {
		this.littleType = littleType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFrequence() {
		return frequence;
	}

	public void setFrequence(String frequence) {
		this.frequence = frequence;
	}

	public Long getExpDateTime() {
		return expDateTime;
	}

	public void setExpDateTime(Long expDateTime) {
		this.expDateTime = expDateTime;
	}

	public Integer getCardStyle() {
		return cardStyle;
	}

	public void setCardStyle(Integer cardStyle) {
		this.cardStyle = cardStyle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	public Integer getAnchorId() {
		return anchorId;
	}

	public void setAnchorId(Integer anchorId) {
		this.anchorId = anchorId;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	
}
