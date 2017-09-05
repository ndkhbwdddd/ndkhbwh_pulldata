package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.FilmBO;
import com.yitianyike.calendar.pullserver.bo.LotteryBO;
import com.yitianyike.calendar.pullserver.bo.PictureBO;
import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.handler.BusinessHandler;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.Film;
import com.yitianyike.calendar.pullserver.model.responseCardData.Lottery;
import com.yitianyike.calendar.pullserver.model.responseCardData.Picture;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.DateUtil;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("pictureBO")
public class PictureBOImpl implements PictureBO {

	@Autowired
	private AlmanacDAO almanacDAO;
	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");
	private static final Logger logger = Logger.getLogger(PictureBOImpl.class);

	@Override
	public List<DataCache> pressInPicture(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");
		List<DataCache> saveCacheList = new ArrayList<DataCache>();
		// 获取这个渠道下所有美图的订阅项
		List<ChanneRelevance> channeRelevances = cardDataDao.getDataTypeForPicture(channel_code);

		for (ChanneRelevance channeRelevance : channeRelevances) {
			String alias_name = channeRelevance.getAlias_name();
			int data_style = channeRelevance.getData_style();
			// 前十后十天
			List<Date> dates = DateUtil.findStringTenDates();
			for (Date date : dates) {
				long time = date.getTime();
				// 发布计划结束和开始
				List<Picture> pictures = cardDataDao.pressInPicture(alias_name, Long.toString(time));
				StringBuffer sb = new StringBuffer();
				sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
						.append(channeRelevance.getTree_id());
				// 如果有此发布计划
				if ( pictures.size() >= 3) {
					Picture picture = pictures.get(0);
					Picture picture2 = pictures.get(1);
					Picture picture3 = pictures.get(2);

					Map<String, Object> savaListMap = new HashMap<String, Object>();
					savaListMap.put("data_type", data_style);
					savaListMap.put("unique_type", 0);
					savaListMap.put("p1", picture.getSnapshot());
					savaListMap.put("p2", picture2.getSnapshot());
					savaListMap.put("p3", picture3.getSnapshot());
					savaListMap.put("c1", picture.getTitle());
					savaListMap.put("c2", picture2.getTitle());
					savaListMap.put("c3", picture3.getTitle());
					savaListMap.put("u1", picture.getSkip_url());
					savaListMap.put("u2", picture2.getSkip_url());
					savaListMap.put("u3", picture3.getSkip_url());
					String jsonString = JSONObject.toJSONString(savaListMap);

					DataCache dc = new DataCache();
					dc.setKey(sb.toString());
					dc.setField(DateUtil.getyyyyMMddDate(date));
					dc.setValue(jsonString);
					saveCacheList.add(dc);

					// 如果无此发布计划
				} else {
					dataCacheDAO.deleteDataCache(sb.toString());
					redisDAO.deleteRedis(sb.toString());
				}

			}
		}
		int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(saveCacheList);
		redisDAO.updateRedis(saveCacheList);

		return saveCacheList;
	}

}
