package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.NewsBO;
import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.News;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.DateUtil;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("newsBO")
public class NewsBOImpl implements NewsBO {

	@Autowired
	private AlmanacDAO almanacDAO;
	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public List<DataCache> pressInNews(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");
		List<ChanneRelevance> channeRelevances = cardDataDao.getDataType(channel_code);
		List<DataCache> saveCacheList = new ArrayList<DataCache>();
		for (ChanneRelevance channeRelevance : channeRelevances) {

			String alias_name = channeRelevance.getAlias_name();
			int data_style = channeRelevance.getData_type();
			int show_number = channeRelevance.getShow_number();
			String tree_name = channeRelevance.getTree_name();
			// int tree_id = channeRelevance.getTree_id();
			if (data_style == 1 || data_style == 2 || data_style == 26) {// p1,c1,c2,u1(icon)

				List<News> newss = cardDataDao.pressInNews(alias_name, show_number);
				if (!newss.isEmpty()) {
					Map<String, Object> savaJsonMap = new HashMap<String, Object>();
					List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
					for (News news : newss) {
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("p1", news.getImg_url());
						savaListMap.put("c1", news.getTitle());
						savaListMap.put("c2", news.getInstro());
						savaListMap.put("u1", news.getSource_url());
						saveList.add(savaListMap);
					}
					savaJsonMap.put("unique_type", 1);
					savaJsonMap.put("title", tree_name);
					savaJsonMap.put("datas", saveList);

					DataCache dc = new DataCache();
					StringBuffer sb = new StringBuffer();
					sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
							.append(channeRelevance.getTree_id());
					dc.setKey(sb.toString());
					dc.setField(DateUtil.getCurrentDate());
					dc.setValue(JSONObject.toJSONString(savaJsonMap));
					saveCacheList.add(dc);
				}

			} else if (data_style == 3 || data_style == 25) {// c1,p1,p2,p3,u1

				List<News> newss = cardDataDao.pressInNews(alias_name, show_number);
				if (!newss.isEmpty()) {
					Map<String, Object> savaJsonMap = new HashMap<String, Object>();
					List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
					for (News news : newss) {
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("c1", news.getTitle());
						savaListMap.put("u1", news.getSource_url());
						String pic_list = news.getPic_list();
						if (StringUtils.isBlank(pic_list)) {
							continue;
						}
						JSONArray parseArray = JSONArray.parseArray(pic_list);
						if (parseArray.size() < 3) {
							continue;
						}
						String p1 = parseArray.getJSONObject(0).getString("img_url");
						String p2 = parseArray.getJSONObject(1).getString("img_url");
						String p3 = parseArray.getJSONObject(2).getString("img_url");
						savaListMap.put("p1", p1);
						savaListMap.put("p2", p2);
						savaListMap.put("p3", p3);
						saveList.add(savaListMap);
					}
					savaJsonMap.put("unique_type", 1);
					savaJsonMap.put("title", tree_name);
					savaJsonMap.put("datas", saveList);
					DataCache dc = new DataCache();
					StringBuffer sb = new StringBuffer();
					sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
							.append(channeRelevance.getTree_id());
					dc.setKey(sb.toString());
					dc.setField(DateUtil.getCurrentDate());
					dc.setValue(JSONObject.toJSONString(savaJsonMap));
					saveCacheList.add(dc);

				}

			} else if (data_style == 4) {// c1,p1,p2,u1

				List<News> newss = cardDataDao.pressInNews(alias_name, show_number);
				if (!newss.isEmpty()) {

					Map<String, Object> savaJsonMap = new HashMap<String, Object>();
					List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
					for (News news : newss) {
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("c1", news.getTitle());
						savaListMap.put("u1", news.getSource_url());
						String pic_list = news.getPic_list();
						if (StringUtils.isBlank(pic_list)) {
							continue;
						}
						JSONArray parseArray = JSONArray.parseArray(pic_list);
						if (parseArray.size() < 2) {
							continue;
						}
						String p1 = parseArray.getJSONObject(0).getString("img_url");
						String p2 = parseArray.getJSONObject(1).getString("img_url");
						savaListMap.put("p1", p1);
						savaListMap.put("p2", p2);
						saveList.add(savaListMap);
					}
					savaJsonMap.put("unique_type", 1);
					savaJsonMap.put("title", tree_name);
					savaJsonMap.put("datas", saveList);
					DataCache dc = new DataCache();
					StringBuffer sb = new StringBuffer();
					sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
							.append(channeRelevance.getTree_id());
					dc.setKey(sb.toString());
					dc.setField(DateUtil.getCurrentDate());
					dc.setValue(JSONObject.toJSONString(savaJsonMap));
					saveCacheList.add(dc);
				}

			} else if (data_style == 6 || data_style == 5 || data_style == 9 || data_style == 12 || data_style == 38) {// c1,p1,u1
				// 9是推广可删除,图片为banner

				List<News> newss = cardDataDao.pressInNews(alias_name, show_number);
				if (!newss.isEmpty()) {
					Map<String, Object> savaJsonMap = new HashMap<String, Object>();
					List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
					for (News news : newss) {
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("p1", news.getPic_url());
						savaListMap.put("c1", news.getTitle());
						savaListMap.put("u1", news.getSource_url());

						saveList.add(savaListMap);
					}
					savaJsonMap.put("unique_type", 1);
					savaJsonMap.put("title", tree_name);
					savaJsonMap.put("datas", saveList);
					DataCache dc = new DataCache();
					StringBuffer sb = new StringBuffer();
					sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
							.append(channeRelevance.getTree_id());
					dc.setKey(sb.toString());
					dc.setField(DateUtil.getCurrentDate());
					dc.setValue(JSONObject.toJSONString(savaJsonMap));
					saveCacheList.add(dc);
				}

			} else if (data_style == 36) {// c1,c2,p1,u1 (banner)

				List<News> newss = cardDataDao.pressInNews(alias_name, show_number);
				if (!newss.isEmpty()) {
					Map<String, Object> savaJsonMap = new HashMap<String, Object>();
					List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
					for (News news : newss) {
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("p1", news.getPic_url());
						savaListMap.put("c1", news.getTitle());
						savaListMap.put("c1", news.getInstro());
						savaListMap.put("u1", news.getSource_url());
						saveList.add(savaListMap);
					}
					savaJsonMap.put("unique_type", 1);
					savaJsonMap.put("title", tree_name);
					savaJsonMap.put("datas", saveList);

					DataCache dc = new DataCache();
					StringBuffer sb = new StringBuffer();
					sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
							.append(channeRelevance.getTree_id());
					dc.setKey(sb.toString());
					dc.setField(DateUtil.getCurrentDate());
					dc.setValue(JSONObject.toJSONString(savaJsonMap));
					saveCacheList.add(dc);
				}

			} else if (data_style == 7) {// c1,u1

				List<News> newss = cardDataDao.pressInNews(alias_name, show_number);
				if (!newss.isEmpty()) {
					Map<String, Object> savaJsonMap = new HashMap<String, Object>();
					List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
					for (News news : newss) {
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("c1", news.getTitle());
						savaListMap.put("u1", news.getSource_url());
						saveList.add(savaListMap);
					}
					savaJsonMap.put("unique_type", 1);
					savaJsonMap.put("title", tree_name);
					savaJsonMap.put("datas", saveList);
					DataCache dc = new DataCache();
					StringBuffer sb = new StringBuffer();
					sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
							.append(channeRelevance.getTree_id());
					dc.setKey(sb.toString());
					dc.setField(DateUtil.getCurrentDate());
					dc.setValue(JSONObject.toJSONString(savaJsonMap));
					saveCacheList.add(dc);
				}

			} else if (data_style == 8 || data_style == 28) {// p1,u1

				List<News> newss = cardDataDao.pressInNews(alias_name, show_number);
				if (!newss.isEmpty()) {
					Map<String, Object> savaJsonMap = new HashMap<String, Object>();
					List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
					for (News news : newss) {
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						savaListMap.put("data_type", data_style);
						savaListMap.put("p1", news.getPic_url());
						savaListMap.put("u1", news.getSource_url());
						saveList.add(savaListMap);
					}
					savaJsonMap.put("unique_type", 1);
					savaJsonMap.put("title", tree_name);
					savaJsonMap.put("datas", saveList);
					DataCache dc = new DataCache();
					StringBuffer sb = new StringBuffer();
					sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
							.append(channeRelevance.getTree_id());
					dc.setKey(sb.toString());
					dc.setField(DateUtil.getCurrentDate());
					dc.setValue(JSONObject.toJSONString(savaJsonMap));
					saveCacheList.add(dc);
				}

			} else if (data_style == 30 || data_style == 39) {// 2
				// c1,c2,p1,p2,u1,u2
				List<News> newss = cardDataDao.pressInNews(alias_name, show_number);

				Map<Integer, List<News>> subMap = new HashMap<Integer, List<News>>();

				if (!newss.isEmpty() && newss.size() >= 2) {
					// 分组
					int in = 0;
					for (int i = 0; i < newss.size() / 2; i++) {
						List<News> subList = newss.subList(in, in += 2);
						subMap.put(i, subList);
					}

					Map<String, Object> savaJsonMap = new HashMap<String, Object>();
					List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();

					Collection<List<News>> values = subMap.values();
					for (List<News> list : values) {
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						News news1 = list.get(0);
						News news2 = list.get(1);
						savaListMap.put("data_type", data_style);
						savaListMap.put("p1", news1.getImg_url());
						savaListMap.put("p2", news2.getImg_url());
						savaListMap.put("c1", news1.getTitle());
						savaListMap.put("c2", news2.getTitle());
						savaListMap.put("u1", news1.getSource_url());
						savaListMap.put("u2", news2.getSource_url());
						saveList.add(savaListMap);
					}
					savaJsonMap.put("unique_type", 1);
					savaJsonMap.put("title", tree_name);
					savaJsonMap.put("datas", saveList);
					DataCache dc = new DataCache();
					StringBuffer sb = new StringBuffer();
					sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
							.append(channeRelevance.getTree_id());
					dc.setKey(sb.toString());
					dc.setField(DateUtil.getCurrentDate());
					dc.setValue(JSONObject.toJSONString(savaJsonMap));
					saveCacheList.add(dc);
				}

			} else if (data_style == 18) {// 3
											// c1,c2,p1,u1,u2,u3(banner)

				List<News> newss = cardDataDao.pressInNews(alias_name, show_number);

				Map<Integer, List<News>> subMap = new HashMap<Integer, List<News>>();

				if (!newss.isEmpty() && newss.size() >= 3) {

					// 分组
					int in = 0;
					for (int i = 0; i < newss.size() / 3; i++) {
						List<News> subList = newss.subList(in, in += 3);
						subMap.put(i, subList);
					}

					Map<String, Object> savaJsonMap = new HashMap<String, Object>();
					List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();

					Collection<List<News>> values = subMap.values();
					for (List<News> list : values) {
						Map<String, Object> savaListMap = new HashMap<String, Object>();
						News news1 = list.get(0);
						News news2 = list.get(1);
						News news3 = list.get(2);
						savaListMap.put("data_type", data_style);
						savaListMap.put("c1", news2.getTitle());
						savaListMap.put("c2", news3.getTitle());
						savaListMap.put("p1", news1.getPic_url());

						savaListMap.put("u1", news1.getSource_url());
						savaListMap.put("u2", news2.getSource_url());
						savaListMap.put("u3", news3.getSource_url());
						saveList.add(savaListMap);
					}
					savaJsonMap.put("unique_type", 1);
					savaJsonMap.put("title", tree_name);
					savaJsonMap.put("datas", saveList);
					DataCache dc = new DataCache();
					StringBuffer sb = new StringBuffer();
					sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
							.append(channeRelevance.getTree_id());
					dc.setKey(sb.toString());
					dc.setField(DateUtil.getCurrentDate());
					dc.setValue(JSONObject.toJSONString(savaJsonMap));
					saveCacheList.add(dc);
				}

			} else if (data_style == 29) {// 3
				// c1,c2,c3,c4,u1,u2,u3

				List<News> newss = cardDataDao.pressInNews(alias_name, show_number);
				Map<Integer, List<News>> subMap = new HashMap<Integer, List<News>>();

				if (!newss.isEmpty() && newss.size() >= 3) {

					// 分组
					int in = 0;
					for (int i = 0; i < newss.size() / 3; i++) {
						List<News> subList = newss.subList(in, in += 3);
						subMap.put(i, subList);
					}

					Map<String, Object> savaJsonMap = new HashMap<String, Object>();
					List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();

					Collection<List<News>> values = subMap.values();
					for (List<News> list : values) {
						Map<String, Object> savaListMap = new HashMap<String, Object>();

						News news1 = list.get(0);
						News news2 = list.get(1);
						News news3 = list.get(2);
						savaListMap.put("data_type", data_style);
						savaListMap.put("c1", "集锦");
						savaListMap.put("c2", news1.getTitle());
						savaListMap.put("c3", news2.getTitle());
						savaListMap.put("c4", news3.getTitle());

						savaListMap.put("u1", news1.getSource_url());
						savaListMap.put("u2", news2.getSource_url());
						savaListMap.put("u3", news3.getSource_url());
						saveList.add(savaListMap);

					}
					savaJsonMap.put("unique_type", 1);
					savaJsonMap.put("title", tree_name);
					savaJsonMap.put("datas", saveList);

					DataCache dc = new DataCache();
					StringBuffer sb = new StringBuffer();
					sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
							.append(channeRelevance.getTree_id());
					dc.setKey(sb.toString());
					dc.setField(DateUtil.getCurrentDate());
					dc.setValue(JSONObject.toJSONString(savaJsonMap));
					saveCacheList.add(dc);
				}

			}

		}

		int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(saveCacheList);
		redisDAO.updateRedis(saveCacheList);
		// 按别名取数据

		return saveCacheList;
	}

}
