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

import com.yitianyike.calendar.pullserver.bo.AidTypeBO;
import com.yitianyike.calendar.pullserver.bo.FootBasketBO;
import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.DataType;
import com.yitianyike.calendar.pullserver.model.responseCardData.FootBasket;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("aidTypeBO")
public class AidTypeBOImpl implements AidTypeBO {

	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public List<DataCache> pressInAidType(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");
		//aid类型
		List<DataType> dts = cardDataDao.pressInDataType(channel_code);

		List<DataCache> list = new ArrayList<DataCache>();
		StringBuffer sb = new StringBuffer();
		sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-").append("aidtype");

		//确定场景类型
		List<DataType> scenedts = cardDataDao.pressInSceneType(channel_code);
		if (!dts.isEmpty()||!scenedts.isEmpty()) {

			for (DataType dt : dts) {
				DataCache dc = new DataCache();
				//
				String saveKey = sb.toString();
				dc.setKey(saveKey);
				dc.setField(dt.getTree_id());
				//
				dc.setValue(dt.getData_type());
				list.add(dc);
			}
			
			for (DataType sdt : scenedts) {
				DataCache dc = new DataCache();
				//
				String saveKey = sb.toString();
				dc.setKey(saveKey);
				dc.setField(sdt.getTree_id());
				//
				dc.setValue("66");
				list.add(dc);
			}

			int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(list);
			redisDAO.updateRedis(list);
			// 按别名取数据

		}
		return list;
	}

}
