package com.yitianyike.calendar.pullserver.bo;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.DataCache;

public interface LogBO {

	public List<String> getChannels();

	public void writLogs(String channel, String key, String value);

}
