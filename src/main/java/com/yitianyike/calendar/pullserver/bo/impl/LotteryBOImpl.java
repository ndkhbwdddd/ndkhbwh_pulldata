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

import com.yitianyike.calendar.pullserver.bo.LotteryBO;
import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.Lottery;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("lotteryBO")
public class LotteryBOImpl implements LotteryBO {

	@Autowired
	private AlmanacDAO almanacDAO;
	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public List<DataCache> pressInLottery(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");

		// 获取这个渠道下所有彩票的订阅项
		List<ChanneRelevance> channeRelevances = cardDataDao.getDataTypeForLottery(channel_code);
		List<DataCache> list = new ArrayList<DataCache>();
		for (ChanneRelevance channeRelevance : channeRelevances) {

			// 前3后6
			List<Lottery> lotterys = cardDataDao.pressInLottery(channeRelevance.getAlias_name());
			if (!lotterys.isEmpty()) {

				StringBuffer sb = new StringBuffer();
				sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
						.append(channeRelevance.getTree_id());

				//
				String saveKey = sb.toString();
				for (Lottery lottery : lotterys) {
					DataCache dc = new DataCache();
					dc.setKey(saveKey);
					String open = lottery.getOpen();
					Map<String, Object> responseData = new HashMap<String, Object>();
					responseData.put("data_type", channeRelevance.getData_type());
					responseData.put("skip_url", lottery.getSkip_url());
					responseData.put("name", lottery.getName());
					responseData.put("open", open);
					responseData.put("color", lottery.getColor());
					responseData.put("detail", lottery.getDetail());
					responseData.put("phase", lottery.getPhase());
					responseData.put("unique_type", 0);
					responseData.put("more", 1);
					responseData.put("sub_aid", channeRelevance.getTree_id());
					
					
					dc.setField(open.replace("-", ""));
					//
					dc.setValue(JSONObject.toJSONString(responseData));
					list.add(dc);
				}

			}
		}

		int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(list);
		redisDAO.updateRedis(list);

		return list;
	}

}
