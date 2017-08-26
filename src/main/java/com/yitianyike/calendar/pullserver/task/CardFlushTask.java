package com.yitianyike.calendar.pullserver.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.DataCacheBO;
import com.yitianyike.calendar.pullserver.bo.SportCardDataBO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("task")
public class CardFlushTask {

	private List<Map<String, Object>> flushSportCardData = new ArrayList<Map<String, Object>>();
	@Autowired
	private DataCacheBO dataCacheBO;
	@Autowired
	private SportCardDataBO sportCardDataBO;

	//@Scheduled(cron = "0 0/2 * * * ?")
	public void flushSportCardData() {
		System.out.println("flushSportCardData定时器执行了");
		String channel_code = PropertiesUtil.channel_code;
		List<Map<String, Object>> flushMap = sportCardDataBO.flushSportCardData(channel_code);
		flushSportCardData.addAll(flushMap);
		System.out.println("flushSportCardData定时器结束了");
	}

	//@Scheduled(cron = "0 0/4 * * * ?")
	public void flushSportCardDataFromMap() {
		
		System.out.println("获取卡片数据定时器执行了");
		for (Map<String, Object> map : flushSportCardData) {
			String aid = map.get("aid").toString();
			String channel_code = map.get("channel_code").toString();
			long nowdate = new Date().getTime();
			long frequence = Long.parseLong(map.get("frequence").toString());
			Object last_time = map.get("last_time");
			long next_time = Long.parseLong(last_time.toString()) + (frequence * 1000);
			if (nowdate > next_time) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("channel_code", channel_code);
				params.put("cids", aid);
				params.put("version", PropertiesUtil.version);
				params.put("flush_style", "flush_style");
				List<Map<String, Object>> organizeCardData = sportCardDataBO.organizeCardData(params);
				dataCacheBO.insertCardList(organizeCardData, params);
				params.put("details", "details");
				List<Map<String, Object>> organizeCardData1 = sportCardDataBO.organizeCardData(params);
				List<DataCache> list = new ArrayList<DataCache>();
				for (Map<String, Object> map2 : organizeCardData1) {
					List<Map<String, Object>> keydata = (List<Map<String, Object>>) map2.get("keydata");
					DataCache dataCache = new DataCache();
					String savekey = map2.get("savekey").toString();
					for (Map<String, Object> map3 : keydata) {
						String savefield = map3.get("savefield").toString();
						String saveValue = map3.get("saveValue").toString();
						dataCache.setField(savefield);
						dataCache.setKey(savekey);
						dataCache.setValue(saveValue);
						dataCache.setCreate_time(new Date().getTime());
						list.add(dataCache);
					}
					//dataCacheBO.insertDataCacheListNOFiled(savekey , list);
				}
				map.put("last_time", new Date().getTime());
			}
		}
		System.out.println("获取卡片数据定时器执行结束");
	}

}
