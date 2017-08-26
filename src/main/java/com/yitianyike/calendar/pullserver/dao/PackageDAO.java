package com.yitianyike.calendar.pullserver.dao;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.PackageInfo;
import com.yitianyike.calendar.pullserver.model.response.Recommend;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responsedata.BRecommend;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;

public interface PackageDAO {

	// public List<PackageInfo> getPackage(int pid, String channelCode);

//	public List<Map<String, Object>> organizeMiddle(Map<String, String> parmMap);
//
//	public List<Recommend> organizeTail(Map<String, String> parmMap);
//
//	public List<Map<String, Object>> organizeTop(Map<String, String> parmMap);
//
//	public List<Integer> getRecommendVersion(Map<String, String> paramMap);

	/**
	 * 6带包类推荐,获取父亲包
	 * 
	 * @param i
	 * @param paramMap
	 * @return
	 */
	// public List<PackageInfo> getPackage(Integer i, Map<String, String>
	// paramMap);

	//public List<Integer> getPackageId(Map<String, String> paramMap);

	/**
	 * v1
	 * 
	 * @param tabs
	 * @param channel_code
	 * @return
	 */
	public List<Bcolumn> getBeginPackage(int tabs, String channel_code);

	/**
	 * v1
	 * 
	 * @param tree_id
	 * @param channel_code
	 * @return
	 */
	public List<Bcolumn> getItemPackage(int tree_id, String channel_code);

	/**
	 * v1
	 * 
	 * @param channel_code
	 * @return
	 */
	public List<BRecommend> getBannerList(String channel_code);

	/**
	 * v1
	 * 
	 * @param channel_code
	 * @return
	 */
	public List<BRecommend> getTopicList(String channel_code);

	/**
	 * v1
	 * 
	 * @param channel_code
	 * @return
	 */
	public List<BRecommend> getClassifyList(String channel_code);

	/**
	 * v1
	 * 
	 * @param banner_id
	 * @return
	 */
	public List<BRecommend> getItemUnderBanner(int banner_id);

	/**
	 * v1
	 * 
	 * @param aid
	 * @param channel_code
	 * @return
	 */
	public List<BRecommend> getItemUnderTopic(int aid, String channel_code);

	/**
	 * v1
	 * 
	 * @param style_id
	 * @return
	 */
	public List<Bcolumn> getItemUnderTopicIsPackage(int style_id);

	/**
	 * v1
	 * 
	 * @param source_id
	 * @return
	 */
	public List<BRecommend> getItemUnderTopicIsBanner(int source_id);

	/**
	 * v1
	 * 
	 * @param aid
	 * @param channel_code
	 * @return
	 */
	public List<Bcolumn> getItemUnderClassifyAndValue(int aid, String channel_code);



}
