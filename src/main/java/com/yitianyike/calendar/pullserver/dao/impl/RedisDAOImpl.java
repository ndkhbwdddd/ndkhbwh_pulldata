/**
 * 
 */
package com.yitianyike.calendar.pullserver.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

/**
 * @author xujinbo
 *
 */
@Component("redisDAO")
public class RedisDAOImpl implements RedisDAO {

	private static final ThreadLocal<RedisTemplate<String, Object>> contextHolder = new ThreadLocal<RedisTemplate<String, Object>>();

	@SuppressWarnings("unchecked")
	@Override
	public void setRedisTemplate(String templateString) {
		RedisDAOImpl.contextHolder
				.set((RedisTemplate<String, Object>) DataAccessFactory.getRedisCtxXml().getBean(templateString));
	}

	@Override
	public void clearRedisTemplate() {
		RedisDAOImpl.contextHolder.remove();
	}

	public RedisTemplate<String, Object> get() {
		return contextHolder.get();
	}

	public int hMsetValue(final String key, final Map<String, String> map, final String time) {
		Long ret = contextHolder.get().execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {

				byte[] keyb = key.getBytes();
				int seconds = Integer.parseInt(time);

				try {
					connection.multi();
					for (Entry<String, String> entry : map.entrySet()) {
						connection.hSet(keyb, entry.getKey().getBytes(), entry.getValue().getBytes());
					}
					if (seconds > 0) {
						connection.expire(keyb, seconds);
					}
					connection.exec();
				} catch (Exception e) {
					e.printStackTrace();
					return 0L;
				}

				return 1L;
			}
		});
		return ret.intValue();
	}

	@Override
	public void updateCardList(List<Map<String, Object>> cardDataList) {
		for (int i = 0; i < PropertiesUtil.redisCount - 1; i++) {
			setRedisTemplate("redisTemplate" + i);
			RedisTemplate<String, Object> redisTemplate = get();
			updateCards(cardDataList, redisTemplate);
			clearRedisTemplate();
		}
	}

	public int updateCards(final List<Map<String, Object>> cardDataList, RedisTemplate<String, Object> redisTemplate) {

		Long ret = redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {

				try {
					connection.multi();
					for (Map<String, Object> map : cardDataList) {
						byte[] savekey = map.get("savekey").toString().getBytes();
						String s = map.get("savekey").toString() + "-zset";
						@SuppressWarnings("unchecked")
						List<Map<String, Object>> keydata = (List<Map<String, Object>>) map.get("keydata");
						for (Map<String, Object> map2 : keydata) {
							String savefield = new String(map2.get("savefield").toString());
							String saveValue = new String(map2.get("saveValue").toString());
							connection.hSet(savekey, savefield.getBytes(), saveValue.getBytes());
							connection.zAdd(s.getBytes(), Double.parseDouble(savefield), savefield.getBytes());
						}
					}
					connection.exec();
				} catch (Exception e) {
					e.printStackTrace();
					return 0L;
				}
				return 1L;
			}

		});
		return ret.intValue();

	}

	@Override
	public void updateRedis(List<DataCache> list) {
		for (int i = 0; i < PropertiesUtil.redisCount - 1; i++) {
			setRedisTemplate("redisTemplate" + i);
			RedisTemplate<String, Object> redisTemplate = get();
			updateRedis(list, redisTemplate);
			clearRedisTemplate();
		}
	}

	@Override
	public void updateRedisNOFiled(List<DataCache> list) {
		for (int i = 0; i < PropertiesUtil.redisCount - 1; i++) {
			setRedisTemplate("redisTemplate" + i);
			RedisTemplate<String, Object> redisTemplate = get();
			updateRedisNOFiled(list, redisTemplate);
			clearRedisTemplate();
		}
	}

	public int updateRedisNOFiled(final List<DataCache> list, RedisTemplate<String, Object> redisTemplate) {
		Long ret = redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {

				try {
					connection.multi();
					for (DataCache dataCache : list) {
						connection.hSet(dataCache.getKey().getBytes(), dataCache.getField().getBytes(),
								dataCache.getValue().getBytes());
					}
					connection.exec();
				} catch (Exception e) {
					e.printStackTrace();
					return 0L;
				}
				return 1L;
			}

		});
		return ret.intValue();
	}

	public int keyExist(final String key) {
		Long ret = contextHolder.get().execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {

				byte[] keyb = key.getBytes();

				try {
					Boolean exist = connection.exists(keyb);
					if (exist) {
						return 1L;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				return 0L;
			}
		});
		return ret.intValue();
	}

	public int updateRedis(final List<DataCache> list, RedisTemplate<String, Object> redisTemplate) {
		Long ret = redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {

				try {
					connection.multi();
					for (DataCache dataCache : list) {
						connection.hSet(dataCache.getKey().getBytes(), dataCache.getField().getBytes(),
								dataCache.getValue().getBytes());
						connection.zAdd((dataCache.getKey() + "-zset").getBytes(),
								Double.parseDouble(dataCache.getField()), dataCache.getField().getBytes());
					}
					connection.exec();
				} catch (Exception e) {
					e.printStackTrace();
					return 0L;
				}
				return 1L;
			}

		});
		return ret.intValue();
	}

	@Override
	public void removeRedisByCacheKey(String cacheKey) {
		for (int i = 0; i < PropertiesUtil.redisCount - 1; i++) {
			setRedisTemplate("redisTemplate" + i);
			RedisTemplate<String, Object> redisTemplate = get();
			removeRedisByCacheKey(cacheKey, redisTemplate);
			clearRedisTemplate();
		}

	}

	private void removeRedisByCacheKey(final String cacheKey, RedisTemplate<String, Object> redisTemplate) {
		int keyExist = keyExist(cacheKey);
		if (keyExist != 0) {

			redisTemplate.execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {

					try {
						connection.multi();
						connection.del(cacheKey.getBytes());
						connection.exec();
					} catch (Exception e) {
						e.printStackTrace();
						return 0L;
					}
					return 1L;
				}
			});

		}

	}

	@Override
	public void updateSubscribedList(String cacheKey, List<Bcolumn> subscribedList) {
		for (int i = 0; i < PropertiesUtil.redisCount - 1; i++) {
			setRedisTemplate("redisTemplate" + i);
			RedisTemplate<String, Object> redisTemplate = get();
			updateRedis(cacheKey, subscribedList, redisTemplate);
			clearRedisTemplate();
		}
	}

	private int updateRedis(final String cacheKey, final List<Bcolumn> subscribedList,
			RedisTemplate<String, Object> redisTemplate) {

		Long ret = redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {

				try {
					connection.multi();
					for (Bcolumn subscribedInfo : subscribedList) {
						connection.hSet(cacheKey.getBytes(), (subscribedInfo.getTree_id() + "").getBytes(),
								subscribedInfo.getSave_value().getBytes());
					}
					connection.exec();
				} catch (Exception e) {
					e.printStackTrace();
					return 0L;
				}
				return 1L;
			}

		});
		return ret.intValue();

	}

}
