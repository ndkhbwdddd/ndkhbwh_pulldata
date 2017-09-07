package com.yitianyike.calendar.pullserver.task;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.yitianyike.calendar.pullserver.bo.AidTypeBO;
import com.yitianyike.calendar.pullserver.bo.AlmanacZipBO;
import com.yitianyike.calendar.pullserver.bo.CompleteBO;
import com.yitianyike.calendar.pullserver.bo.ControlDriveBO;
import com.yitianyike.calendar.pullserver.bo.FestivalZipBO;
import com.yitianyike.calendar.pullserver.bo.FilmBO;
import com.yitianyike.calendar.pullserver.bo.FootBasketBO;
import com.yitianyike.calendar.pullserver.bo.LogBO;
import com.yitianyike.calendar.pullserver.bo.LotteryBO;
import com.yitianyike.calendar.pullserver.bo.NewsBO;
import com.yitianyike.calendar.pullserver.bo.PictureBO;
import com.yitianyike.calendar.pullserver.bo.RecommendSubscribeListBO;
import com.yitianyike.calendar.pullserver.bo.SceneBO;
import com.yitianyike.calendar.pullserver.bo.StarBO;
import com.yitianyike.calendar.pullserver.bo.SubscribedListBO;
import com.yitianyike.calendar.pullserver.bo.TabsListBO;
import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("task")
public class CardFlushTask {

	@Autowired
	private SubscribedListBO subscribedListBO;
	@Autowired
	private RecommendSubscribeListBO recommendSubscribeListBO;
	@Autowired
	private TabsListBO tabsListBO;
	@Autowired
	private CompleteBO completeBO;
	@Autowired
	private AidTypeBO aidTypeBO;
	@Autowired
	private StarBO starBO;
	@Autowired
	private ControlDriveBO controlDriveBO;
	@Autowired
	private TodayOnHistoryBO todayOnHistoryBO;
	@Autowired
	private FootBasketBO footBasketBO;
	@Autowired
	private AlmanacZipBO almanacZipBO;
	@Autowired
	private FestivalZipBO festivalZipBO;
	@Autowired
	private NewsBO newsBO;
	@Autowired
	private SceneBO sceneBO;
	@Autowired
	private LotteryBO lotteryBO;
	@Autowired
	private FilmBO filmBO;
	@Autowired
	private PictureBO pictureBO;

	@Autowired
	private LogBO logBO;

