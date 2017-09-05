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

import com.yitianyike.calendar.pullserver.bo.FootBasketBO;
import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.FootBasket;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("footBasketBO")
public class FootBasketBOImpl implements FootBasketBO {

	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public List<DataCache> pressInFootBasket(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");
		List<FootBasket> ths = cardDataDao.pressInFootBasket();

		List<DataCache> list = new ArrayList<DataCache>();

		if (!ths.isEmpty()) {

			for (FootBasket footBasket : ths) {
				
				String hostname = footBasket.getHostname();
				String guestname = footBasket.getGuestname();
				// 理论上出现2条数据
				List<ChanneRelevance> channeRelevance = cardDataDao.getDataType(hostname, guestname, channel_code);

				for (ChanneRelevance channeRelevancel : channeRelevance) {
					StringBuffer sb = new StringBuffer();
					sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
							.append(channeRelevancel.getTree_id());

					DataCache dc = new DataCache();
					//
					String saveKey = sb.toString();
					dc.setKey(saveKey);
					//

					dc.setField(footBasket.getDate().replace("-", ""));

					Map<String, Object> responseData = new HashMap<String, Object>();
					responseData.put("data_type", channeRelevancel.getData_type());
					responseData.put("rnd", footBasket.getRnd());
					responseData.put("hostscore", footBasket.getHostscore());
					responseData.put("hostname", footBasket.getHostname());
					responseData.put("hostflag", footBasket.getHostflag());
					responseData.put("guestname", footBasket.getGuestname());
					responseData.put("guestscore", footBasket.getGuestscore());
					responseData.put("guestflag", footBasket.getGuestflag());
					responseData.put("matchurl", footBasket.getMatchurl());
					responseData.put("status", footBasket.getStatus());
					responseData.put("date", footBasket.getDate());
					responseData.put("time", footBasket.getTime());
					responseData.put("package_title", footBasket.getPackage_title());
					responseData.put("unique_type", 0);

					//
					dc.setValue(JSONObject.toJSONString(responseData));
					list.add(dc);
				}
			}

			int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(list);
			redisDAO.updateRedis(list);
			// 按别名取数据

		}
		return list;
	}

}
