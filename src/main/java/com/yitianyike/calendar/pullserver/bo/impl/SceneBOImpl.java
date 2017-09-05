package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.NewsBO;
import com.yitianyike.calendar.pullserver.bo.SceneBO;
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
import com.yitianyike.calendar.pullserver.model.responseCardData.FestivalZip;
import com.yitianyike.calendar.pullserver.model.responseCardData.Film;
import com.yitianyike.calendar.pullserver.model.responseCardData.FootBasket;
import com.yitianyike.calendar.pullserver.model.responseCardData.Lottery;
import com.yitianyike.calendar.pullserver.model.responseCardData.News;
import com.yitianyike.calendar.pullserver.model.responseCardData.Picture;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.DateUtil;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("sceneBO")
public class SceneBOImpl implements SceneBO {

	@Autowired
	private CardDataDao cardDataDao;
	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	@Override
	public List<DataCache> pressInScene(Map<String, String> parmMap) {

		String channel_code = parmMap.get("channel_code");
		List<DataType> sceneTreeIds = cardDataDao.pressInSceneType(channel_code);
		List<DataCache> saveCacheList = new ArrayList<DataCache>();
		// 所有场景
		for (DataType dataType : sceneTreeIds) {
			String tree_id = dataType.getTree_id();
			String tree_name = dataType.getTree_name();
			// 存入key,field
			StringBuffer sb = new StringBuffer();
			sb.append(channel_code).append("-").append(PropertiesUtil.version).append("-").append(tree_id);

			//
			String saveKey = sb.toString();

			// 所有场景下的锚点
			List<ChanneRelevance> channeRelevances = cardDataDao.pressInAnchorsUnderScene(tree_id);

			// 缓存前后10天的场景数据
			List<String> dates = DateUtil.findStringDates();
			for (String date : dates) {
				Map<String, Object> savaJsonMap = new HashMap<String, Object>();
				// 每个场景每天的存储
				List<Map<String, Object>> savaJsonList = new ArrayList<Map<String, Object>>();

				DataCache dc = new DataCache();
				dc.setKey(saveKey);
				dc.setField(date.replace("-", ""));
				// 存入value
				for (ChanneRelevance channeRelevance : channeRelevances) {
					int data_style = channeRelevance.getData_style();
					int data_type = channeRelevance.getData_type();
					int show_number = channeRelevance.getShow_number();
					String alias_name = channeRelevance.getAlias_name();
					switch (data_type) {

					case 3: {// 历史上的今天

						// String y = date.substring(0, 4);
						String m = date.substring(5, 7);
						String d = date.substring(8, 10);

						pushInTodayOnHistoryToList(data_style, show_number, m, d, savaJsonList);
					}
						break;
					case 4: {// 体育
						pushInFootBasketToList(data_style, show_number, alias_name, date, savaJsonList);
					}
						break;
					case 5: {// 资讯
						pushInNewsToList(data_style, show_number, alias_name, savaJsonList);
					}
						break;
					case 6: {// 黄历
						pushInAlmanacToList(data_style, date, savaJsonList);
					}
						break;
					case 7: {// 节日
						pushInFestivalToList(data_style, show_number, alias_name, date, savaJsonList);
					}
						break;
					case 8: {// 电影
						pushInFilmsToList(data_style, show_number, alias_name, savaJsonList);
					}
						break;
					case 9: {// 彩票
						pushInLotteryToList(data_style, show_number, alias_name, date, savaJsonList);
					}
						break;
					case 10: {// 壹天美图
						pushInPictureToList(data_style, show_number, alias_name, date, savaJsonList);
					}
						break;
					default:
						break;
					}
				}
				savaJsonMap.put("unique_type", 1);
				savaJsonMap.put("title", tree_name);
				savaJsonMap.put("datas", savaJsonList);
				dc.setValue(JSONObject.toJSONString(savaJsonMap));
				saveCacheList.add(dc);

			}

		}

		int[] insertDataCacheList = dataCacheDAO.insertDataCacheList(saveCacheList);
		redisDAO.updateRedis(saveCacheList);

		return saveCacheList;
	}

