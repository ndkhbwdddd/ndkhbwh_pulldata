package com.yitianyike.calendar.pullserver.bo;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;


public interface SubscribedListBO {
	
	public List<Bcolumn>  subscribedList(Map<String, String> parmMap);
	
}
