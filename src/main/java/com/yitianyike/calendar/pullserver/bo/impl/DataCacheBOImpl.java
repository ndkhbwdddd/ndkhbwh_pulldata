package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.DataCacheBO;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.response.SubscribedInfo;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;
import com.yitianyike.calendar.pullserver.model.responsedata.Tabs;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("dataCacheBO")
public class DataCacheBOImpl implements DataCacheBO {

	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public void insertSubscribedList(String cacheKey,List<Bcolumn> subscribedList) {
		dataCacheDAO.insertSubscribedList(cacheKey, subscribedList);
		// redisDAO.removeRedisByCacheKey(cacheKey);
		redisDAO.updateSubscribedList(cacheKey, subscribedList);
	}

	@Override
	public void insertRecommendSubscribeList(String cacheKey, String recommendSubscribeListJson) {
		dataCacheDAO.insertRecommendSubscribeList(cacheKey, recommendSubscribeListJson);
		redisDAO.removeRedisByCacheKey(cacheKey);
	}

	// private void removeRedisByCacheKey(final String cacheKey) {
	// for (int i = 0; i < PropertiesUtil.redisCount - 1; i++) {
	// redisDAO.setRedisTemplate("redisTemplate" + i);
	// RedisTemplate<String, Object> redisTemplate = redisDAO.get();
	// if(redisTemplate.hasKey(cacheKey)){
	// redisTemplate.delete(cacheKey);
	// }
	// }
	// }

	@Override
	public void insertCardList(List<Map<String, Object>> cardDataList, Map<String, String> paramMap) {
		dataCacheDAO.insertCardList(cardDataList, paramMap);
		updateCardList(cardDataList);
	}

	private void updateCardList(List<Map<String, Object>> cardDataList) {

		redisDAO.updateCardList(cardDataList);
	}

//	@Override
//	public int[] insertDataCacheList(String cacheKey, List<DataCache> list) {
//		int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(cacheKey, list);
//		redisDAO.updateRedis(list);
//		return insertDataCacheList;
//	}
//	@Override
//	public int[] insertDataCacheListNOFiled(String cacheKey, List<DataCache> list) {
//		int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(cacheKey, list);
//		redisDAO.updateRedisNOFiled(list);
//		return insertDataCacheList;
//	}

	@Override
	public void insertTabsList(String cacheKey, String tabs) {
		dataCacheDAO.insertTabsList(cacheKey, tabs);
		redisDAO.removeRedisByCacheKey(cacheKey);
	}

}
