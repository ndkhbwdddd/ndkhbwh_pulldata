package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yitianyike.calendar.pullserver.bo.StarBO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.Star;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("starBO")
public class StarBOImpl implements StarBO {

	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public List<DataCache> pressInStar(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");
		List<Star> starsList = cardDataDao.pressInStar();
		List<DataCache> list = new ArrayList<DataCache>();
		// 按别名取数据
		for (Star star : starsList) {
			String xz_name = star.getXz_name();
			ChanneRelevance channeRelevance = cardDataDao.getDataType(xz_name, channel_code);
			if (channeRelevance != null) {
				StringBuffer sb = new StringBuffer();
				sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
						.append(channeRelevance.getTree_id());

				DataCache dc = new DataCache();
				//
				String saveKey = sb.toString();
				dc.setKey(saveKey);
				//
				dc.setField(star.getDate().replace("-", ""));

				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("data_type", channeRelevance.getData_type());
				responseData.put("name", star.getXz_name());
				responseData.put("colour", star.getColour());
				responseData.put("date", star.getDate());

				responseData.put("logo", star.getLogo());
				responseData.put("matching", star.getMatching());
				responseData.put("number", star.getNumber());
				responseData.put("skip_url", star.getSkip_url());
				responseData.put("stzs", star.getStzs());
				responseData.put("complex", Integer.parseInt(star.getComplex()) * 2);
				responseData.put("discuss", Integer.parseInt(star.getDiscuss()) / 10);
				responseData.put("healthy", Integer.parseInt(star.getHealthy()) / 10);
				responseData.put("job", Integer.parseInt(star.getJob()) * 2);
				responseData.put("love", Integer.parseInt(star.getLove()) * 2);
				responseData.put("unique_type", 0);
				//
				dc.setValue(JSONObject.toJSONString(responseData));
				list.add(dc);
			}
		}
		int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(list);
		redisDAO.updateRedis(list);
		return list;

	}

}