	// 已订阅列表
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushSportCardData() {
		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);
			String reString = "";
			try {
				parmMap.put("version", PropertiesUtil.version);
				List<Bcolumn> subscribedList = subscribedListBO.subscribedList(parmMap);

				for (Bcolumn bcolumn : subscribedList) {
					reString += bcolumn.getSave_value() + ",";
				}
				if (reString.length() > 0) {
					String substring = reString.substring(0, reString.length() - 1);
					reString = "[" + substring + "]";
				}

				logBO.writLogs(channel, "已订阅列表", reString);
			} catch (Exception e) {
				logBO.writLogs(channel, "已订阅列表==报错", e.getMessage());
				continue;
			}
		}

	}

	// tabsList列表
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushTabs() {
		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);
			String tabs = "";
			try {
				parmMap.put("version", PropertiesUtil.version);
				tabs = tabsListBO.tabsList(parmMap);
				logBO.writLogs(channel, "tabs列表", tabs);
			} catch (Exception e) {
				logBO.writLogs(channel, "tabs列表==报错", e.getMessage());
				continue;
			}
		}

	}

	// 热门
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushRecommendSubscribe() {
		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);
			String recommendSubscribeListJson = "";
			try {
				recommendSubscribeListJson = recommendSubscribeListBO.organizeRecommendSubscribeList(parmMap);

				logBO.writLogs(channel, "热门列表", recommendSubscribeListJson);
			} catch (Exception e) {
				logBO.writLogs(channel, "热门列表==报错", e.getMessage());
				continue;
			}
		}
	}

	// 全部
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushComplete() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);
			String completeString = "";
			try {
				List<Map<String, Object>> complete = completeBO.getComplete(parmMap.get("channel_code"));
				completeString = JSONArray.toJSONString(complete);

				logBO.writLogs(channel, "全部列表", completeString);
			} catch (Exception e) {
				logBO.writLogs(channel, "全部列表==报错", e.getMessage());
				continue;
			}
		}
	}

	// aidType
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushAidType() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);
			String aids = "";
			try {
				List<DataCache> pressInAid = aidTypeBO.pressInAidType(parmMap);
				// 显示返回
				aids = JSONArray.toJSONString(pressInAid);
				logBO.writLogs(channel, "aidtype列表", aids);
			} catch (Exception e) {
				logBO.writLogs(channel, "aidtype==报错", e.getMessage());
				continue;
			}

		}
	}

	// 星座
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushStar() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);
			String completeString = "";
			try {
				List<DataCache> pressInStar = starBO.pressInStar(parmMap);

				// 显示返回
				List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

				for (DataCache dc : pressInStar) {
					Map<String, String> rMap = new HashMap<String, String>();
					rMap.put("key", dc.getKey());
					rMap.put("field", dc.getField());
					rMap.put("value", dc.getValue());
					responseDatas.add(rMap);
				}
				completeString = JSONArray.toJSONString(responseDatas);
				logBO.writLogs(channel, "星座", completeString);
			} catch (Exception e) {
				logBO.writLogs(channel, "星座==报错", e.getMessage());
				continue;
			}

		}
	}

	// 限行
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushControlDrive() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);
			String completeString = "";
			try {
				List<DataCache> pressInControlDrive = controlDriveBO.pressInControlDrive(parmMap);
				// 显示返回
				List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

				for (DataCache dc : pressInControlDrive) {
					Map<String, String> rMap = new HashMap<String, String>();
					rMap.put("key", dc.getKey());
					rMap.put("field", dc.getField());
					rMap.put("value", dc.getValue());
					responseDatas.add(rMap);
				}
				completeString = JSONArray.toJSONString(responseDatas);
				logBO.writLogs(channel, "限行", completeString);
			} catch (Exception e) {
				logBO.writLogs(channel, "限行==报错", e.getMessage());
				continue;
			}

		}
	}

	// 历史上的今天
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushTodayOnHistory() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);
			String completeString = "";
			try {
				List<DataCache> pressInTodayOnHistory = todayOnHistoryBO.pressInTodayOnHistory(parmMap);
				// 显示返回
				List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

				for (DataCache dc : pressInTodayOnHistory) {
					Map<String, String> rMap = new HashMap<String, String>();
					rMap.put("key", dc.getKey());
					rMap.put("field", dc.getField());
					rMap.put("value", dc.getValue());
					responseDatas.add(rMap);
				}
				completeString = JSONArray.toJSONString(responseDatas);
				logBO.writLogs(channel, "历史上的今天", completeString);
			} catch (Exception e) {
				logBO.writLogs(channel, "历史上的今天==报错", e.getMessage());
				continue;
			}

		}
	}

	// 足篮球
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushFootBasket() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);

			try {
				List<DataCache> pressInTodayOnHistory = footBasketBO.pressInFootBasket(parmMap);
				// 显示返回
				List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

				for (DataCache dc : pressInTodayOnHistory) {
					Map<String, String> rMap = new HashMap<String, String>();
					rMap.put("key", dc.getKey());
					rMap.put("field", dc.getField());
					rMap.put("value", dc.getValue());
					responseDatas.add(rMap);
				}
				String completeString = JSONArray.toJSONString(responseDatas);
				logBO.writLogs(channel, "足球和篮球", completeString);
			} catch (Exception e) {
				logBO.writLogs(channel, "足球和篮球==报错", e.getMessage());
				continue;
			}

		}
	}

	// 日历
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushAlmanacZip() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);
			try {

				List<DataCache> pressInTodayOnHistory = almanacZipBO.pressInAlmanacZip(parmMap);
				Map<String, Object> response = new HashMap<String, Object>();

				// 显示返回
				List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

				for (DataCache dc : pressInTodayOnHistory) {
					Map<String, String> rMap = new HashMap<String, String>();
					rMap.put("key", dc.getKey());
					rMap.put("field", dc.getField());
					rMap.put("value", dc.getValue());
					responseDatas.add(rMap);
				}
				response.put("mes", "success");
				response.put("code", 0);
				response.put("data", responseDatas);
				String completeString = JSONArray.toJSONString(responseDatas);
				logBO.writLogs(channel, "日历", completeString);
			} catch (Exception e) {
				logBO.writLogs(channel, "日历==报错", e.getMessage());
				continue;
			}

		}
	}

	// 节日
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushFestivalZip() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);

			try {
				List<DataCache> pressInTodayOnHistory = festivalZipBO.pressInFestivalZip(parmMap);

				// 显示返回
				List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

				for (DataCache dc : pressInTodayOnHistory) {
					Map<String, String> rMap = new HashMap<String, String>();
					rMap.put("key", dc.getKey());
					rMap.put("field", dc.getField());
					rMap.put("value", dc.getValue());
					responseDatas.add(rMap);
				}

				String completeString = JSONArray.toJSONString(responseDatas);
				logBO.writLogs(channel, "节日", completeString);
			} catch (Exception e) {
				logBO.writLogs(channel, "节日==报错", e.getMessage());
				continue;
			}

		}
	}

	// 新闻
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushNews() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);

			try {
				List<DataCache> pressInTodayOnHistory = newsBO.pressInNews(parmMap);

				// 显示返回
				List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

				for (DataCache dc : pressInTodayOnHistory) {
					Map<String, String> rMap = new HashMap<String, String>();
					rMap.put("key", dc.getKey());
					rMap.put("field", dc.getField());
					rMap.put("value", dc.getValue());
					responseDatas.add(rMap);
				}
				String completeString = JSONArray.toJSONString(responseDatas);
				logBO.writLogs(channel, "新闻", completeString);
			} catch (Exception e) {
				logBO.writLogs(channel, "新闻==报错", e.getMessage());
				continue;
			}

		}
	}

	// 场景
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushScene() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);

			try {
				List<DataCache> pressInTodayOnHistory = sceneBO.pressInScene(parmMap);

				// 显示返回
				List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

				for (DataCache dc : pressInTodayOnHistory) {
					Map<String, String> rMap = new HashMap<String, String>();
					rMap.put("key", dc.getKey());
					rMap.put("field", dc.getField());
					rMap.put("value", dc.getValue());
					responseDatas.add(rMap);
				}
				String completeString = JSONArray.toJSONString(responseDatas);
				logBO.writLogs(channel, "场景", completeString);
			} catch (Exception e) {
				logBO.writLogs(channel, "场景==报错", e.getMessage());
				continue;
			}
		}
	}

	// 彩票
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushLottery() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);
			try {
				List<DataCache> pressInTodayOnHistory = lotteryBO.pressInLottery(parmMap);

				// 显示返回
				List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

				for (DataCache dc : pressInTodayOnHistory) {
					Map<String, String> rMap = new HashMap<String, String>();
					rMap.put("key", dc.getKey());
					rMap.put("field", dc.getField());
					rMap.put("value", dc.getValue());
					responseDatas.add(rMap);
				}
				String completeString = JSONArray.toJSONString(responseDatas);
				logBO.writLogs(channel, "彩票", completeString);
			} catch (Exception e) {
				logBO.writLogs(channel, "彩票==报错", e.getMessage());
				continue;
			}

		}
	}

	// 电影
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushFilm() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);

			try {
				List<DataCache> pressInTodayOnHistory = filmBO.pressInFilm(parmMap);

				// 显示返回
				List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

				for (DataCache dc : pressInTodayOnHistory) {
					Map<String, String> rMap = new HashMap<String, String>();
					rMap.put("key", dc.getKey());
					rMap.put("field", dc.getField());
					rMap.put("value", dc.getValue());
					responseDatas.add(rMap);
				}
				String completeString = JSONArray.toJSONString(responseDatas);
				logBO.writLogs(channel, "电影", completeString);
			} catch (Exception e) {
				logBO.writLogs(channel, "电影==报错", e.getMessage());
				continue;
			}

		}
	}

	// 壹天美图
	@Scheduled(cron = "0 0/30 * * * ?")
	public void flushPicture() {

		List<String> channels = logBO.getChannels();
		for (String channel : channels) {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("channel_code", channel);

			try {

				List<DataCache> pressInTodayOnHistory = pictureBO.pressInPicture(parmMap);
				// 显示返回
				List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

				for (DataCache dc : pressInTodayOnHistory) {
					Map<String, String> rMap = new HashMap<String, String>();
					rMap.put("key", dc.getKey());
					rMap.put("field", dc.getField());
					rMap.put("value", dc.getValue());
					responseDatas.add(rMap);
				}
				String completeString = JSONArray.toJSONString(responseDatas);
				logBO.writLogs(channel, "壹天美图", completeString);
			} catch (Exception e) {
				logBO.writLogs(channel, "壹天美图==报错", e.getMessage());
				continue;
			}

		}

	}
}
