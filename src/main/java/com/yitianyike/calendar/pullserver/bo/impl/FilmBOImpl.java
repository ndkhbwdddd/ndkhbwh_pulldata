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

import com.yitianyike.calendar.pullserver.bo.FilmBO;
import com.yitianyike.calendar.pullserver.bo.LotteryBO;
import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.Film;
import com.yitianyike.calendar.pullserver.model.responseCardData.Lottery;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.DateUtil;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("filmBO")
public class FilmBOImpl implements FilmBO {

	@Autowired
	private AlmanacDAO almanacDAO;
	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public List<DataCache> pressInFilm(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");
		List<DataCache> saveCacheList = new ArrayList<DataCache>();
		// 获取这个渠道下所有电影的订阅项
		List<ChanneRelevance> channeRelevances = cardDataDao.getDataTypeForFilm(channel_code);

		for (ChanneRelevance channeRelevance : channeRelevances) {
			String tree_name = channeRelevance.getTree_name();
			// 前10后20天,刷入今天数据
			List<Film> films = cardDataDao.pressInFilm(channeRelevance.getAlias_name(),
					channeRelevance.getShow_number());
			if (!films.isEmpty()) {
				Map<String, Object> savaJsonMap = new HashMap<String, Object>();
				List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();

				int data_style = channeRelevance.getData_style();
				for (Film film : films) {
					if (31 == data_style) {// c1,c2,c3,p1,u1
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("p1", film.getBanner());
						savaListMap.put("c1", "电影");
						savaListMap.put("c2", film.getTv_title());
						savaListMap.put("c3", film.getStory_brief().length() <= 10 ? film.getStory_brief()
								: film.getStory_brief().substring(0, 9));
						savaListMap.put("u1", film.getLink_url());
						saveList.add(savaListMap);
					} else if (36 == data_style) {// c1,c2,p1,u1
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("p1", film.getBanner());
						savaListMap.put("c1", film.getTv_title());
						savaListMap.put("c2", film.getStory_brief().length() <= 10 ? film.getStory_brief()
								: film.getStory_brief().substring(0, 9));
						savaListMap.put("u1", film.getLink_url());
						saveList.add(savaListMap);
					} else if (38 == data_style) {// c1,p1,u1
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("p1", film.getBanner());
						savaListMap.put("c1", film.getTv_title());
						savaListMap.put("u1", film.getLink_url());
						saveList.add(savaListMap);
					} else if (7 == data_style) {// c1,c2,u1
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("c1", film.getTv_title());
						savaListMap.put("c2", film.getStar());
						savaListMap.put("u1", film.getLink_url());
						saveList.add(savaListMap);
					} else if (33 == data_style) {// c1,c2,p1,u1
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("c1", film.getPlay_date());
						savaListMap.put("c2", film.getTv_title());
						savaListMap.put("p1", film.getIcon_address());
						savaListMap.put("u1", film.getLink_url());
						saveList.add(savaListMap);
					} else if (34 == data_style) {// c1,c2,c3,p1,u1
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("c1", film.getTv_title());
						savaListMap.put("c2", "演员:" + film.getStar());
						savaListMap.put("c3", film.getPlay_date());
						savaListMap.put("p1", film.getIcon_address());
						savaListMap.put("u1", film.getLink_url());
						saveList.add(savaListMap);
					} else if (35 == data_style) {// c1,c2,c3,c4,c5,p1,u1
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("c1", film.getTv_title());
						savaListMap.put("c2", "演员:" + film.getStar());
						savaListMap.put("c3", "类型:" + film.getType());
						savaListMap.put("c4", "地区:中国");
						savaListMap.put("c5", "上映时间:" + film.getPlay_date());
						savaListMap.put("p1", film.getIcon_address());
						savaListMap.put("u1", film.getLink_url());
						saveList.add(savaListMap);
					}
				}

				savaJsonMap.put("unique_type", 1);
				savaJsonMap.put("title", tree_name);
				savaJsonMap.put("datas", saveList);
				String jsonString = JSONObject.toJSONString(savaJsonMap);
				List<String> dates = DateUtil.findStringDates();
				for (String date : dates) {

					DataCache dc = new DataCache();
					StringBuffer sb = new StringBuffer();
					sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
							.append(channeRelevance.getTree_id());
					dc.setKey(sb.toString());
					dc.setField(date.replace("-", ""));
					dc.setValue(jsonString);
					saveCacheList.add(dc);
				}

			}

		}

		int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(saveCacheList);
		redisDAO.updateRedis(saveCacheList);

		return saveCacheList;
	}

}
