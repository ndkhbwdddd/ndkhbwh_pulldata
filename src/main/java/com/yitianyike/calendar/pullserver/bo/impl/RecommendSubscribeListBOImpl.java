package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.yitianyike.calendar.pullserver.bo.DataCacheBO;
import com.yitianyike.calendar.pullserver.bo.RecommendSubscribeListBO;
import com.yitianyike.calendar.pullserver.dao.PackageDAO;
import com.yitianyike.calendar.pullserver.model.responsedata.BRecommend;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.MapUtil;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("recommendSubscribeListBO")
public class RecommendSubscribeListBOImpl implements RecommendSubscribeListBO {
	@Autowired
	private PackageDAO packageDAO;
	@Autowired
	private DataCacheBO dataCacheBO;// = (DataCacheBO) DataAccessFactory.dataHolder().get("dataCacheBO");
	@Override
	public String organizeRecommendSubscribeList(Map<String, String> paramMap) {

		List<Map<String, Object>> resposeList = new ArrayList<Map<String, Object>>();

		String channel_code = paramMap.get("channel_code");
		List<BRecommend> banners = packageDAO.getBannerList(channel_code);
		Map<Map<String, Object>, Integer> sortMap = new HashMap<Map<String, Object>, Integer>();

		for (BRecommend bRecommend : banners) {
			List<BRecommend> itemUnderBanner = packageDAO.getItemUnderBanner(bRecommend.getBanner_id());
			Map<String, Object> itemUnderBannerMap = new HashMap<String, Object>();
			itemUnderBannerMap.put("type", 0);
			List<Map<String, Object>> itemUnderBannerList = new ArrayList<Map<String, Object>>();
			for (BRecommend itemBRecommend : itemUnderBanner) {
				Map<String, Object> itemBRecommendMap = new HashMap<String, Object>();
				itemBRecommendMap.put("banner_url", itemBRecommend.getIcon_url());
				itemBRecommendMap.put("skip_url", itemBRecommend.getSkip_url());
				itemBRecommendMap.put("skip_type", itemBRecommend.getSkip_type());
				itemUnderBannerList.add(itemBRecommendMap);
			}
			itemUnderBannerMap.put("data", itemUnderBannerList);
			sortMap.put(itemUnderBannerMap, bRecommend.getTree_order());
		}

		// ============================================================================
		List<BRecommend> topics = packageDAO.getTopicList(channel_code);

		for (BRecommend bRecommend : topics) {
			List<BRecommend> itemUnderBanner = packageDAO.getItemUnderTopic(bRecommend.getAid(), channel_code);
			Map<String, Object> itemUnderBannerMap = new HashMap<String, Object>();
			itemUnderBannerMap.put("type", 1);
			List<Map<String, Object>> itemUnderBannerList = new ArrayList<Map<String, Object>>();
			for (BRecommend itemBRecommend : itemUnderBanner) {
				Map<String, Object> itemBRecommendMap = new HashMap<String, Object>();
				int data_type = itemBRecommend.getData_type();
				// 包
				if (data_type == 0) {
					List<Bcolumn> itemUnderTopicIsPackageBRecommends = packageDAO
							.getItemUnderTopicIsPackage(itemBRecommend.getStyle_id());
					if (!itemUnderTopicIsPackageBRecommends.isEmpty()) {
						Bcolumn bcolumn = itemUnderTopicIsPackageBRecommends.get(0);
						itemBRecommendMap.put("skip_action", 0);
						itemBRecommendMap.put("skip_url", "");
						itemBRecommendMap.put("package_name", bcolumn.getName());
						itemBRecommendMap.put("icon_url", bcolumn.getIcon());
						itemBRecommendMap.put("pid", itemBRecommend.getSource_id());
						itemUnderBannerList.add(itemBRecommendMap);
					}
					// 广告
				} else {
					List<Bcolumn> itemUnderTopicIsPackageBRecommends = packageDAO
							.getItemUnderTopicIsPackage(itemBRecommend.getStyle_id());
					if (!itemUnderTopicIsPackageBRecommends.isEmpty()) {
						Bcolumn bcolumn = itemUnderTopicIsPackageBRecommends.get(0);
						itemBRecommendMap.put("skip_action", 1);
						itemBRecommendMap.put("skip_url", bcolumn.getSkip_url());
						itemBRecommendMap.put("package_name", bcolumn.getName());
						itemBRecommendMap.put("icon_url", bcolumn.getIcon());
						itemBRecommendMap.put("pid", 0);
						itemUnderBannerList.add(itemBRecommendMap);
					}
				}

			}
			itemUnderBannerMap.put("data", itemUnderBannerList);
			sortMap.put(itemUnderBannerMap, bRecommend.getTree_order());
		}

		// =======================================================================
		List<BRecommend> classify = packageDAO.getClassifyList(channel_code);

		for (BRecommend bRecommend : classify) {
			List<Bcolumn> itemUnderBanner = packageDAO.getItemUnderClassifyAndValue(bRecommend.getAid(), channel_code);
			Map<String, Object> itemUnderBannerMap = new HashMap<String, Object>();
			itemUnderBannerMap.put("type", 2);
			itemUnderBannerMap.put("package_name", bRecommend.getPackage_name());
			List<Map<String, Object>> itemUnderBannerList = new ArrayList<Map<String, Object>>();
			for (Bcolumn bcolumn : itemUnderBanner) {
				Map<String, Object> itemBRecommendMap = new HashMap<String, Object>();

				itemBRecommendMap.put("icon", bcolumn.getIcon());
				itemBRecommendMap.put("name", bcolumn.getName());
				itemBRecommendMap.put("aid", bcolumn.getTree_id());
				itemUnderBannerList.add(itemBRecommendMap);
			}
			itemUnderBannerMap.put("data", itemUnderBannerList);
			sortMap.put(itemUnderBannerMap, bRecommend.getTree_order());
		}

		Map<Map<String, Object>, Integer> sortByValue = MapUtil.sortByValue(sortMap);
		Set<Map<String, Object>> keySet = sortByValue.keySet();
		for (Map<String, Object> map : keySet) {
			resposeList.add(map);
		}
		// String jsonString = JSONArray.toJSONString(resposeList);
		// System.out.println(jsonString);

		
		String recommendSubscribeListJson = JSONArray.toJSONString(resposeList);
		
		StringBuilder deleteSbParam = new StringBuilder();
		deleteSbParam.append(paramMap.get("channel_code")).append("-").append(PropertiesUtil.version)
				.append("-recommend");
		dataCacheBO.insertRecommendSubscribeList(deleteSbParam.toString(), recommendSubscribeListJson);
		
		return recommendSubscribeListJson;
		// } else {
		// List<Map<String, Object>> recommendSubscribeList = new
		// ArrayList<Map<String, Object>>();
		// organizeRecommend1(recommendSubscribeList, paramMap);
		// return JSONArray.fromObject(recommendSubscribeList).toString();
	}

}