	private void pushInPictureToList(int data_style, int show_number, String alias_name, String date,
			List<Map<String, Object>> savaJsonList) {
		String longTime = DateUtil.getLongTime(date);
		List<Picture> pictures = cardDataDao.pressInPictureForScene(alias_name, longTime);
		if (pictures.size() >= 3) {
			Picture picture = pictures.get(0);
			Picture picture2 = pictures.get(1);
			Picture picture3 = pictures.get(2);
			Map<String, Object> savaListMap = new HashMap<String, Object>();
			savaListMap.put("data_type", data_style);
			savaListMap.put("unique_type", 0);
			savaListMap.put("p1", picture.getSnapshot());
			savaListMap.put("p2", picture2.getSnapshot());
			savaListMap.put("p3", picture3.getSnapshot());
			savaListMap.put("c1", picture.getTitle());
			savaListMap.put("c2", picture2.getTitle());
			savaListMap.put("c3", picture3.getTitle());
			savaListMap.put("u1", picture.getSkip_url());
			savaListMap.put("u2", picture2.getSkip_url());
			savaListMap.put("u3", picture3.getSkip_url());
			savaJsonList.add(savaListMap);

		}

	}

	private void pushInFilmsToList(int data_style, int show_number, String alias_name,
			List<Map<String, Object>> savaJsonList) {
		List<Film> films = cardDataDao.pressInFilmForScene(alias_name, show_number);

		for (Film film : films) {
			if (31 == data_style) {// c1,c2,c3,p1,u1
				Map<String, Object> savaListMap = new HashMap<String, Object>();
				savaListMap.put("data_type", data_style);
				savaListMap.put("p1", film.getBanner());
				savaListMap.put("c1", "电影");
				savaListMap.put("c2", film.getTv_title());
				savaListMap.put("c3", film.getStory_brief().length() <= 10 ? film.getStory_brief()
						: film.getStory_brief().substring(0, 9));
				savaListMap.put("u1", film.getLink_url());
				savaJsonList.add(savaListMap);
			} else if (36 == data_style) {// c1,c2,p1,u1
				Map<String, Object> savaListMap = new HashMap<String, Object>();
				savaListMap.put("data_type", data_style);
				savaListMap.put("p1", film.getBanner());
				savaListMap.put("c1", film.getTv_title());
				savaListMap.put("c2", film.getStory_brief().length() <= 10 ? film.getStory_brief()
						: film.getStory_brief().substring(0, 9));
				savaListMap.put("u1", film.getLink_url());
				savaJsonList.add(savaListMap);
			} else if (38 == data_style) {// c1,p1,u1
				Map<String, Object> savaListMap = new HashMap<String, Object>();
				savaListMap.put("data_type", data_style);
				savaListMap.put("p1", film.getBanner());
				savaListMap.put("c1", film.getTv_title());
				savaListMap.put("u1", film.getLink_url());
				savaJsonList.add(savaListMap);
			} else if (7 == data_style) {// c1,c2,u1
				Map<String, Object> savaListMap = new HashMap<String, Object>();
				savaListMap.put("data_type", data_style);
				savaListMap.put("c1", film.getTv_title());
				savaListMap.put("c2", film.getStar());
				savaListMap.put("u1", film.getLink_url());
				savaJsonList.add(savaListMap);
			} else if (33 == data_style) {// c1,c2,p1,u1
				Map<String, Object> savaListMap = new HashMap<String, Object>();
				savaListMap.put("data_type", data_style);
				savaListMap.put("c1", film.getPlay_date());
				savaListMap.put("c2", film.getTv_title());
				savaListMap.put("p1", film.getIcon_address());
				savaListMap.put("u1", film.getLink_url());
				savaJsonList.add(savaListMap);
			} else if (34 == data_style) {// c1,c2,c3,p1,u1
				Map<String, Object> savaListMap = new HashMap<String, Object>();
				savaListMap.put("data_type", data_style);
				savaListMap.put("c1", film.getTv_title());
				savaListMap.put("c2", "演员:" + film.getStar());
				savaListMap.put("c3", film.getPlay_date());
				savaListMap.put("p1", film.getIcon_address());
				savaListMap.put("u1", film.getLink_url());
				savaJsonList.add(savaListMap);
			} else if (35 == data_style) {// c1,c2,c3,c4,c5,p1,u1
				Map<String, Object> savaListMap = new HashMap<String, Object>();
				savaListMap.put("data_type", data_style);
				savaListMap.put("c1", film.getTv_title());
				savaListMap.put("c2", "演员:" + film.getStar());
				savaListMap.put("c3", "类型:" + film.getType());
				savaListMap.put("c4", "地区:中国");
				savaListMap.put("c5", "上映时间:" + film.getPlay_date());
				savaListMap.put("p1", film.getIcon_address());
				savaListMap.put("u1", film.getLink_url());
				savaJsonList.add(savaListMap);
			}
		}

	}

