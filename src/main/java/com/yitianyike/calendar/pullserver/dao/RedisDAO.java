/**
 * 
 */
package com.yitianyike.calendar.pullserver.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.response.SubscribedInfo;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;

/**
 * @author xujinbo
 *
 */
public interface RedisDAO {
	void setRedisTemplate(String templateString);

	void clearRedisTemplate();

	RedisTemplate<String, Object> get();

	void updateCardList(List<Map<String, Object>> cardDataList);

	public void updateRedis(List<DataCache> list);

	void removeRedisByCacheKey(String cacheKey);

	void updateSubscribedList(String cacheKey, List<Bcolumn> subscribedList);
	
	void updateRedisNOFiled(List<DataCache> list);

	void deleteRedis(String saveKey);
}
