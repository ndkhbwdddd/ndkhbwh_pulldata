package com.yitianyike.calendar.pullserver.model;

/**
 * 栏目实体
 * 
 * @author shiminghang
 * 
 */
public class Column {
	private Integer id;
	private Integer isdefault;
	private Integer packageId;
	private Integer imageId;
	private String columnName;
	private Long startTime;
	private Long endTime;
	private Integer columnStyle;
	private String columnDescInfo;
	private Integer status;
	private Integer columnOrderNo;
	private String h5Url;
	private Integer skipAction;
	private Integer subedColumnStyle;
	private Integer subedTopAction;
	private Integer anchorId;
	private String createName;
	private Long createTime;
	private String updateName;
	private Long updateTime;

	private String icon;
	private Integer type;
	private Integer littleType;
	
	
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getLittleType() {
		return littleType;
	}

	public void setLittleType(Integer littleType) {
		this.littleType = littleType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
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

	public Integer getColumnStyle() {
		return columnStyle;
	}

	public void setColumnStyle(Integer columnStyle) {
		this.columnStyle = columnStyle;
	}

	public String getColumnDescInfo() {
		return columnDescInfo;
	}

	public void setColumnDescInfo(String columnDescInfo) {
		this.columnDescInfo = columnDescInfo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getColumnOrderNo() {
		return columnOrderNo;
	}

	public void setColumnOrderNo(Integer columnOrderNo) {
		this.columnOrderNo = columnOrderNo;
	}

	public String getH5Url() {
		return h5Url;
	}

	public void setH5Url(String h5Url) {
		this.h5Url = h5Url;
	}

	public Integer getSkipAction() {
		return skipAction;
	}

	public void setSkipAction(Integer skipAction) {
		this.skipAction = skipAction;
	}

	public Integer getSubedColumnStyle() {
		return subedColumnStyle;
	}

	public void setSubedColumnStyle(Integer subedColumnStyle) {
		this.subedColumnStyle = subedColumnStyle;
	}

	public Integer getSubedTopAction() {
		return subedTopAction;
	}

	public void setSubedTopAction(Integer subedTopAction) {
		this.subedTopAction = subedTopAction;
	}

	public Integer getAnchorId() {
		return anchorId;
	}

	public void setAnchorId(Integer anchorId) {
		this.anchorId = anchorId;
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