	private void pushInLotteryToList(int data_style, int show_number, String alias_name, String date,
			List<Map<String, Object>> savaJsonList) {
		List<Lottery> lotterys = cardDataDao.pressInLotteryForScene(alias_name, date, show_number);

		for (Lottery lottery : lotterys) {
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("data_type", data_style);
			responseData.put("skip_url", lottery.getSkip_url());
			responseData.put("name", lottery.getName());
			responseData.put("open", lottery.getOpen());
			responseData.put("color", lottery.getColor());
			responseData.put("detail", lottery.getDetail());
			responseData.put("phase", lottery.getPhase());
			;
			savaJsonList.add(responseData);
		}

	}

	// 资讯
	private void pushInNewsToList(int data_style, int show_number, String alias_name,
			List<Map<String, Object>> savaJsonList) {

		List<News> newss = cardDataDao.pressInNews(alias_name, show_number);
		if (!newss.isEmpty()) {
			if (data_style == 1 || data_style == 2 || data_style == 26) {// p1,c1,c2,u1(icon)
				for (News news : newss) {
					Map<String, Object> savaListMap = new HashMap<String, Object>();
					savaListMap.put("data_type", data_style);
					savaListMap.put("p1", news.getImg_url());
					savaListMap.put("c1", news.getTitle());
					savaListMap.put("c2", news.getInstro());
					savaListMap.put("u1", news.getSource_url());
					savaJsonList.add(savaListMap);
				}

			} else if (data_style == 3 || data_style == 25) {// c1,p1,p2,p3,u1

				for (News news : newss) {
					Map<String, Object> savaListMap = new HashMap<String, Object>();
					savaListMap.put("data_type", data_style);
					savaListMap.put("c1", news.getTitle());
					savaListMap.put("u1", news.getSource_url());
					String pic_list = news.getPic_list();
					if (StringUtils.isBlank(pic_list)) {
						continue;
					}
					JSONArray parseArray = JSONArray.parseArray(pic_list);
					if (parseArray.size() < 3) {
						continue;
					}
					String p1 = parseArray.getJSONObject(0).getString("img_url");
					String p2 = parseArray.getJSONObject(1).getString("img_url");
					String p3 = parseArray.getJSONObject(2).getString("img_url");
					savaListMap.put("p1", p1);
					savaListMap.put("p2", p2);
					savaListMap.put("p3", p3);
					savaJsonList.add(savaListMap);
				}
			}
		} else if (data_style == 4) {// c1,p1,p2,u1

			if (!newss.isEmpty()) {

				for (News news : newss) {
					Map<String, Object> savaListMap = new HashMap<String, Object>();
					savaListMap.put("data_type", data_style);
					savaListMap.put("c1", news.getTitle());
					savaListMap.put("u1", news.getSource_url());
					String pic_list = news.getPic_list();
					if (StringUtils.isBlank(pic_list)) {
						continue;
					}
					JSONArray parseArray = JSONArray.parseArray(pic_list);
					if (parseArray.size() < 2) {
						continue;
					}
					String p1 = parseArray.getJSONObject(0).getString("img_url");
					String p2 = parseArray.getJSONObject(1).getString("img_url");
					savaListMap.put("p1", p1);
					savaListMap.put("p2", p2);
					savaJsonList.add(savaListMap);
				}
			}

		} else if (data_style == 6 || data_style == 5 || data_style == 9 || data_style == 12 || data_style == 13
				|| data_style == 38) {// c1,p1,u1
			// 9是推广可删除,图片为banner
			for (News news : newss) {
				Map<String, Object> savaListMap = new HashMap<String, Object>();
				savaListMap.put("data_type", data_style);
				savaListMap.put("p1", news.getPic_url());
				savaListMap.put("c1", news.getTitle());
				savaListMap.put("u1", news.getSource_url());

				savaJsonList.add(savaListMap);
			}

		} else if (data_style == 36) {// c1,c2,p1,u1 (banner)
			for (News news : newss) {
				Map<String, Object> savaListMap = new HashMap<String, Object>();
				savaListMap.put("data_type", data_style);
				savaListMap.put("p1", news.getPic_url());
				savaListMap.put("c1", news.getTitle());
				savaListMap.put("c1", news.getInstro());
				savaListMap.put("u1", news.getSource_url());
				savaJsonList.add(savaListMap);
			}

		} else if (data_style == 7) {// c1,u1

			for (News news : newss) {
				Map<String, Object> savaListMap = new HashMap<String, Object>();
				savaListMap.put("data_type", data_style);
				savaListMap.put("c1", news.getTitle());
				savaListMap.put("u1", news.getSource_url());
				savaJsonList.add(savaListMap);
			}

		} else if (data_style == 8 || data_style == 28) {// p1,u1

			for (News news : newss) {
				Map<String, Object> savaListMap = new HashMap<String, Object>();
				savaListMap.put("data_type", data_style);
				savaListMap.put("p1", news.getPic_url());
				savaListMap.put("u1", news.getSource_url());
				savaJsonList.add(savaListMap);
			}

		} else if (data_style == 30 || data_style == 39) {// 2
															// c1,c2,p1,p2,u1,u2

			Map<Integer, List<News>> subMap = new HashMap<Integer, List<News>>();

			if (!newss.isEmpty() && newss.size() >= 2) {
				// 分组
				int in = 0;
				for (int i = 0; i < newss.size() / 2; i++) {
					List<News> subList = newss.subList(in, in += 2);
					subMap.put(i, subList);
				}

				Collection<List<News>> values = subMap.values();
				for (List<News> list : values) {
					Map<String, Object> savaListMap = new HashMap<String, Object>();
					News news1 = list.get(0);
					News news2 = list.get(1);
					savaListMap.put("data_type", data_style);
					savaListMap.put("p1", news1.getImg_url());
					savaListMap.put("p2", news2.getImg_url());
					savaListMap.put("c1", news1.getTitle());
					savaListMap.put("c2", news2.getTitle());
					savaListMap.put("u1", news1.getSource_url());
					savaListMap.put("u2", news2.getSource_url());
					savaJsonList.add(savaListMap);
				}
			}

		} else if (data_style == 18) {// 3
										// c1,c2,p1,u1,u2,u3(banner)

			Map<Integer, List<News>> subMap = new HashMap<Integer, List<News>>();

			if (newss.size() >= 3) {

				// 分组
				int in = 0;
				for (int i = 0; i < newss.size() / 3; i++) {
					List<News> subList = newss.subList(in, in += 3);
					subMap.put(i, subList);
				}

				Collection<List<News>> values = subMap.values();
				for (List<News> list : values) {
					Map<String, Object> savaListMap = new HashMap<String, Object>();
					News news1 = list.get(0);
					News news2 = list.get(1);
					News news3 = list.get(2);
					savaListMap.put("data_type", data_style);
					savaListMap.put("c1", news2.getTitle());
					savaListMap.put("c2", news3.getTitle());
					savaListMap.put("p1", news1.getPic_url());

					savaListMap.put("u1", news1.getSource_url());
					savaListMap.put("u2", news2.getSource_url());
					savaListMap.put("u3", news3.getSource_url());
					savaJsonList.add(savaListMap);
				}
			}
		} else if (data_style == 29) {// 3
										// c1,c2,c3,c4,u1,u2,u3

			Map<Integer, List<News>> subMap = new HashMap<Integer, List<News>>();

			if (newss.size() >= 3) {

				// 分组
				int in = 0;
				for (int i = 0; i < newss.size() / 3; i++) {
					List<News> subList = newss.subList(in, in += 3);
					subMap.put(i, subList);
				}

				Collection<List<News>> values = subMap.values();
				for (List<News> list : values) {
					Map<String, Object> savaListMap = new HashMap<String, Object>();

					News news1 = list.get(0);
					News news2 = list.get(1);
					News news3 = list.get(2);
					savaListMap.put("data_type", data_style);
					savaListMap.put("c1", "集锦");
					savaListMap.put("c2", news1.getTitle());
					savaListMap.put("c3", news2.getTitle());
					savaListMap.put("c4", news3.getTitle());

					savaListMap.put("u1", news1.getSource_url());
					savaListMap.put("u2", news2.getSource_url());
					savaListMap.put("u3", news3.getSource_url());
					savaJsonList.add(savaListMap);

				}

			}

		}

	}

