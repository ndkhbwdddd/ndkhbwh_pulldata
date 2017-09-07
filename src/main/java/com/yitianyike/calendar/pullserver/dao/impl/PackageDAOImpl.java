package com.yitianyike.calendar.pullserver.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.dao.PackageDAO;
import com.yitianyike.calendar.pullserver.model.PackageInfo;
import com.yitianyike.calendar.pullserver.model.response.Recommend;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responsedata.BRecommend;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;
import com.yitianyike.calendar.pullserver.util.DateUtil;

@Component("packageDAO")
public class PackageDAOImpl extends BaseDAO implements PackageDAO {

	// private String packageParam =
	// "p.id,p.channel_code,p.package_id,p.package_name,p.pid,p.pid_path,"
	// +
	// "p.image_id,p.package_style,p.status,p.package_order_no,p.use_range,p.start_time,p.end_time
	// ,"
	// + "p.create_name,p.create_time,p.update_name,p.update_time ";

	// @Override
	// public List<PackageInfo> getPackage(int pid, String channelCode) {
	// // TODO Auto-generated method stub
	// Map<String, Object> paramMap = new HashMap<String, Object>();
	//
	// String selectSql = "select " + packageParam
	// + ", i.icon_url as icon from cms_package p, cms_image i, cms_channel c
	// where p.pid=:pid "
	// + " AND c.`status`=1 AND p.channel_code=:channelCode"
	// + " AND p.channel_code=c.channel_code AND p.image_id=i.id AND p.status=1
	// AND p.use_range=1";
	//
	// paramMap.put("pid", pid);
	// paramMap.put("channelCode", channelCode);
	// List<PackageInfo> list = new ArrayList<PackageInfo>();
	// try {
	// list = this.getNamedParameterJdbcTemplate().query(selectSql, paramMap,
	// new RowMapper<PackageInfo>() {
	// @Override
	// public PackageInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
	// PackageInfo mi = new PackageInfo();
	// mi.setChannelCode(rs.getString("channel_code"));
	// mi.setEndTime(rs.getLong("end_time"));
	// mi.setStartTime(rs.getLong("start_time"));
	// mi.setImageId(rs.getInt("image_id"));
	// mi.setIcon(rs.getString("icon"));
	// mi.setPackageStyle(rs.getInt("package_style"));
	// mi.setPackageId(rs.getInt("package_id"));
	// mi.setPackageName(rs.getString("package_name"));
	// mi.setPackageOrderNo(rs.getInt("package_order_no"));
	// mi.setPid(rs.getInt("pid"));
	// mi.setId(rs.getInt("id"));
	// return mi;
	// }
	//
	// });
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	//
	// return list;
	//
	// }

	// @Override
	// public List<Map<String, Object>> organizeMiddle(Map<String, String>
	// parmMap) {
	// StringBuilder sql = new StringBuilder();
	// sql.append(
	// "SELECT p.package_name,p.package_order_no,i.icon_url,p.package_id FROM
	// cms_package p,cms_channel c,cms_image i WHERE
	// p.channel_code=c.channel_code AND i.id=p.image_id AND
	// p.channel_code=:channel_code AND p.status=1 AND p.use_range=3 ORDER BY
	// p.package_order_no");
	//
	// List<Map<String, Object>> resultList =
	// this.getNamedParameterJdbcTemplate().query(sql.toString(), parmMap,
	// new RowMapper<Map<String, Object>>() {
	// @Override
	// public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws
	// SQLException {
	// Map<String, Object> recommendMap = new HashMap<String, Object>();
	// String package_name = rs.getString("package_name");
	// int package_order_no = rs.getInt("package_order_no");
	// String icon_url = rs.getString("icon_url");
	// int package_id = rs.getInt("package_id");
	// recommendMap.put("icon", icon_url);
	// recommendMap.put("title", package_name);
	// recommendMap.put("order_no", package_order_no);
	// recommendMap.put("pid", package_id);
	// return recommendMap;
	// }
	// });
	//
	// return resultList;
	// }

