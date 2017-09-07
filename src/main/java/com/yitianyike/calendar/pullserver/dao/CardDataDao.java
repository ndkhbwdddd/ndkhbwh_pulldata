package com.yitianyike.calendar.pullserver.dao;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.Anchor;
import com.yitianyike.calendar.pullserver.model.responseCardData.AlmanacZip;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.DataType;
import com.yitianyike.calendar.pullserver.model.responseCardData.FestivalZip;
import com.yitianyike.calendar.pullserver.model.responseCardData.Film;
import com.yitianyike.calendar.pullserver.model.responseCardData.FootBasket;
import com.yitianyike.calendar.pullserver.model.responseCardData.Lottery;
import com.yitianyike.calendar.pullserver.model.responseCardData.News;
import com.yitianyike.calendar.pullserver.model.responseCardData.Picture;
import com.yitianyike.calendar.pullserver.model.responseCardData.Star;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;

public interface CardDataDao {

	List<Anchor> findAnchorData(Map<String, String> paramMap);

	List<Map<String, Object>> organizeBasketBalls(Map<String, String> paramMap);

	List<Map<String, Object>> organizeFootBalls(Map<String, String> paramMap);

	List<Map<String, Object>> flushSportCardData(String channel_code);

	List<Map<String, Object>> getBasketBalls(Map<String, String> paramMap);

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

	// 黄历
	List<AlmanacZip> pressInAlmanacZip();

	// 将压缩包链接存储到数据库
	void pressInZipUrl(String channel_code, String saveZipUrl, String alias_name, int tree_id);

	// 节日
	List<FestivalZip> pressInFestivalZip(String alias_name);

	// 资讯正向关联
	List<ChanneRelevance> getDataType(String channel_code);

	// 资讯
	List<News> pressInNews(String alias_name, int num_ber);

	// 某渠道下scene类型,刷新aid使用
	List<DataType> pressInSceneType(String channel_code);

	// 获取所有场景下的锚点
	List<ChanneRelevance> pressInAnchorsUnderScene(String tree_id);

	// 场景下历史上的今天
	List<TodayOnHistory> pressInTodayOnHistoryForScene(String m, String d, int show_number);

	// 场景下的体育
	List<FootBasket> pressInFootBasketForScene(int show_number, String alias_name, String date);

	// 场景下的黄历
	List<AlmanacZip> pressInAlmanacZipForScene(String date);

	// 节日正关联
	List<ChanneRelevance> getDataTypeForFestival(String channel_code);

	// 场景下的节日
	List<FestivalZip> pressInFestivalZipForScene(String alias_name, String date);

	// 彩票
	List<ChanneRelevance> getDataTypeForLottery(String channel_code);

	// 彩票数据
	List<Lottery> pressInLottery(String alias_name);

	// 场景下的彩票
	List<Lottery> pressInLotteryForScene(String alias_name, String date, int show_number);

	// 电影正向关联
	List<ChanneRelevance> getDataTypeForFilm(String channel_code);

	// 电影数据
	List<Film> pressInFilm(String alias_name, int show_number);

	// 场景下的电影
	List<Film> pressInFilmForScene(String alias_name, int show_number);

	// 图片正关联
	List<ChanneRelevance> getDataTypeForPicture(String channel_code);

	// 一天美图
	List<Picture> pressInPicture(String alias_name, String time);

	// 场景下的壹天美图
	List<Picture> pressInPictureForScene(String alias_name, String longTime);

	// 查询所有限行城市
	List<ControlDrive> pressInControlDriveCity();

	// 获取所有渠道
	List<String> getChannels();

	// 写入刷新日志
	void writLogs(String channel, String key, String value);

	// more历史上的今天
	List<TodayOnHistory> pressInMoreTodayOnHistory();

}
