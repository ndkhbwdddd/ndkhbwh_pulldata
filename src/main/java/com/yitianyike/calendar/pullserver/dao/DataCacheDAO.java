package com.yitianyike.calendar.pullserver.dao;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.response.SubscribedInfo;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;

public interface DataCacheDAO {

	void insertSubscribedList(String cacheKey, List<Bcolumn> subscribedList);

	void insertRecommendSubscribeList(String cacheKey, String recommendSubscribeListJson);

	/**
	 * 批量插入
	 * 
	 * @param cacheKey
	 * @param list
	 */
	public int[] insertDataCacheList(List<DataCache> list);

	void insertCardList(List<Map<String, Object>> cardDataList, Map<String, String> paramMap);
	
	/**
	 * 插入tabs
	 * @param cacheKey
	 * @param tabs
	 */
	void insertTabsList(String cacheKey, String tabs);

	int[] insertDataCacheList(String cacheKey, List<DataCache> list);

	//删除某个aid缓存
	void deleteDataCache(String string);

}
