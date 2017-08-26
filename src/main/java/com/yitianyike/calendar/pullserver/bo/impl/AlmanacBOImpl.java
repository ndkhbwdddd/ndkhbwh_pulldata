package com.yitianyike.calendar.pullserver.bo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.AlmanacBO;
import com.yitianyike.calendar.pullserver.bo.DataCacheBO;
import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.Almanac;
import com.yitianyike.calendar.pullserver.model.Anchor;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;
@Component("almanacBO")
public class AlmanacBOImpl implements AlmanacBO {

	@Autowired
	private AlmanacDAO almanacDAO;
	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");
	
	@Override
	public int[] setAlmanac(Map<String , String> parmMap) {
		List<Anchor> findAnchorData = cardDataDao
				.findAnchorData(parmMap);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Almanac> almanacList = new ArrayList<Almanac>();
		for (Anchor anchor : findAnchorData) {
			map.put("param", new ArrayList<Object>());
			map.put("dataStyle", anchor.getCard_style());
			Map<String, Object> datamap = new HashMap<String, Object>();
			datamap.put("frequence", anchor.getFrequence());
			map.put("data", datamap);
			map.put("aid", anchor.getAnchor_id());
			map.put("endTime", "");
			map.put("type", anchor.getType());
			map.put("expDateTime", anchor.getExp_date_time());
			map.put("dataType", anchor.getLittle_type());
			map.put("column_name", anchor.getColumn_name());
			map.put("column_id", anchor.getId());
			almanacList = almanacDAO.getAllAlmanac(anchor.getAnchor_id());
			if(!almanacList.isEmpty()){
				break;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		List<DataCache> list = new ArrayList<DataCache>();
		String cacheKey = parmMap.get("channel_code")+"-"+PropertiesUtil.version+"-"+map.get("column_id");
		for (Almanac almanac : almanacList) {
			map.put("preference", almanac);
			DataCache dataCache = new DataCache();
			dataCache.setKey(cacheKey);
			dataCache.setField(sdf.format(almanac.getDate()));
			dataCache.setValue(JSONObject.fromObject(map).toString());
			dataCache.setCreate_time(new Date().getTime());
			list.add(dataCache);
		}
		int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(cacheKey, list);
		redisDAO.updateRedis(list);
		return insertDataCacheList;
	}

	
}
