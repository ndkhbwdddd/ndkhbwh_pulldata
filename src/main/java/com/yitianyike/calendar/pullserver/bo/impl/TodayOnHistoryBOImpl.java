package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.ListUtil;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("todayOnHistoryBO")
public class TodayOnHistoryBOImpl implements TodayOnHistoryBO {
	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public List<DataCache> pressInTodayOnHistory(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");
		List<TodayOnHistory> ths = cardDataDao.pressInTodayOnHistory();

		List<DataCache> list = new ArrayList<DataCache>();
		ChanneRelevance channeRelevance = cardDataDao.getDataType("历史上的今天", channel_code);

		if (channeRelevance != null && !ths.isEmpty()) {

			for (TodayOnHistory todayOnHistory : ths) {
				StringBuffer sb = new StringBuffer();
				sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
						.append(channeRelevance.getTree_id());

				DataCache dc = new DataCache();
				//
				String saveKey = sb.toString();
				dc.setKey(saveKey);
				Calendar calendar = Calendar.getInstance();
				String curYear = Integer.toString(calendar.get(Calendar.YEAR));
				//
				String month = todayOnHistory.getMonth();
				String day = todayOnHistory.getDay();

				dc.setField(curYear + month + day);

				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("data_type", channeRelevance.getData_type());
				// responseData.put("day", todayOnHistory.getDay());
				// responseData.put("month", todayOnHistory.getMonth());
				// responseData.put("year", todayOnHistory.getYear());
				responseData.put("c2", todayOnHistory.getDes());// des
				responseData.put("p1", todayOnHistory.getPic());// pic
				responseData.put("c1", todayOnHistory.getTitle());// title
				responseData.put("u1", todayOnHistory.getSkip_url());
				responseData.put("unique_type", 0);
				responseData.put("more", 1);
				responseData.put("sub_aid", channeRelevance.getTree_id());
				//
				dc.setValue(JSONObject.toJSONString(responseData));
				list.add(dc);

			}

			int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(list);
			redisDAO.updateRedis(list);
		}
		// 按别名取数据

		return list;
	}

	@Override
	public List<DataCache> pressInMoreTodayOnHistory(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");
		// 打包条数
		String num = parmMap.get("num");
		// 获取所有历史上的今天数据
		List<TodayOnHistory> ths = cardDataDao.pressInMoreTodayOnHistory();

		List<DataCache> list = new ArrayList<DataCache>();
		ChanneRelevance channeRelevance = cardDataDao.getDataType("历史上的今天", channel_code);

		if (channeRelevance != null && !ths.isEmpty()) {
			int tree_id = channeRelevance.getTree_id();
			String tree_name = channeRelevance.getTree_name();
			int data_type = channeRelevance.getData_type();
			// 先给1万条数据分个组,按月日分组
			Map<String, List<TodayOnHistory>> groupMap = new HashMap<String, List<TodayOnHistory>>();
			for (TodayOnHistory todayOnHistory : ths) {
				String month = todayOnHistory.getMonth();
				String day = todayOnHistory.getDay();
				StringBuffer sb = new StringBuffer();
				sb.append(month).append(day);
				String groupKey = sb.toString();
				List<TodayOnHistory> mdList = groupMap.get(groupKey);
				if (mdList == null) {
					List<TodayOnHistory> newmdList = new ArrayList<TodayOnHistory>();
					newmdList.add(todayOnHistory);
					groupMap.put(groupKey, newmdList);
				} else {
					mdList.add(todayOnHistory);
				}
			}

			// 遍历分组进行分页
			Set<String> keySet = groupMap.keySet();
			Calendar calendar = Calendar.getInstance();
			String curYear = Integer.toString(calendar.get(Calendar.YEAR));
			for (String gropkey : keySet) {
				StringBuffer sb = new StringBuffer();
				sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-").append(tree_id)
						.append("-").append(curYear).append(gropkey);
				String saveKey = sb.toString();

				List<TodayOnHistory> monthDayTHs = groupMap.get(gropkey);

				// 分页
				List<List<TodayOnHistory>> groupTHListByNum = ListUtil.groupListByNum(monthDayTHs,
						Integer.parseInt(num));
				int save_field = 1;
				for (List<TodayOnHistory> todayOnHistorys : groupTHListByNum) {
					DataCache dc = new DataCache();
					Map<String, Object> saveMap = new HashMap<String, Object>();
					List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();

					for (TodayOnHistory todayOnHistory : todayOnHistorys) {
						Map<String, Object> responseData = new HashMap<String, Object>();
						responseData.put("data_type", data_type);
						responseData.put("c2", todayOnHistory.getDes());// des
						responseData.put("p1", todayOnHistory.getPic());// pic
						responseData.put("c1", todayOnHistory.getTitle());// title
						responseData.put("u1", todayOnHistory.getSkip_url());
						responseData.put("sub_aid", tree_id);
						saveList.add(responseData);
					}
					saveMap.put("name", tree_name);
					saveMap.put("datas", saveList);
					String saveValue = JSONObject.toJSONString(saveMap);
					dc.setKey(saveKey);
					dc.setField(Integer.toString(save_field));
					dc.setValue(saveValue);
					list.add(dc);
					save_field++;
				}
			}

			int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(list);
			redisDAO.updateRedis(list);
		}
		// 按别名取数据

		return list;
	}

}