	// 节日
	private void pushInFestivalToList(int data_style, int show_number, String alias_name, String date,
			List<Map<String, Object>> savaJsonList) {
		List<FestivalZip> azs = cardDataDao.pressInFestivalZipForScene(alias_name, date);
		for (FestivalZip fz : azs) {
			Map<String, Object> azsmap = new HashMap<String, Object>();
			azsmap.put("data_type", data_style);
			azsmap.put("zh_name", fz.getZh_name());
			azsmap.put("icon", fz.getIcon());
			azsmap.put("source", fz.getSource());
			azsmap.put("date", fz.getDate());
			azsmap.put("banner_url", fz.getBanner_url());
			azsmap.put("country_id", fz.getCountry_id());
			azsmap.put("skip_url", fz.getSkip_url());
			savaJsonList.add(azsmap);
		}
	}

	// 黄历
	private void pushInAlmanacToList(int data_style, String date, List<Map<String, Object>> savaJsonList) {
		List<AlmanacZip> azs = cardDataDao.pressInAlmanacZipForScene(date);
		for (AlmanacZip az : azs) {
			Map<String, Object> azsmap = new HashMap<String, Object>();
			azsmap.put("data_type", data_style);
			azsmap.put("lunar_year", az.getLunar_year());
			azsmap.put("animals_year", az.getAnimals_year());
			azsmap.put("avoid", az.getAvoid());
			azsmap.put("suit", az.getSuit());
			azsmap.put("lunar", az.getLunar());
			azsmap.put("date", az.getDate());
			azsmap.put("skip_url", az.getSkip_url());
			savaJsonList.add(azsmap);
		}
	}

