package com.yitianyike.calendar.pullserver.bo;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.response.SubscribedInfo;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;
import com.yitianyike.calendar.pullserver.model.responsedata.Tabs;

public interface DataCacheBO {

	void insertSubscribedList(String cacheKey, List<Bcolumn> subscribedList);

	void insertRecommendSubscribeList(String cacheKey, String recommendSubscribeListJson);

	/**
	 * 批量插入
	 * @param cacheKey 
	 * @param list
	 */
	//public int[] insertDataCacheList(String cacheKey, List<DataCache> list);

	void insertCardList(List<Map<String, Object>> organizeCardData, Map<String, String> paramMap);

	//public int[] insertDataCacheListNOFiled(String cacheKey, List<DataCache> list);

	/**
	 * 插入缓存tabs
	 * @param string
	 * @param tabs
	 */
	void insertTabsList(String string, String tabs);
}
