package com.yitianyike.calendar.pullserver.bo;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.DataCache;

public interface ControlDriveBO {

	public List<DataCache> pressInControlDrive(Map<String, String> parmMap);
}
