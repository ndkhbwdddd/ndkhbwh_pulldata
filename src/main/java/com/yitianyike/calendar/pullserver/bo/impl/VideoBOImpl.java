package com.yitianyike.calendar.pullserver.bo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.DataCacheBO;
import com.yitianyike.calendar.pullserver.bo.VideoBO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.ColumnDAO;
import com.yitianyike.calendar.pullserver.dao.ColumnExtendDAO;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.dao.VideoDAO;
import com.yitianyike.calendar.pullserver.model.Anchor;
import com.yitianyike.calendar.pullserver.model.Column;
import com.yitianyike.calendar.pullserver.model.ColumnExtend;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.Video;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

//@Component("videoBO")
//public class VideoBOImpl implements VideoBO {
//
//	@Autowired
//	private VideoDAO videoDAO;
//	@Autowired
//	private CardDataDao cardDataDao;
//	@Autowired
//	private ColumnExtendDAO columnExtendDAO;
//	@Autowired
//	private ColumnDAO columnDAO;
//	
//	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
//	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");
//	
//	
//	@Override
//	public String setVideo(Map<String, String> paramMap) {
//		String res = "success";
//		List<Column> columnList = columnDAO.getColumnById(paramMap);
//		for (Column column : columnList) {
//			List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
////			List<List<Map<String, Object>>> allList = new ArrayList<List<Map<String, Object>>>();
//			if (column.getSkipAction() == 2) {
//				List<ColumnExtend> ceList = columnExtendDAO
//						.getExtendByCid(column.getId());
//				for (ColumnExtend columnExtend : ceList) {
//					paramMap.put("card_style", columnExtend.getCardStyle() + "");
//					paramMap.put("column_name", column.getColumnName());
//					paramMap.put("exp_date_time", columnExtend.getExpDateTime()
//							.toString());
//					paramMap.put("frequence", columnExtend.getFrequence());
//					paramMap.put("type", columnExtend.getType() + "");
//					paramMap.put("little_type", columnExtend.getLittleType()
//							+ "");
//					paramMap.put("column_id", column.getId() + "");
//					List<Video> videoList = videoDAO
//							.getAllVideoByType(columnExtend.getAnchorId());
//
//					List<Map<String, Object>> resmapList = pullData(videoList,
//							columnExtend.getStartTime(), paramMap, column);
//					resList.addAll(resmapList);
////					allList.add(resList);
//				}
//			} else {
//				List<Anchor> findAnchorData = cardDataDao
//						.findAnchorData(paramMap);
//				for (Anchor anchor : findAnchorData) {
//					paramMap.put("card_style", anchor.getCard_style() + "");
//					paramMap.put("column_name", anchor.getColumn_name());
//					paramMap.put("exp_date_time", anchor.getExp_date_time()
//							+ "");
//					paramMap.put("frequence", anchor.getFrequence() + "");
//					paramMap.put("type", anchor.getType() + "");
//					paramMap.put("little_type", anchor.getLittle_type() + "");
//					paramMap.put("column_id", column.getId() + "");
//					Integer anchor_id = anchor.getAnchor_id();
//					List<Video> videoList = new ArrayList<Video>();
//					videoList = videoDAO.getAllVideoByType(anchor_id);
//					if(!videoList.isEmpty()){
//						List<Map<String, Object>> resmapList = pullData(videoList,
//								null, paramMap, column);
//						resList.addAll(resmapList);
////						allList.add(resList);
//					}
//				}
//			}
//			int[] addData = addData(paramMap, column, resList);
//			if (addData.length == 0) {
//				res = "error";
//			}
//		}
//		return res;
//	}
//
//	public List<Map<String, Object>> pullData(List<Video> videoList,
//			Long startTime, Map<String, String> paramMap, Column column) {
//
//		List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
//
//		for (Video video : videoList) {
//			video.setExtendUrl(PropertiesUtil.getValue("video_extend"));
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("param", new ArrayList<Object>());
//			map.put("dataStyle", paramMap.get("card_style"));
//			Map<String, Object> datamap = new HashMap<String, Object>();
//			datamap.put("frequence", paramMap.get("frequence"));
//			map.put("data", datamap);
//			if (column.getSkipAction() == 2) {
//				map.put("aid", column.getId());
//			} else {
//				map.put("aid", video.getAid());
//			}
//			map.put("endTime", "");
//			map.put("type", paramMap.get("type"));
//			map.put("expDateTime", paramMap.get("exp_date_time"));
//			map.put("dataType", paramMap.get("little_type"));
//			map.put("column_name", paramMap.get("column_name"));
//			if (startTime != null) {
//				map.put("startTime", startTime);
//			} else {
//				map.put("startTime", video.getReleaseDate());
//			}
//			map.put("preference", video);
//			resList.add(map);
//		}
//
//		return resList;
//	}
//
//	public int[] addData(Map<String, String> paramMap, Column column,
//			List<Map<String, Object>>  allList) {
//		String cacheKey = paramMap.get("channel_code") + "-"
//				+ PropertiesUtil.version + "-" + paramMap.get("column_id");
//		List<DataCache> list = new ArrayList<DataCache>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		DataCache dataCache = new DataCache();
//		dataCache.setKey(cacheKey);
//		dataCache.setField(sdf.format(column.getUpdateTime()));
//		dataCache.setValue(JSONArray.fromObject(allList).toString());
//		dataCache.setCreate_time(new Date().getTime());
//		list.add(dataCache);
//		int[] insertDataCacheList = new int[] {};
//		if (list.isEmpty()) {
//			insertDataCacheList = new int[] { 0 };
//		} else {
//			insertDataCacheList = dataCacheDAO.insertDataCacheList(cacheKey,
//					list);
//			redisDAO.updateRedis(list);
//		}
//		return insertDataCacheList;
//	}
//	
//	
//	public void setDetails(Map<String, String> paramMap){
//		List<Column> columnList = columnDAO.getColumnById(paramMap);
//		for (Column column : columnList) {
//			List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
//			List<Anchor> findAnchorData = cardDataDao
//					.findAnchorData(paramMap);
//			for (Anchor anchor : findAnchorData) {
//				paramMap.put("card_style", anchor.getCard_style() + "");
//				paramMap.put("column_name", anchor.getColumn_name());
//				paramMap.put("exp_date_time", anchor.getExp_date_time()
//						+ "");
//				paramMap.put("frequence", anchor.getFrequence() + "");
//				paramMap.put("type", anchor.getType() + "");
//				paramMap.put("little_type", anchor.getLittle_type() + "");
//				paramMap.put("column_id", column.getId() + "");
//				Integer anchor_id = anchor.getAnchor_id();
//				List<Video> videoList = new ArrayList<Video>();
//				videoList = videoDAO.getVideoByType(anchor_id);
//				if(!videoList.isEmpty()){
//					List<Map<String, Object>> resmapList = pullData(videoList,
//							null, paramMap, column);
//					resList.addAll(resmapList);
//				}
//			}
//			
//			String cacheKey = paramMap.get("channel_code") + "-"
//					+ PropertiesUtil.version +"-" + "details" + "-" + paramMap.get("column_id");
//			List<DataCache> list = new ArrayList<DataCache>();
//			DataCache dataCache = new DataCache();
//			dataCache.setKey(cacheKey);
//			dataCache.setField(cacheKey);
//			dataCache.setValue(JSONArray.fromObject(resList).toString());
//			dataCache.setCreate_time(new Date().getTime());
//			list.add(dataCache);
//			int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(cacheKey,
//					list);
//			redisDAO.updateRedisNOFiled(list);
//		}
//		
//	}
//}
