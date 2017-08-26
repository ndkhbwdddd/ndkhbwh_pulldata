package com.yitianyike.calendar.pullserver.bo.impl;

import java.io.File;
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
import com.yitianyike.calendar.pullserver.bo.AlmanacZipBO;
import com.yitianyike.calendar.pullserver.bo.FootBasketBO;
import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.AlmanacZip;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.DataType;
import com.yitianyike.calendar.pullserver.model.responseCardData.FootBasket;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.CCRDFile;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;
import com.yitianyike.calendar.pullserver.util.QiNiuUploadUtils;
import com.yitianyike.calendar.pullserver.util.WriteJsonToFile;
import com.yitianyike.calendar.pullserver.util.ZipCompressing;

@Component("almanacZipBO")
public class AlmanacZipBOImpl implements AlmanacZipBO {

	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public List<DataCache> pressInAlmanacZip(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");
		List<AlmanacZip> azs = cardDataDao.pressInAlmanacZip();
		List<DataCache> saveCachelist = new ArrayList<DataCache>();

		ChanneRelevance channeRelevance = cardDataDao.getDataType("黄历", channel_code);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (channeRelevance != null || !azs.isEmpty()) {
			for (AlmanacZip az : azs) {
				Map<String, Object> azsmap = new HashMap<String, Object>();
				azsmap.put("data_type", channeRelevance.getData_type());
				azsmap.put("lunar_year", az.getLunar_year());
				azsmap.put("animals_year", az.getAnimals_year());
				azsmap.put("avoid", az.getAvoid());
				azsmap.put("suit", az.getSuit());
				azsmap.put("lunar", az.getLunar());
				azsmap.put("date", az.getDate());
				azsmap.put("skip_url", az.getSkip_url());
				list.add(azsmap);
			}

			String almanacZipJsonString = JSONObject.toJSONString(list);
			String saveZipUrl = saveZip(channel_code, almanacZipJsonString);
			cardDataDao.pressInZipUrl(channel_code, saveZipUrl, channeRelevance.getAlias_name(),
					channeRelevance.getTree_id());

			StringBuffer sb = new StringBuffer();
			sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-downzip");
			DataCache dc = new DataCache();
			dc.setKey(sb.toString());
			dc.setField(Integer.toString(channeRelevance.getTree_id()));
			dc.setValue(saveZipUrl);
			saveCachelist.add(dc);
			redisDAO.updateRedis(saveCachelist);
		}

		return saveCachelist;
	}

	private String saveZip(String channel_code, String almanacZipJsonString) {
		StringBuffer sbPath = new StringBuffer();
		sbPath.append("src/main/resources/cardresource/").append(channel_code);
		String path = sbPath.toString();
		CCRDFile.deleteFolder(path);
		CCRDFile.createDir(path);
		String filePath = path + "/almanac.json";
		CCRDFile.createFile(filePath);
		WriteJsonToFile.writeJsonToFile(almanacZipJsonString, filePath);

		String zipfilePath = path + "/almanac.zip";
		try {
			ZipCompressing.zip(zipfilePath, new File(filePath));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("压缩出现问题");
			return null;
		}
		return QiNiuUploadUtils.upZip(zipfilePath);
	}

}
