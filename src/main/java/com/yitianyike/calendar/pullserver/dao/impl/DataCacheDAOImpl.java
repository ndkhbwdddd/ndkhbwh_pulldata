package com.yitianyike.calendar.pullserver.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.response.SubscribedInfo;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;

@Component("dataCacheDAO")
public class DataCacheDAOImpl extends BaseDAO implements DataCacheDAO {

	@Override
	public void insertSubscribedList(String cacheKey, List<Bcolumn> subscribedList) {
		deleteDBCacheByCacheKey(cacheKey);
		StringBuilder sb = new StringBuilder();
		sb.append("insert into data_cache(cache_key,field,cache_value,create_time) values(?,?,?,?)");
		List<Object[]> batchList = new ArrayList<Object[]>();
		for (Bcolumn subscribedInfo : subscribedList) {
			Object[] objs = new Object[] { cacheKey, subscribedInfo.getTree_id(), subscribedInfo.getSave_value(),
					new Date().getTime() };
			batchList.add(objs);
		}
		this.getJdbcTemplate().batchUpdate(sb.toString(), batchList);
	}

	@Override
	public void insertRecommendSubscribeList(String cacheKey, String recommendSubscribeListJson) {
		deleteDBCacheByCacheKey(cacheKey);
		StringBuilder sb = new StringBuilder();
		sb.append("insert into data_cache(cache_key,field,cache_value,create_time) values(?,?,?,?)");
		Object[] objs = new Object[] { cacheKey, cacheKey, recommendSubscribeListJson, new Date().getTime() };
		this.getJdbcTemplate().update(sb.toString(), objs);
	}

	private void deleteDBCacheByCacheKey(String cacheKey) {
		StringBuilder deleteSbSql = new StringBuilder();
		deleteSbSql.append("DELETE FROM `data_cache`  WHERE cache_key=:cache_key");
		Map<String, Object> deleteMap = new HashMap<String, Object>();
		deleteMap.put("cache_key", cacheKey);
		this.getNamedParameterJdbcTemplate().update(deleteSbSql.toString(), deleteMap);
	}

	@Override
	public int[] insertDataCacheList(List<DataCache> list) {
		String sql = "insert into data_cache(cache_key,field,cache_value,create_time) values(?,?,?,?)";
		List<Object[]> batchList = new ArrayList<Object[]>();
		for (DataCache dataCache : list) {
			deleteDBCacheByCacheKeyAndField(dataCache.getKey(), dataCache.getField());
			Object[] objs = new Object[] { dataCache.getKey(), dataCache.getField(), dataCache.getValue(),
					new Date().getTime() };
			batchList.add(objs);
		}
		int[] batchUpdate = this.getJdbcTemplate().batchUpdate(sql, batchList);
		return batchUpdate;
	}

	public void insertCardList(List<Map<String, Object>> cardDataList, Map<String, String> paramMap) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into data_cache(cache_key,field,cache_value,create_time) values(?,?,?,?)");
		List<Object[]> batchList = new ArrayList<Object[]>();

		for (Map<String, Object> map : cardDataList) {
			String savekey = map.get("savekey").toString();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> keydata = (List<Map<String, Object>>) map.get("keydata");
			if (paramMap.containsKey("flush_style")) {
				for (Map<String, Object> map2 : keydata) {
					String savefield = map2.get("savefield").toString();
					deleteDBCacheByCacheKeyAndField(savekey, savefield);
				}
			} else {
				deleteDBCacheByCacheKey(savekey);
			}
			for (Map<String, Object> map2 : keydata) {
				String savefield = map2.get("savefield").toString();
				String saveValue = map2.get("saveValue").toString();
				Object[] objs = new Object[] { savekey, savefield, saveValue, new Date().getTime() };
				batchList.add(objs);
			}
		}
		this.getJdbcTemplate().batchUpdate(sb.toString(), batchList);
	}

	private void deleteDBCacheByCacheKeyAndField(String savekey, String savefield) {
		StringBuilder deleteSbSql = new StringBuilder();
		deleteSbSql.append("DELETE FROM `data_cache`  WHERE cache_key=:cache_key and field=:savefield");
		Map<String, Object> deleteMap = new HashMap<String, Object>();
		deleteMap.put("cache_key", savekey);
		deleteMap.put("savefield", savefield);
		this.getNamedParameterJdbcTemplate().update(deleteSbSql.toString(), deleteMap);

	}

	@Override
	public void insertTabsList(String cacheKey, String tabs) {
		deleteDBCacheByCacheKey(cacheKey);
		StringBuilder sb = new StringBuilder();
		sb.append("insert into data_cache(cache_key,field,cache_value,create_time) values(?,?,?,?)");
		Object[] objs = new Object[] { cacheKey, cacheKey, tabs, new Date().getTime() };
		this.getJdbcTemplate().update(sb.toString(), objs);
	}

	@Override
	public int[] insertDataCacheList(String cacheKey, List<DataCache> list) {
		// TODO Auto-generated method stub
		return null;
	}

}
