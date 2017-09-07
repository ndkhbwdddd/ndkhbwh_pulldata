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
import com.yitianyike.calendar.pullserver.bo.FestivalZipBO;
import com.yitianyike.calendar.pullserver.bo.FootBasketBO;
import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.common.EnumConstants;
import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responseCardData.AlmanacZip;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.DataType;
import com.yitianyike.calendar.pullserver.model.responseCardData.FestivalZip;
import com.yitianyike.calendar.pullserver.model.responseCardData.FootBasket;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.CCRDFile;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;
import com.yitianyike.calendar.pullserver.util.QiNiuUploadUtils;
import com.yitianyike.calendar.pullserver.util.WriteJsonToFile;
import com.yitianyike.calendar.pullserver.util.ZipCompressing;

@Component("festivalZipBO")
public class FestivalZipBOImpl implements FestivalZipBO {

	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public List<DataCache> pressInFestivalZip(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");

		List<DataCache> saveCachelist = new ArrayList<DataCache>();

		// 获取这个渠道下所有为节日的订阅项
		List<ChanneRelevance> channeRelevance = cardDataDao.getDataTypeForFestival(channel_code);

		for (ChanneRelevance cr : channeRelevance) {
			String alias_name = cr.getAlias_name();

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<FestivalZip> azs = cardDataDao.pressInFestivalZip(alias_name);
			Map<String, Object> mapZip = new HashMap<String, Object>();
			for (FestivalZip fz : azs) {

				Map<String, Object> azsmap = new HashMap<String, Object>();
				azsmap.put("data_type", cr.getData_type());
				azsmap.put("zh_name", fz.getZh_name());
				azsmap.put("icon", fz.getIcon());
				azsmap.put("source", fz.getSource());
				azsmap.put("date", fz.getDate());
				azsmap.put("banner_url", fz.getBanner_url());
				azsmap.put("country_id", fz.getCountry_id());
				azsmap.put("skip_url", fz.getSkip_url());
				list.add(azsmap);
			}
			mapZip.put("type", EnumConstants.FESTIVAL);
			mapZip.put("data", list);
			mapZip.put("more", 0);
			mapZip.put("sub_aid", cr.getTree_id());
			String almanacZipJsonString = JSONObject.toJSONString(mapZip);
			String saveZipUrl = saveZip(channel_code, almanacZipJsonString);
			cardDataDao.pressInZipUrl(channel_code, saveZipUrl, cr.getAlias_name(), cr.getTree_id());
			StringBuffer sb = new StringBuffer();
			sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-downzip");

			DataCache dc = new DataCache();
			dc.setKey(sb.toString());
			dc.setField(Integer.toString(cr.getTree_id()));
			dc.setValue(saveZipUrl);
			saveCachelist.add(dc);
		}

		redisDAO.updateRedis(saveCachelist);
		return saveCachelist;
	}

	private String saveZip(String channel_code, String almanacZipJsonString) {
		StringBuffer sbPath = new StringBuffer();
		sbPath.append("src/main/resources/cardresource/").append(channel_code);
		String path = sbPath.toString();
		CCRDFile.deleteFolder(path);
		CCRDFile.createDir(path);
		String filePath = path + "/festival.json";
		CCRDFile.createFile(filePath);
		WriteJsonToFile.writeJsonToFile(almanacZipJsonString, filePath);

		String zipfilePath = path + "/festival.zip";
		try {
			ZipCompressing.zip(zipfilePath, new File(filePath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return QiNiuUploadUtils.upZip(zipfilePath);
	}

}
