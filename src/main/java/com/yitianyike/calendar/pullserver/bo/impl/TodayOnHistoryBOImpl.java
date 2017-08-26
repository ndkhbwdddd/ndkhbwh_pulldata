package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("todayOnHistoryBO")
public class TodayOnHistoryBOImpl implements TodayOnHistoryBO {

	@Autowired
	private AlmanacDAO almanacDAO;
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

		if (channeRelevance != null || !ths.isEmpty()) {

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
				responseData.put("c1", todayOnHistory.getDes());// des
				responseData.put("p1", todayOnHistory.getPic());// pic
				responseData.put("t1", todayOnHistory.getTitle());//title
				responseData.put("skip_url", todayOnHistory.getSkip_url());

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

}
