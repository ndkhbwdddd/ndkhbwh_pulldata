package com.yitianyike.calendar.pullserver.bo;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.response.SubscribedInfo;
import com.yitianyike.calendar.pullserver.model.responsedata.Tabs;

public interface TabsListBO {
	
	public String tabsList(Map<String, String> parmMap);
	
}
