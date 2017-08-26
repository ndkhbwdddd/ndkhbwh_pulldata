package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.SubscribedListBO;
import com.yitianyike.calendar.pullserver.dao.ColumnDAO;
import com.yitianyike.calendar.pullserver.model.response.SubscribedInfo;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;

@Component("subscribedListBO")
public class SubscribedListBOImpl implements SubscribedListBO {
	@Autowired
	private ColumnDAO columnDAO;

	@Override
	public List<Bcolumn> subscribedList(Map<String, String> parmMap) {
		
		return  columnDAO.subscribedList(parmMap);
	}
}