	// @Override
	// public List<Recommend> organizeTail(Map<String, String> parmMap) {
	// StringBuilder sql = new StringBuilder();
	// sql.append(
	// "SELECT p.package_name,p.package_id,p.package_order_no FROM cms_package
	// p,cms_channel c WHERE p.channel_code=c.channel_code AND
	// p.channel_code=:channel_code AND p.use_range=4 ORDER BY
	// p.package_order_no");
	//
	// List<Recommend> resultList =
	// this.getNamedParameterJdbcTemplate().query(sql.toString(), parmMap,
	// new RowMapper<Recommend>() {
	// @Override
	// public Recommend mapRow(ResultSet rs, int rowNum) throws SQLException {
	// Recommend recommend = new Recommend();
	// String package_name = rs.getString("package_name");
	// int package_order_no = rs.getInt("package_order_no");
	// int package_id = rs.getInt("package_id");
	// recommend.setPackage_id(package_id);
	// recommend.setPackage_name(package_name);
	// recommend.setPackage_order_no(package_order_no);
	// return recommend;
	// }
	// });
	//
	// return resultList;
	// }

	// @Override
	// public List<Map<String, Object>> organizeTop(Map<String, String> parmMap)
	// {
	// StringBuilder sql = new StringBuilder();
	// sql.append(
	// "SELECT r.`name`,r.descinfo,r.banner_url,r.h5_url FROM
	// cms_recommend_extend r,cms_channel c WHERE r.channel_code=c.channel_code
	// AND r.channel_code=:channel_code AND r.`status`=1 ORDER BY order_no");
	// List<Map<String, Object>> resultList =
	// this.getNamedParameterJdbcTemplate().query(sql.toString(), parmMap,
	// new RowMapper<Map<String, Object>>() {
	// @Override
	// public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws
	// SQLException {
	// Map<String, Object> recommendMap = new HashMap<String, Object>();
	// String name = rs.getString("name");
	// String descinfo = rs.getString("descinfo");
	// String banner_url = rs.getString("banner_url");
	// String h5_url = rs.getString("h5_url");
	// recommendMap.put("title", name);
	// recommendMap.put("desc_info", descinfo);
	// recommendMap.put("h5_url", h5_url);
	// recommendMap.put("banner_url", banner_url);
	// return recommendMap;
	// }
	// });
	//
	// return resultList;
	// }

	// @Override
	// public List<Integer> getRecommendVersion(Map<String, String> paramMap) {
	// StringBuilder sql = new StringBuilder();
	// sql.append("SELECT recommend_version FROM cms_channel WHERE
	// channel_code=:channel_code AND version=:version");
	//
	// List<Integer> recommendVersion =
	// this.getNamedParameterJdbcTemplate().query(sql.toString(), paramMap,
	// new RowMapper<Integer>() {
	// @Override
	// public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
	// int recommend_version = rs.getInt("recommend_version");
	// return recommend_version;
	// }
	// });
	//
	// return recommendVersion;
	// }

	// @Override
	// public List<PackageInfo> getPackage(Integer i, Map<String, String>
	// paramMap) {
	// String selectSql = "select " + packageParam
	// + ", i.icon_url as icon from cms_package p, cms_image i, cms_channel c
	// where p.pid=:pid "
	// + " AND c.`status`=1 AND p.channel_code=:channel_code"
	// + " AND p.channel_code=c.channel_code AND p.image_id=i.id AND p.status=1
	// AND p.use_range=6";
	// paramMap.put("pid", i.toString());
	// List<PackageInfo> list = new ArrayList<PackageInfo>();
	// try {
	// list = this.getNamedParameterJdbcTemplate().query(selectSql, paramMap,
	// new RowMapper<PackageInfo>() {
	// @Override
	// public PackageInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
	// PackageInfo mi = new PackageInfo();
	// mi.setChannelCode(rs.getString("channel_code"));
	// mi.setEndTime(rs.getLong("end_time"));
	// mi.setStartTime(rs.getLong("start_time"));
	// mi.setImageId(rs.getInt("image_id"));
	// mi.setIcon(rs.getString("icon"));
	// mi.setPackageStyle(rs.getInt("package_style"));
	// mi.setPackageId(rs.getInt("package_id"));
	// mi.setPackageName(rs.getString("package_name"));
	// mi.setPackageOrderNo(rs.getInt("package_order_no"));
	// mi.setPid(rs.getInt("pid"));
	// mi.setId(rs.getInt("id"));
	// return mi;
	// }
	//
	// });
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return list;
	//
	// }

