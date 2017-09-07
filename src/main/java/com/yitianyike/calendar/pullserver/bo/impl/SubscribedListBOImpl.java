package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.DataCacheBO;
import com.yitianyike.calendar.pullserver.bo.SubscribedListBO;
import com.yitianyike.calendar.pullserver.dao.ColumnDAO;
import com.yitianyike.calendar.pullserver.model.response.SubscribedInfo;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;

@Component("subscribedListBO")
public class SubscribedListBOImpl implements SubscribedListBO {
	@Autowired
	private ColumnDAO columnDAO;
	//private DataCacheBO dataCacheBO = (DataCacheBO) DataAccessFactory.dataHolder().get("dataCacheBO");
	@Autowired
	private DataCacheBO dataCacheBO;
	@Override
	public List<Bcolumn> subscribedList(Map<String, String> parmMap) {

		List<Bcolumn> subscribedList = columnDAO.subscribedList(parmMap);

		StringBuilder deleteSbParam = new StringBuilder();
		deleteSbParam.append(parmMap.get("channel_code")).append("-").append(parmMap.get("version"))
				.append("-subscribed");
		dataCacheBO.insertSubscribedList(deleteSbParam.toString(), subscribedList);
		return subscribedList;

	}
}
