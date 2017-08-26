package com.yitianyike.calendar.pullserver.bo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.SportCardDataBO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.model.Anchor;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

import net.sf.json.JSONObject;

@Component("sportCardDataBO")
public class SportCardDataBOImpl implements SportCardDataBO {

	@Autowired
	private CardDataDao cardDataDao;

	@Override
	public List<Map<String, Object>> organizeCardData(Map<String, String> paramMap) {
		if (!paramMap.containsKey("cids")) {
			List<Map<String, Object>> flushMap = flushSportCardData(paramMap.get("channel_code"));
			if (!flushMap.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				for (Map<String, Object> map : flushMap) {
					String aid = map.get("aid").toString();
					sb.append(aid).append(",");
				}
				paramMap.put("cids", sb.toString().substring(0, sb.toString().length() - 1));
			}
		}
		List<Anchor> anchorList = cardDataDao.findAnchorData(paramMap);
		List<Map<String, Object>> organizeCardData = organizeCardData(anchorList, paramMap);
		if(paramMap.containsKey("details")){
			organizeCardData = organizeCardDataDetails(anchorList, paramMap);
		}
		return organizeCardData;
	}

	private List<Map<String, Object>> organizeCardData(List<Anchor> anchorList, Map<String, String> paramMap) {
		List<Map<String, Object>> resultGroup = new ArrayList<Map<String, Object>>();
		for (Anchor anchor : anchorList) {
			int id = anchor.getId();
			Integer anchor_id = anchor.getAnchor_id();
			int card_style = anchor.getCard_style();
			String column_name = anchor.getColumn_name();
			long exp_date_time = anchor.getExp_date_time();
			int frequence = anchor.getFrequence();
			int type = anchor.getType();
			int little_type = anchor.getLittle_type();
			String quote_name = anchor.getQuote_name();
			String season = anchor.getSeason();
			paramMap.put("anchor_id", anchor_id.toString());
			paramMap.put("quote_name", quote_name);
			paramMap.put("season", season);
			List<Map<String, Object>> balls = new ArrayList<Map<String, Object>>();

			StringBuilder sbkey = new StringBuilder();
			sbkey.append(paramMap.get("channel_code").toString()).append("-").append(PropertiesUtil.getValue("version"))
					.append("-").append(id);

			if (little_type == 3) {// 篮球
				balls = cardDataDao.organizeBasketBalls(paramMap);
			} else if (little_type == 4) { // 足球
				balls = cardDataDao.organizeFootBalls(paramMap);
			}
			Map<String, Object> keyMap = new HashMap<String, Object>();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> ball : balls) {

				Map<String, Object> resultMap = new HashMap<String, Object>();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("param", new ArrayList<Object>());
				map.put("dataStyle", card_style);
				Map<String, Object> datamap = new HashMap<String, Object>();
				datamap.put("frequence", frequence);
				map.put("data", datamap);
				map.put("aid", anchor_id);
				map.put("endTime", "");
				map.put("type", type);
				map.put("expDateTime", exp_date_time);
				map.put("dataType", little_type);
				map.put("column_name", column_name);
				orgBall(map, ball);

				resultMap.put("savefield", ball.get("date").toString().replace("-", ""));
				resultMap.put("saveValue", JSONObject.fromObject(map).toString());
				resultList.add(resultMap);
			}
			keyMap.put("savekey", sbkey.toString());
			keyMap.put("keydata", resultList);
			resultGroup.add(keyMap);
		}
		return resultGroup;
	}

	
	private List<Map<String, Object>> organizeCardDataDetails(List<Anchor> anchorList, Map<String, String> paramMap) {
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMdd");
		List<Map<String, Object>> resultGroup = new ArrayList<Map<String, Object>>();
		for (Anchor anchor : anchorList) {
			int id = anchor.getId();
			Integer anchor_id = anchor.getAnchor_id();
			int card_style = anchor.getCard_style();
			String column_name = anchor.getColumn_name();
			long exp_date_time = anchor.getExp_date_time();
			int frequence = anchor.getFrequence();
			int type = anchor.getType();
			int little_type = anchor.getLittle_type();
			String quote_name = anchor.getQuote_name();
			String season = anchor.getSeason();
			paramMap.put("anchor_id", anchor_id.toString());
			paramMap.put("quote_name", quote_name);
			paramMap.put("season", season);
			List<Map<String, Object>> balls = new ArrayList<Map<String, Object>>();

			StringBuilder sbkey = new StringBuilder();
			sbkey.append(paramMap.get("channel_code").toString()).append("-").append(PropertiesUtil.getValue("version")).append("-").append("details")
					.append("-").append(id);

			if (little_type == 3) {// 篮球
				balls = cardDataDao.getBasketBalls(paramMap);
			} else if (little_type == 4) { // 足球
				balls = cardDataDao.getFootBalls(paramMap);
			}
			Map<String, Object> keyMap = new HashMap<String, Object>();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
//			for (Map<String, Object> ball : balls) {

				Map<String, Object> resultMap = new HashMap<String, Object>();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("param", new ArrayList<Object>());
				map.put("dataStyle", card_style);
				Map<String, Object> datamap = new HashMap<String, Object>();
				datamap.put("frequence", frequence);
				map.put("data", datamap);
				map.put("aid", anchor_id);
				map.put("endTime", "");
				map.put("type", type);
				map.put("expDateTime", exp_date_time);
				map.put("dataType", little_type);
				map.put("column_name", column_name);
//				orgBall(map, ball);
//				map.put("startTime", startTime);
				map.put("preference", balls);
				resultMap.put("savefield", sbkey.toString());
				resultMap.put("saveValue", JSONObject.fromObject(map).toString());
				resultList.add(resultMap);
//			}
			keyMap.put("savekey", sbkey.toString());
			keyMap.put("keydata", resultList);
			resultGroup.add(keyMap);
		}
		return resultGroup;
	}
	
	private void orgBall(Map<String, Object> map, Map<String, Object> ball) {
		StringBuilder sb = new StringBuilder(ball.get("date").toString());
		sb.append(" ").append(ball.get("time").toString());
		Long startTime = stringToDate(sb.toString()).getTime();
		map.put("startTime", startTime);
		map.put("preference", ball);
	}

	public Date stringToDate(String datetime) {
		SimpleDateFormat format;
		Date date = null;
		try {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
			date = format.parse(datetime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	@Override
	public List<Map<String, Object>> flushSportCardData(String channel_code) {
		return cardDataDao.flushSportCardData(channel_code);
	}

}