	// @Override
	// public List<Integer> getPackageId(Map<String, String> paramMap) {
	// String selectSql = "select p.package_id from cms_package p, cms_channel c
	// where c.`status`=1 AND p.channel_code=:channel_code AND
	// p.channel_code=c.channel_code AND p.status=1 AND p.use_range=5";
	// List<Integer> list = new ArrayList<Integer>();
	// try {
	// list = this.getNamedParameterJdbcTemplate().query(selectSql, paramMap,
	// new RowMapper<Integer>() {
	// @Override
	// public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
	//
	// int package_id = rs.getInt("package_id");
	// return package_id;
	// }
	//
	// });
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return list;
	//
	// }

	@Override
	public List<Bcolumn> getBeginPackage(int tabs, String channel_code) {

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT  cps_package.tree_id,cps_package.data_type,cps_package.skip_action,cps_package.skip_url as h5_url,cps_package.tree_name,cps_source.img_url FROM cps_package  INNER JOIN cps_source   ON cps_package.resource_id=cps_source.resource_id  WHERE cps_package.history_id=0 AND cps_source.history_id=0 AND cps_package.`status`=1  AND cps_package.data_type=0   AND  cps_package.pid=0  AND cps_package.channel_code=:channel_code  ORDER BY tree_order");
		List<Bcolumn> list = new ArrayList<Bcolumn>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("tabs", tabs);
		paramMap.put("channel_code", channel_code);

		try {
			list = this.getNamedParameterJdbcTemplate().query(sb.toString(), paramMap, new RowMapper<Bcolumn>() {
				@Override
				public Bcolumn mapRow(ResultSet rs, int rowNum) throws SQLException {
					Bcolumn bcolumn = new Bcolumn();
					bcolumn.setData_type(rs.getInt("data_type"));
					bcolumn.setSkip_action(rs.getInt("skip_action"));
					bcolumn.setSkip_url(rs.getString("h5_url"));
					// bcolumn.setValues(rs.getString("style_value"));
					bcolumn.setIcon(rs.getString("img_url"));
					bcolumn.setName(rs.getString("tree_name"));
					bcolumn.setTree_id(rs.getInt("tree_id"));
					return bcolumn;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Bcolumn> getItemPackage(int tree_id, String channel_code) {

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT  cps_package.tree_id,cps_package.data_type,cps_package.skip_action,cps_package.skip_url as h5_url,cps_package.tree_name,cps_source.img_url FROM cps_package  INNER JOIN cps_source   ON cps_package.resource_id=cps_source.resource_id  WHERE cps_package.history_id=0 AND cps_source.history_id=0 AND cps_package.`status`=1    AND  cps_package.pid=:tree_id AND cps_package.channel_code=:channel_code  ORDER BY tree_order");
		List<Bcolumn> list = new ArrayList<Bcolumn>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tree_id", tree_id);
		paramMap.put("channel_code", channel_code);

		try {
			list = this.getNamedParameterJdbcTemplate().query(sb.toString(), paramMap, new RowMapper<Bcolumn>() {
				@Override
				public Bcolumn mapRow(ResultSet rs, int rowNum) throws SQLException {
					Bcolumn bcolumn = new Bcolumn();
					bcolumn.setData_type(rs.getInt("data_type"));
					bcolumn.setSkip_action(rs.getInt("skip_action"));
					bcolumn.setSkip_url(rs.getString("h5_url"));
					// bcolumn.setValues(rs.getString("style_value"));
					bcolumn.setIcon(rs.getString("img_url"));
					bcolumn.setName(rs.getString("tree_name"));
					bcolumn.setTree_id(rs.getInt("tree_id"));
					return bcolumn;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<BRecommend> getBannerList(String channel_code) {

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT resource_id  as banner_id,tree_order FROM `cps_package` WHERE  tab_id=2 AND data_type in (2,3) AND `status`=1 AND history_id=0  AND channel_code=:channel_code");
		List<BRecommend> list = new ArrayList<BRecommend>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("channel_code", channel_code);

		try {
			list = this.getNamedParameterJdbcTemplate().query(sb.toString(), paramMap, new RowMapper<BRecommend>() {
				@Override
				public BRecommend mapRow(ResultSet rs, int rowNum) throws SQLException {
					BRecommend bRecommend = new BRecommend();
					bRecommend.setBanner_id(rs.getInt("banner_id"));
					bRecommend.setTree_order(rs.getInt("tree_order"));
					return bRecommend;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public List<BRecommend> getTopicList(String channel_code) {

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT  tree_id,tree_order FROM `cps_package` WHERE   pid=0 AND  tab_id=2 AND data_type=5 AND status=1  AND history_id=0 AND channel_code=:channel_code");
		List<BRecommend> list = new ArrayList<BRecommend>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("channel_code", channel_code);

		try {
			list = this.getNamedParameterJdbcTemplate().query(sb.toString(), paramMap, new RowMapper<BRecommend>() {
				@Override
				public BRecommend mapRow(ResultSet rs, int rowNum) throws SQLException {
					BRecommend bRecommend = new BRecommend();
					bRecommend.setAid(rs.getInt("tree_id"));
					bRecommend.setTree_order(rs.getInt("tree_order"));
					return bRecommend;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public List<BRecommend> getClassifyList(String channel_code) {

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT  tree_name,tree_id,tree_order FROM `cps_package` WHERE   pid=0 AND  tab_id=2 AND data_type=4 AND status=1  AND history_id=0 AND channel_code=:channel_code");
		List<BRecommend> list = new ArrayList<BRecommend>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("channel_code", channel_code);
		try {
			list = this.getNamedParameterJdbcTemplate().query(sb.toString(), paramMap, new RowMapper<BRecommend>() {
				@Override
				public BRecommend mapRow(ResultSet rs, int rowNum) throws SQLException {
					BRecommend bRecommend = new BRecommend();
					bRecommend.setAid(rs.getInt("tree_id"));
					bRecommend.setTree_order(rs.getInt("tree_order"));
					bRecommend.setPackage_name(rs.getString("tree_name"));
					return bRecommend;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public List<BRecommend> getItemUnderBanner(int banner_id) {

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT   skip_url AS h5_url, banner_url AS img_url,type AS url_type FROM `cps_banner` WHERE history_id=0 AND resource_id=:banner_id ORDER BY banner_order");
		List<BRecommend> list = new ArrayList<BRecommend>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("banner_id", banner_id);
		// paramMap.put("data_time", DateUtil.getCurrentDateTime());

		try {
			list = this.getNamedParameterJdbcTemplate().query(sb.toString(), paramMap, new RowMapper<BRecommend>() {
				@Override
				public BRecommend mapRow(ResultSet rs, int rowNum) throws SQLException {
					BRecommend bRecommend = new BRecommend();
					bRecommend.setSkip_url(rs.getString("h5_url"));
					bRecommend.setIcon_url(rs.getString("img_url"));
					bRecommend.setSkip_type(rs.getInt("url_type"));
					return bRecommend;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public List<BRecommend> getItemUnderTopic(int aid, String channel_code) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT rem_skip_action  AS data_type,tree_id  AS source_id,resource_id AS style_id FROM `cps_package` WHERE   pid=:pid AND history_id=0 AND  tab_id=2 AND data_type=5 AND status=1 AND channel_code=:channel_code  ORDER BY tree_order");
		List<BRecommend> list = new ArrayList<BRecommend>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pid", aid);
		paramMap.put("channel_code", channel_code);

		try {
			list = this.getNamedParameterJdbcTemplate().query(sb.toString(), paramMap, new RowMapper<BRecommend>() {
				@Override
				public BRecommend mapRow(ResultSet rs, int rowNum) throws SQLException {
					BRecommend bRecommend = new BRecommend();
					bRecommend.setData_type(rs.getInt("data_type"));
					bRecommend.setSource_id(rs.getInt("source_id"));
					bRecommend.setStyle_id(rs.getInt("style_id"));
					return bRecommend;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public List<Bcolumn> getItemUnderTopicIsPackage(int style_id) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT resource_name,skip_url,img_url  FROM cps_source WHERE history_id=0 AND resource_id=:resource_id ORDER BY img_order");
		List<Bcolumn> list = new ArrayList<Bcolumn>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("resource_id", style_id);

		try {
			list = this.getNamedParameterJdbcTemplate().query(sb.toString(), paramMap, new RowMapper<Bcolumn>() {
				@Override
				public Bcolumn mapRow(ResultSet rs, int rowNum) throws SQLException {
					Bcolumn bcolumn = new Bcolumn();
					bcolumn.setName(rs.getString("resource_name"));
					bcolumn.setIcon(rs.getString("img_url"));
					bcolumn.setSkip_url(rs.getString("skip_url"));
					return bcolumn;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public List<BRecommend> getItemUnderTopicIsBanner(int source_id) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT h5_url , img_url FROM `cms_img` WHERE history_id=0 AND banner_id=:banner_id");
		List<BRecommend> list = new ArrayList<BRecommend>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("banner_id", source_id);

		try {
			list = this.getNamedParameterJdbcTemplate().query(sb.toString(), paramMap, new RowMapper<BRecommend>() {
				@Override
				public BRecommend mapRow(ResultSet rs, int rowNum) throws SQLException {
					BRecommend bRecommend = new BRecommend();
					bRecommend.setSkip_url(rs.getString("h5_url"));
					return bRecommend;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Bcolumn> getItemUnderClassifyAndValue(int aid, String channel_code) {
		


		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT  cps_package.tree_id,cps_package.data_type,cps_package.skip_action,cps_package.skip_url as h5_url,cps_package.tree_name,cps_source.img_url FROM cps_package  INNER JOIN cps_source   ON cps_package.resource_id=cps_source.resource_id  WHERE cps_package.history_id=0 AND cps_source.history_id=0 AND cps_package.`status`=1  AND cps_package.data_type=4  AND cps_package.tab_id=2  AND  cps_package.pid=:aid  AND cps_package.channel_code=:channel_code  ORDER BY tree_order");
		List<Bcolumn> list = new ArrayList<Bcolumn>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("aid", aid);
		paramMap.put("channel_code", channel_code);

		try {
			list = this.getNamedParameterJdbcTemplate().query(sb.toString(), paramMap, new RowMapper<Bcolumn>() {
				@Override
				public Bcolumn mapRow(ResultSet rs, int rowNum) throws SQLException {
					Bcolumn bcolumn = new Bcolumn();
					bcolumn.setData_type(rs.getInt("data_type"));
					bcolumn.setSkip_action(rs.getInt("skip_action"));
					bcolumn.setSkip_url(rs.getString("h5_url"));
					// bcolumn.setValues(rs.getString("style_value"));
					bcolumn.setIcon(rs.getString("img_url"));
					bcolumn.setName(rs.getString("tree_name"));
					bcolumn.setTree_id(rs.getInt("tree_id"));
					return bcolumn;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	
		

	}

}
