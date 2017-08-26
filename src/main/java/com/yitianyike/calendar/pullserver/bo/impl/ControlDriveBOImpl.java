package com.yitianyike.calendar.pullserver.bo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.ControlDriveBO;
import com.yitianyike.calendar.pullserver.bo.StarBO;
import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.Star;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.DateUtil;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("controlDriveBO")
public class ControlDriveBOImpl implements ControlDriveBO {

	@Autowired
	private AlmanacDAO almanacDAO;
	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public List<DataCache> pressInControlDrive(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");
		List<ControlDrive> controlDriveList = cardDataDao.pressInControlDrive();
		List<DataCache> list = new ArrayList<DataCache>();
		ChanneRelevance channeRelevance = cardDataDao.getDataType("限行", channel_code);
		
		//聚合map
		Map<String,ControlDrive>  aggregationMap = new HashMap<String, ControlDrive>();
		for (ControlDrive controlDrive : controlDriveList) {
			StringBuffer sb =new StringBuffer();
			sb.append(controlDrive.getDate()).append("-").append(controlDrive.getCityname());
			aggregationMap.put(sb.toString(), controlDrive);
		}
		
		
		if(channeRelevance!=null){
			for (ControlDrive controlDrive : controlDriveList) {
				
				StringBuffer sb = new StringBuffer();
				sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-")
						.append(controlDrive.getControl_drive_id());

				DataCache dc = new DataCache();
				//
				String saveKey = sb.toString();
				dc.setKey(saveKey);
				//
				dc.setField(controlDrive.getDate().replace("-", ""));

				Map<String, Object> responseData = new HashMap<String, Object>();
				List<Map<String, Object>> twoDate = new ArrayList<Map<String, Object>>();
				String cityname = controlDrive.getCityname();
				responseData.put("cityname", cityname);
				responseData.put("skip_url", controlDrive.getSkip_url());
				
				Map<String, Object>	todayData = new HashMap<String, Object>();
				String date = controlDrive.getDate();
				responseData.put("data_type", channeRelevance.getData_type());
				todayData.put("date", date);
				todayData.put("week", controlDrive.getWeek());
				todayData.put("xxweihao", controlDrive.getXxweihao());
				
				
				twoDate.add(todayData);
				//tomorrow
				String decreaseDate = DateUtil.getDecreaseDate(date);
				StringBuffer sbkey =new StringBuffer();
				sbkey.append(decreaseDate).append("-").append(cityname);
				ControlDrive tomorrowControlDrive = aggregationMap.get(sbkey.toString());
				if(tomorrowControlDrive!=null){
					Map<String, Object>	tomorrowData = new HashMap<String, Object>();
					tomorrowData.put("date", tomorrowControlDrive.getDate());
					tomorrowData.put("week", tomorrowControlDrive.getWeek());
					tomorrowData.put("xxweihao", tomorrowControlDrive.getXxweihao());
					twoDate.add(tomorrowData);
				}
				responseData.put("data", twoDate);
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
