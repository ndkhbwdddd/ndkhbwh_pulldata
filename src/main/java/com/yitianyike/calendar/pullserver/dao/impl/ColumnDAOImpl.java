package com.yitianyike.calendar.pullserver.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.dao.ColumnDAO;
import com.yitianyike.calendar.pullserver.model.Column;
import com.yitianyike.calendar.pullserver.model.Flush;
import com.yitianyike.calendar.pullserver.model.response.SubscribedInfo;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;
import com.yitianyike.calendar.pullserver.model.responsedata.Tabs;

@Component("columnDAO")
public class ColumnDAOImpl extends BaseDAO implements ColumnDAO {

	@Override
	public List<Bcolumn> subscribedList(Map<String, String> parmMap) {

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT  cps_package.tree_id,cps_package.data_type,cps_package.skip_action,cps_package.skip_url as h5_url,cps_package.tree_name,cps_source.img_url FROM cps_package  INNER JOIN cps_source   ON cps_package.resource_id=cps_source.resource_id  WHERE cps_package.history_id=0 AND cps_source.history_id=0 AND cps_package.`status`=1   AND data_type=1 AND tab_id=1   AND cps_package.channel_code=:channel_code  ORDER BY tree_order");
		List<Bcolumn> list = new ArrayList<Bcolumn>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("channel_code", parmMap.get("channel_code"));
		try {
			list = this.getNamedParameterJdbcTemplate().query(sb.toString(), paramMap, new RowMapper<Bcolumn>() {
				@Override
				public Bcolumn mapRow(ResultSet rs, int rowNum) throws SQLException {
					Bcolumn bcolumn = new Bcolumn();
					bcolumn.setSkip_action(rs.getInt("skip_action"));
					bcolumn.setSkip_url(rs.getString("h5_url"));
					bcolumn.setIcon(rs.getString("img_url"));
					bcolumn.setName(rs.getString("tree_name"));
					bcolumn.setTree_id(rs.getInt("tree_id"));
					bcolumn.setSaveValue();
					return bcolumn;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// public List<SubscribedInfo> subscribedList1(Map<String, String> parmMap)
	// {
	//
	// StringBuilder sql = new StringBuilder();
	// String version = parmMap.get("version");
	// sql.append("SELECT CONCAT(p.channel_code,'-'," + "'" + version + "'"
	// + ",'-subscribed') as cachekey, l.little_type,l.type,
	// c.skip_action,c.h5_url, c.column_id as id, c.column_name,
	// c.subed_column_style, i.icon_url, p.channel_code FROM cms_layout
	// l,cms_anchor an,cms_package p, cms_column c, cms_image i, cms_channel ch
	// WHERE c.anchor_id=an.anchor_id AND an.layout_id=l.l_id AND c.image_id =
	// i.id AND p.package_id = c.package_id AND ch.channel_code = p.channel_code
	// AND c.`status` = 1 AND an.`status`=1 AND p.`status`=1 AND ch.`status`=1
	// AND p.channel_code=:channel_code AND p.use_range=1");
	// List<SubscribedInfo> resultList =
	// this.getNamedParameterJdbcTemplate().query(sql.toString(), parmMap,
	// new RowMapper<SubscribedInfo>() {
	// @Override
	// public SubscribedInfo mapRow(ResultSet rs, int rowNum) throws
	// SQLException {
	// SubscribedInfo subscribed = new SubscribedInfo();
	// int id = rs.getInt("id");
	// int subed_column_style = rs.getInt("subed_column_style");
	// String column_name = rs.getString("column_name");
	// String icon_url = rs.getString("icon_url");
	// int little_type = rs.getInt("little_type");
	// int type = rs.getInt("type");
	// int skip_action = rs.getInt("skip_action");
	// String h5_url = rs.getString("h5_url");
	// subscribed.setChannel_code(rs.getString("channel_code"));
	// subscribed.setCachekey(rs.getString("cachekey"));
	// subscribed.setId(id);
	// subscribed.setSubed_column_style(subed_column_style);
	// subscribed.setColumn_name(column_name);
	// subscribed.setIcon(icon_url);
	// subscribed.setSavaJson(id, column_name, subed_column_style, icon_url,
	// little_type, type,
	// skip_action, h5_url);
	// return subscribed;
	// }
	// });
	//
	// return resultList;
	// }

	// @Override
	// public List<Column> getColumnByPackageId(Integer packageId, String
	// channelCode) {
	// StringBuilder sql = new StringBuilder();
	// Map<String, Object> paramMap = new HashMap<String, Object>();
	// sql.append("select " + packageParam
	// + ",i.icon_url as icon,l.type,l.little_type from cms_package p, cms_image
	// i, cms_channel ch , cms_column c ,cms_anchor a,cms_layout l where
	// c.package_id=p.package_id AND c.anchor_id = a.anchor_id AND a.layout_id =
	// l.l_id AND ch.`status`=1 AND c.`status`=1 AND a.`status`=1 AND
	// c.package_id=:packageId AND p.channel_code=:channelCode"
	// + " AND p.channel_code=ch.channel_code AND p.image_id=i.id AND c.status=1
	// AND p.use_range=1");
	// paramMap.put("packageId", packageId);
	// paramMap.put("channelCode", channelCode);
	// List<Column> resultList = new ArrayList<Column>();
	// try {
	// resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(),
	// paramMap, new RowMapper<Column>() {
	// @Override
	// public Column mapRow(ResultSet rs, int rowNum) throws SQLException {
	// Column column = new Column();
	// column.setAnchorId(rs.getInt("anchor_id"));
	// column.setId(rs.getInt("id"));
	// column.setColumnDescInfo(rs.getString("column_desc_info"));
	// column.setColumnName(rs.getString("column_name"));
	// column.setColumnStyle(rs.getInt("column_style"));
	// column.setColumnOrderNo(rs.getInt("column_order_no"));
	// column.setEndTime(rs.getLong("end_time"));
	// column.setStartTime(rs.getLong("start_time"));
	// column.setH5Url(rs.getString("h5_url"));
	// column.setSkipAction(rs.getInt("skip_action"));
	// column.setIcon(rs.getString("icon"));
	// column.setType(rs.getInt("type"));
	// column.setLittleType(rs.getInt("little_type"));
	// column.setPackageId(rs.getInt("package_id"));
	// return column;
	// }
	// });
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return resultList;
	// }

	// @Override
	// public List<Column> getColumnByPackageIdAndLimit(Map<String, String>
	// paramMap) {
	// StringBuilder sql = new StringBuilder();
	// sql.append("select " + packageParam
	// + ",i.icon_url as icon from cms_package p, cms_image i, cms_channel ch ,
	// cms_column c where c.package_id=p.package_id AND c.package_id=:package_id
	// AND p.channel_code=:channel_code"
	// + " AND p.channel_code=ch.channel_code AND p.image_id=i.id AND c.status=1
	// LIMIT 0,4");
	// List<Column> resultList = new ArrayList<Column>();
	// try {
	// resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(),
	// paramMap, new RowMapper<Column>() {
	// @Override
	// public Column mapRow(ResultSet rs, int rowNum) throws SQLException {
	// Column column = new Column();
	// column.setAnchorId(rs.getInt("anchor_id"));
	// column.setId(rs.getInt("id"));
	// column.setColumnDescInfo(rs.getString("column_desc_info"));
	// column.setColumnName(rs.getString("column_name"));
	// column.setColumnStyle(rs.getInt("column_style"));
	// column.setColumnOrderNo(rs.getInt("column_order_no"));
	// column.setEndTime(rs.getLong("end_time"));
	// column.setStartTime(rs.getLong("start_time"));
	// column.setH5Url(rs.getString("h5_url"));
	// column.setSkipAction(rs.getInt("skip_action"));
	// column.setIcon(rs.getString("icon"));
	// column.setPackageId(rs.getInt("package_id"));
	// return column;
	// }
	// });
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return resultList;
	// }

	// @Override
	// public List<Column> getColumnById(Map<String, String> paramMap) {
	// String sql = "select c.column_id as id , c.skip_action , c.update_time
	// from cms_column c , cms_package p , cms_channel ch where c.column_id in
	// (:cids) and "
	// + "ch.channel_code=:channel_code and ch.channel_code=p.channel_code and
	// c.package_id = p.package_id AND p.use_range=1";
	// List<Column> resultList = new ArrayList<Column>();
	// try {
	// resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(),
	// paramMap, new RowMapper<Column>() {
	// @Override
	// public Column mapRow(ResultSet rs, int rowNum) throws SQLException {
	// Column column = new Column();
	// column.setId(rs.getInt("id"));
	// column.setUpdateTime(rs.getLong("update_time"));
	// column.setSkipAction(rs.getInt("skip_action"));
	// return column;
	// }
	//
	// });
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return resultList;
	// }

	// @Override
	// public List<Flush> getColumn(String channel_code) {
	// Map<String, Object> paramMap = new HashMap<String, Object>();
	// String sql = "SELECT ch.channel_code,c.column_id as
	// id,a.frequence,a.anchor_id,l.type"
	// + " FROM cms_channel ch,cms_package p,cms_column c,cms_anchor
	// a,cms_layout l WHERE ch.channel_code=p.channel_code"
	// + " AND p.package_id=c.package_id AND c.anchor_id=a.anchor_id AND
	// a.layout_id=l.l_id AND p.`status` = 1 AND c.`status` = 1 "
	// + " AND a.`status` = 1 AND ch.channel_code=:chennelCode ";
	// paramMap.put("chennelCode", channel_code);
	// List<Flush> resultList = new ArrayList<Flush>();
	// try {
	// resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(),
	// paramMap, new RowMapper<Flush>() {
	// @Override
	// public Flush mapRow(ResultSet rs, int rowNum) throws SQLException {
	// Flush flush = new Flush();
	// flush.setAnchorId(rs.getInt("anchor_id"));
	// flush.setChannelCode(rs.getString("channel_code"));
	// flush.setFrequence(rs.getString("frequence"));
	// flush.setType(rs.getInt("type"));
	// flush.setColumn_id(rs.getInt("id"));
	// return flush;
	// }
	//
	// });
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return resultList;
	// }

	// @Override
	// public List<Column> getColumnByPackageId(Integer i, Map<String, String>
	// paramMap) {
	// StringBuilder sql = new StringBuilder();
	// sql.append("select " + packageParam
	// + ",i.icon_url as icon,l.type,l.little_type from cms_package p, cms_image
	// i, cms_channel ch , cms_column c ,cms_anchor a,cms_layout l where
	// c.package_id=p.package_id AND c.anchor_id = a.anchor_id AND a.layout_id =
	// l.l_id AND ch.`status`=1 AND c.`status`=1 AND a.`status`=1 AND
	// c.package_id=:packageId AND p.channel_code=:channel_code"
	// + " AND p.channel_code=ch.channel_code AND p.image_id=i.id AND c.status=1
	// ");
	// paramMap.put("packageId", i.toString());
	// List<Column> resultList = new ArrayList<Column>();
	// try {
	// resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(),
	// paramMap, new RowMapper<Column>() {
	// @Override
	// public Column mapRow(ResultSet rs, int rowNum) throws SQLException {
	// Column column = new Column();
	// column.setAnchorId(rs.getInt("anchor_id"));
	// column.setId(rs.getInt("id"));
	// column.setColumnDescInfo(rs.getString("column_desc_info"));
	// column.setColumnName(rs.getString("column_name"));
	// column.setColumnStyle(rs.getInt("column_style"));
	// column.setColumnOrderNo(rs.getInt("column_order_no"));
	// column.setEndTime(rs.getLong("end_time"));
	// column.setStartTime(rs.getLong("start_time"));
	// column.setH5Url(rs.getString("h5_url"));
	// column.setSkipAction(rs.getInt("skip_action"));
	// column.setIcon(rs.getString("icon"));
	// column.setType(rs.getInt("type"));
	// column.setLittleType(rs.getInt("little_type"));
	// column.setPackageId(rs.getInt("package_id"));
	// return column;
	// }
	// });
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return resultList;
	// }

	// @Override
	// public List<Column> getColumnByPackageId(List<Integer> packageIdList,
	// Map<String, String> paramMap) {
	//
	// StringBuilder param = new StringBuilder();
	//
	// for (Integer id : packageIdList) {
	// param.append(id).append(",");
	// }
	// String substring = (param.toString()).substring(0,
	// (param.toString()).length() - 1);
	// StringBuilder sql = new StringBuilder();
	// sql.append("select " + packageParam
	// + ",i.icon_url as icon,l.type,l.little_type from cms_package p, cms_image
	// i, cms_channel ch , cms_column c ,cms_anchor a,cms_layout l WHERE
	// c.image_id=i.id AND ch.channel_code=p.channel_code AND
	// p.package_id=c.package_id AND c.anchor_id=a.anchor_id AND
	// a.layout_id=l.l_id AND p.use_range=5 AND c.package_id in("
	// + substring
	// + ") AND ch.`status`=1 AND c.`status`=1 AND a.`status`=1 AND
	// p.channel_code=:channel_code AND c.status=1");
	// List<Column> resultList = new ArrayList<Column>();
	// try {
	// resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(),
	// paramMap, new RowMapper<Column>() {
	// @Override
	// public Column mapRow(ResultSet rs, int rowNum) throws SQLException {
	// Column column = new Column();
	// column.setAnchorId(rs.getInt("anchor_id"));
	// column.setId(rs.getInt("id"));
	// column.setColumnDescInfo(rs.getString("column_desc_info"));
	// column.setColumnName(rs.getString("column_name"));
	// column.setColumnStyle(rs.getInt("column_style"));
	// column.setColumnOrderNo(rs.getInt("column_order_no"));
	// column.setEndTime(rs.getLong("end_time"));
	// column.setStartTime(rs.getLong("start_time"));
	// column.setH5Url(rs.getString("h5_url"));
	// column.setSkipAction(rs.getInt("skip_action"));
	// column.setIcon(rs.getString("icon"));
	// column.setType(rs.getInt("type"));
	// column.setLittleType(rs.getInt("little_type"));
	// column.setPackageId(rs.getInt("package_id"));
	// return column;
	// }
	// });
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return resultList;
	// }

	@Override
	public List<Tabs> getTabsList(Map<String, String> parmMap) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT tab_name,h5_url,tab_type FROM cps_center_tablist  WHERE channel_code=:channel_code   AND  history_id=0 AND  status=1 ORDER BY order_num");
		List<Tabs> resultList = new ArrayList<Tabs>();
		try {
			resultList = this.getNamedParameterJdbcTemplate().query(sb.toString(), parmMap, new RowMapper<Tabs>() {
				@Override
				public Tabs mapRow(ResultSet rs, int rowNum) throws SQLException {
					Tabs tabs = new Tabs();
					tabs.setH5_url(rs.getString("h5_url"));
					tabs.setTab_name(rs.getString("tab_name"));
					tabs.setTab_type(rs.getInt("tab_type"));
					return tabs;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
}
