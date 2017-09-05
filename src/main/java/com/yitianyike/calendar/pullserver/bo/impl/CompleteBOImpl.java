package com.yitianyike.calendar.pullserver.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.bo.CompleteBO;
import com.yitianyike.calendar.pullserver.dao.ColumnDAO;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.PackageDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.Column;
import com.yitianyike.calendar.pullserver.model.PackageInfo;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

@Component("completeBO")
public class CompleteBOImpl implements CompleteBO {

	// private static Logger logger =
	// Logger.getLogger(CompleteBOImpl.class.getName());

	// private List<Map<String , Object>> reslist = new
	// ArrayList<Map<String,Object>>();

	@Autowired
	private PackageDAO packageDAO;
	@Autowired
	private ColumnDAO columnDAO;

	private DataCacheDAO dataCacheDAO = (DataCacheDAO) DataAccessFactory.dataHolder().get("dataCacheDAO");
	private RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");

	// private String channelCode;
	// private String version;
	private int tabs = 1;

	@Override
	public List<Map<String, Object>> getComplete(String channel_code) {

		List<Map<String, Object>> reslist = new ArrayList<Map<String, Object>>();

		String version = PropertiesUtil.version;

		List<Bcolumn> columns = packageDAO.getBeginPackage(tabs, channel_code);

		organizationBeginPackage(columns, reslist);

		for (Bcolumn bcolumn : columns) {
			organizationRecursion(bcolumn, reslist, channel_code);
		}

		JSONArray fromObject = JSONArray.fromObject(reslist);
		String cacheKey = channel_code + "-" + version + "-complete";
		dataCacheDAO.insertRecommendSubscribeList(cacheKey, fromObject.toString());
		redisDAO.removeRedisByCacheKey(cacheKey);
		return reslist;
	}

	private void organizationRecursion(Bcolumn bcolumn, List<Map<String, Object>> reslist, String channel_code) {
		List<Bcolumn> columnsItem = packageDAO.getItemPackage(bcolumn.getTree_id(), channel_code);
		Map<String, Object> iteamMap = new HashMap<String, Object>();
		iteamMap.put("pid", bcolumn.getTree_id());
		iteamMap.put("name", bcolumn.getName());

		List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
		for (Bcolumn bcolumnChild : columnsItem) {
			Map<String, Object> bcolumnMap = new HashMap<String, Object>();
			Map<String, Object> listAction = new HashMap<String, Object>();
			Map<String, Object> preference = new HashMap<String, Object>();
			if (bcolumnChild.getData_type() == 0) {
				bcolumnMap.put("aid", -1);
				bcolumnMap.put("pid", bcolumnChild.getTree_id());
				listAction.put("skip_action", 0);
				listAction.put("skip_url", "");

			} else {
				bcolumnMap.put("aid", bcolumnChild.getTree_id());
				bcolumnMap.put("pid", bcolumn.getTree_id());
				listAction.put("skip_action", bcolumnChild.getSkip_action());
				listAction.put("skip_url", bcolumnChild.getSkip_url());
			}

			preference.put("name", bcolumnChild.getName());
			preference.put("icon", bcolumnChild.getIcon());

			bcolumnMap.put("list_action", listAction);
			bcolumnMap.put("preference", preference);

			childlist.add(bcolumnMap);
		}
		iteamMap.put("child", childlist);
		reslist.add(iteamMap);

		for (Bcolumn bcolumnRecursion : columnsItem) {
			if (bcolumnRecursion.getData_type() == 0)
				organizationRecursion(bcolumnRecursion, reslist, channel_code);
		}
	}

	private void organizationBeginPackage(List<Bcolumn> columns, List<Map<String, Object>> reslist) {
		if (!columns.isEmpty()) {
			Map<String, Object> iteamMap = new HashMap<String, Object>();
			iteamMap.put("name", "初始层");
			iteamMap.put("pid", 0);
			List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
			for (Bcolumn bcolumn : columns) {
				Map<String, Object> bcolumnMap = new HashMap<String, Object>();
				Map<String, Object> listAction = new HashMap<String, Object>();
				Map<String, Object> preference = new HashMap<String, Object>();
				bcolumnMap.put("aid", -1);
				bcolumnMap.put("pid", bcolumn.getTree_id());
				listAction.put("skip_action", 0);
				listAction.put("skip_url", "");
				preference.put("name", bcolumn.getName());
				preference.put("icon", bcolumn.getIcon());

				bcolumnMap.put("list_action", listAction);
				bcolumnMap.put("preference", preference);

				childlist.add(bcolumnMap);
			}
			iteamMap.put("child", childlist);
			reslist.add(iteamMap);
		}

	}

}
