package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yitianyike.calendar.pullserver.bo.LogBO;
import com.yitianyike.calendar.pullserver.bo.StarBO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.Star;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("logBO")
public class LogBOImpl implements LogBO {

	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public List<String> getChannels() {
		List<String> channels = cardDataDao.getChannels();
		return channels;
	}

	@Override
	public void writLogs(String channel, String key, String value) {
		cardDataDao.writLogs(channel, key, value);
	}

}
