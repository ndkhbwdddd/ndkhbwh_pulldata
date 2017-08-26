package com.yitianyike.calendar.pullserver.dao;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.Anchor;
import com.yitianyike.calendar.pullserver.model.responseCardData.AlmanacZip;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.DataType;
import com.yitianyike.calendar.pullserver.model.responseCardData.FootBasket;
import com.yitianyike.calendar.pullserver.model.responseCardData.Star;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;

public interface CardDataDao {

	List<Anchor> findAnchorData(Map<String, String> paramMap);

	List<Map<String, Object>> organizeBasketBalls(Map<String, String> paramMap);

	List<Map<String, Object>> organizeFootBalls(Map<String, String> paramMap);

	List<Map<String, Object>> flushSportCardData(String channel_code);

	/**
	 * 查询最近比赛
	 * 
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getBasketBalls(Map<String, String> paramMap);

	/**
	 * 查询最近比赛
	 * 
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getFootBalls(Map<String, String> paramMap);

	// 星座 v1
	List<Star> pressInStar();

	ChanneRelevance getDataType(String xz_name, String channel_code);

	// 限行
	List<ControlDrive> pressInControlDrive();

	// 历史上的今天
	List<TodayOnHistory> pressInTodayOnHistory();

	// 足球和篮球
	List<FootBasket> pressInFootBasket();

	// 足球篮球特殊处理
	List<ChanneRelevance> getDataType(String hostname, String guestname, String channel_code);

	// 某渠道下aid的数据类型
	List<DataType> pressInDataType(String channel_code);

	//黄历
	List<AlmanacZip> pressInAlmanacZip();

	//将压缩包链接存储到数据库
	void pressInZipUrl(String channel_code, String saveZipUrl, String alias_name, int tree_id);

}