	// 体育
	private void pushInFootBasketToList(int data_style, int show_number, String alias_name, String date,
			List<Map<String, Object>> savaJsonList) {
		List<FootBasket> ths = cardDataDao.pressInFootBasketForScene(show_number, alias_name, date);
		for (FootBasket footBasket : ths) {
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("data_type", data_style);
			responseData.put("rnd", footBasket.getRnd());
			responseData.put("hostscore", footBasket.getHostscore());
			responseData.put("hostname", footBasket.getHostname());
			responseData.put("hostflag", footBasket.getHostflag());
			responseData.put("guestname", footBasket.getGuestname());
			responseData.put("guestscore", footBasket.getGuestscore());
			responseData.put("guestflag", footBasket.getGuestflag());
			responseData.put("matchurl", footBasket.getMatchurl());
			responseData.put("status", footBasket.getStatus());
			responseData.put("date", footBasket.getDate());
			responseData.put("time", footBasket.getTime());
			responseData.put("package_title", footBasket.getPackage_title());
			savaJsonList.add(responseData);
		}

	}

	// 历史上的今天
	private void pushInTodayOnHistoryToList(int data_style, int show_number, String m, String d,
			List<Map<String, Object>> savaJsonList) {
		List<TodayOnHistory> ths = cardDataDao.pressInTodayOnHistoryForScene(m, d, show_number);

		for (TodayOnHistory todayOnHistory : ths) {
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("data_type", data_style);
			responseData.put("c2", todayOnHistory.getDes());// des
			responseData.put("p1", todayOnHistory.getPic());// pic
			responseData.put("c1", todayOnHistory.getTitle());// title
			responseData.put("skip_url", todayOnHistory.getSkip_url());
			savaJsonList.add(responseData);
		}

	}

}
