package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.yitianyike.calendar.pullserver.bo.DataCacheBO;
import com.yitianyike.calendar.pullserver.bo.SubscribedListBO;
import com.yitianyike.calendar.pullserver.bo.TabsListBO;
import com.yitianyike.calendar.pullserver.dao.ColumnDAO;
import com.yitianyike.calendar.pullserver.model.responsedata.Tabs;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;

import net.sf.json.JSONObject;

@Component("tabsListBO")
public class TabsListBOImpl implements TabsListBO {
	@Autowired
	private ColumnDAO columnDAO;
	@Autowired
	private DataCacheBO dataCacheBO;// = (DataCacheBO)
									// DataAccessFactory.dataHolder().get("dataCacheBO");

	@Override
	public String tabsList(Map<String, String> parmMap) {
		List<Tabs> tabsList = columnDAO.getTabsList(parmMap);
		List<Map<String, Object>> responseTabsList = new ArrayList<Map<String, Object>>();
		for (Tabs tab : tabsList) {
			Map<String, Object> responseTab = new HashMap<String, Object>();
			responseTab.put("type", tab.getTab_type());
			responseTab.put("tab_name", tab.getTab_name());
			responseTab.put("skip_url", StringUtils.isBlank(tab.getH5_url()) ? "" : tab.getH5_url());
			responseTabsList.add(responseTab);
		}

		String tabs = JSONArray.toJSONString(responseTabsList);
		StringBuilder deleteSbParam = new StringBuilder();
		deleteSbParam.append(parmMap.get("channel_code")).append("-").append(parmMap.get("version")).append("-tabs");
		dataCacheBO.insertTabsList(deleteSbParam.toString(), tabs);
		return tabs;
	}

}
