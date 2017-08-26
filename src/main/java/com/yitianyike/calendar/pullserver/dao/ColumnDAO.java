package com.yitianyike.calendar.pullserver.dao;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.Column;
import com.yitianyike.calendar.pullserver.model.Flush;
import com.yitianyike.calendar.pullserver.model.response.SubscribedInfo;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;
import com.yitianyike.calendar.pullserver.model.responsedata.Tabs;

public interface ColumnDAO {

	List<Bcolumn> subscribedList(Map<String, String> parmMap);

	/**
	 * 根据packageid查询
	 * 
	 * @param packageId
	 * @param version
	 * @param channelCode
	 * @return
	 */
	//public List<Column> getColumnByPackageId(Integer packageId, String channelCode);

	//public List<Column> getColumnByPackageIdAndLimit(Map<String, String> paramMap);

	/**
	 * 根据栏目id查询
	 * 
	 * @param paramMap
	 * @return
	 */
	//public List<Column> getColumnById(Map<String, String> paramMap);

	/**
	 * 查询栏目
	 * 
	 * @param version
	 * @param channel_code
	 * @return
	 */
	//public List<Flush> getColumn(String channel_code);

	//List<Column> getColumnByPackageId(Integer i, Map<String, String> paramMap);

	//List<Column> getColumnByPackageId(List<Integer> packageIdList, Map<String, String> paramMap);

	/**
	 * v1
	 * tabs列表
	 * @param parmMap
	 * @return
	 */
	List<Tabs> getTabsList(Map<String, String> parmMap);

}
