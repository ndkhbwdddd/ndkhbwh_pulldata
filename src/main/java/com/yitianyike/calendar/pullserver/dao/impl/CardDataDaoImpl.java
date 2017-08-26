package com.yitianyike.calendar.pullserver.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.model.Anchor;
import com.yitianyike.calendar.pullserver.model.responseCardData.AlmanacZip;
import com.yitianyike.calendar.pullserver.model.responseCardData.ChanneRelevance;
import com.yitianyike.calendar.pullserver.model.responseCardData.ControlDrive;
import com.yitianyike.calendar.pullserver.model.responseCardData.DataType;
import com.yitianyike.calendar.pullserver.model.responseCardData.FootBasket;
import com.yitianyike.calendar.pullserver.model.responseCardData.Star;
import com.yitianyike.calendar.pullserver.model.responseCardData.TodayOnHistory;

@Component("cardDataDao")
public class CardDataDaoImpl extends BaseDAO implements CardDataDao {

	@Override
	public List<Anchor> findAnchorData(Map<String, String> paramMap) {

		StringBuilder sql = new StringBuilder();
		String cids = paramMap.get("cids").toString();
		sql.append(
				"SELECT co.column_id,an.season,an.anchor_id,an.card_style,co.column_name,an.quote_name,la.little_type,la.type,an.frequence,an.exp_date_time,	co.skip_action as skip_action FROM cms_channel ch,cms_package pa,cms_column co,cms_anchor an ,cms_layout la WHERE ch.channel_code=pa.channel_code AND pa.package_id=co.package_id AND co.anchor_id=an.anchor_id AND ch.channel_code=:channel_code  AND co.column_id in( "
						+ cids
						+ " ) AND pa.`status`=1 AND co.`status`=1 AND an.`status`=1 AND la.l_id=an.layout_id    AND pa.use_range=1");

		List<Anchor> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), paramMap,
				new RowMapper<Anchor>() {
					@Override
					public Anchor mapRow(ResultSet rs, int rowNum) throws SQLException {
						Anchor anchor = new Anchor();
						String column_name = rs.getString("column_name");
						String quote_name = rs.getString("quote_name");
						String season = rs.getString("season");
						int anchor_id = rs.getInt("anchor_id");
						int card_style = rs.getInt("card_style");
						int id = rs.getInt("column_id");
						int little_type = rs.getInt("little_type");
						long exp_date_time = rs.getLong("exp_date_time");
						int type = rs.getInt("type");
						int frequence = rs.getInt("frequence");
						anchor.setAnchor_id(anchor_id);
						anchor.setId(id);
						anchor.setQuote_name(quote_name);
						anchor.setCard_style(card_style);
						anchor.setLittle_type(little_type);
						anchor.setColumn_name(column_name);
						anchor.setExp_date_time(exp_date_time);
						anchor.setFrequence(frequence);
						anchor.setType(type);
						anchor.setSkip_action(rs.getInt("skip_action"));
						anchor.setSeason(season);
						return anchor;
					}
				});

		return resultList;
	}

	@Override
	public List<Map<String, Object>> organizeBasketBalls(Map<String, String> paramMap) {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT * FROM `cms_basketball` b WHERE (b.guestname=:quote_name OR b.hostname=:quote_name) AND b.season=:season");

		if (paramMap.containsKey("flush_style")) {
			sql.append(" AND date=CURDATE()");
		}

		List<Map<String, Object>> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), paramMap,
				new RowMapper<Map<String, Object>>() {
					@Override
					public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
						Map<String, Object> resultMap = new HashMap<String, Object>();
						resultMap.put("id", rs.getString("nid"));
						resultMap.put("hostname", rs.getString("hostname"));
						resultMap.put("hostlogo", rs.getString("hostlogo"));
						resultMap.put("hostscore", rs.getInt("hostscore"));
						resultMap.put("type", rs.getString("type"));
						resultMap.put("guestname", rs.getString("guestname"));
						resultMap.put("guestlogo", rs.getString("guestlogo"));
						resultMap.put("guestscore", rs.getInt("guestscore"));
						int status = rs.getInt("status");
						String statusString = (status == 1 || status == 0) ? (status == 0 ? "未开始" : "进行中") : "已结束";
						resultMap.put("status", statusString);
						resultMap.put("date", rs.getString("date"));
						resultMap.put("time", rs.getString("time"));
						resultMap.put("rnd", "");
						resultMap.put("matchurl", rs.getString("matchurl"));
						resultMap.put("package_title", "NBA");
						resultMap.put("package_type", "NBA");
						return resultMap;
					}
				});

		return resultList;
	}

	@Override
	public List<Map<String, Object>> organizeFootBalls(Map<String, String> paramMap) {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT * FROM `cms_football` b WHERE (b.guestname=:quote_name OR b.hostname=:quote_name) AND b.season=:season");

		if (paramMap.containsKey("flush_style")) {
			sql.append(" AND date=CURDATE()");
		}

		List<Map<String, Object>> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), paramMap,
				new RowMapper<Map<String, Object>>() {
					@Override
					public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
						Map<String, Object> resultMap = new HashMap<String, Object>();
						resultMap.put("id", rs.getString("nid"));
						resultMap.put("hostname", rs.getString("hostname"));
						resultMap.put("hostlogo", rs.getString("hostflag"));
						resultMap.put("hostscore", rs.getInt("hostscore"));
						resultMap.put("type", "");
						resultMap.put("guestname", rs.getString("guestname"));
						resultMap.put("guestlogo", rs.getString("guestflag"));
						resultMap.put("guestscore", rs.getInt("guestscore"));
						int status = rs.getInt("status");
						String statusString = (status == 1 || status == 0) ? (status == 0 ? "未开始" : "进行中") : "已结束";
						resultMap.put("status", statusString);
						resultMap.put("date", rs.getString("date"));
						resultMap.put("time", rs.getString("time"));
						resultMap.put("rnd", rs.getString("rnd_cn"));
						resultMap.put("matchurl", rs.getString("matchurl"));
						resultMap.put("package_title", rs.getString("package_title"));
						resultMap.put("package_type", rs.getString("package_type"));
						return resultMap;
					}
				});

		return resultList;
	}

	@Override
	public List<Map<String, Object>> flushSportCardData(String channel_code) {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT ch.channel_code,co.column_id as id,an.frequence,an.season FROM cms_channel ch,cms_package pa,cms_column co,cms_anchor an  WHERE ch.channel_code=pa.channel_code AND pa.package_id=co.package_id AND co.anchor_id=an.anchor_id  AND pa.`status`=1 AND co.`status`=1 AND an.`status`=1  AND an.season!='no_season' AND ch.channel_code=:channel_code   AND pa.use_range=1");
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("channel_code", channel_code);
		List<Map<String, Object>> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), paramMap,
				new RowMapper<Map<String, Object>>() {
					@Override
					public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
						Map<String, Object> resultMap = new HashMap<String, Object>();
						String channel_code = rs.getString("channel_code");
						String frequence = rs.getString("frequence");
						int id = rs.getInt("id");
						resultMap.put("channel_code", channel_code);
						resultMap.put("frequence", frequence);
						resultMap.put("last_time", new Date().getTime());
						resultMap.put("aid", id);
						return resultMap;
					}
				});
		return resultList;
	}

	@Override
	public List<Map<String, Object>> getBasketBalls(Map<String, String> paramMap) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT * FROM `cms_basketball` b WHERE (b.guestname=:quote_name OR b.hostname=:quote_name) AND b.season=:season AND date <=CURDATE() ORDER BY date desc LIMIT 2");

		List<Map<String, Object>> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), paramMap,
				new RowMapper<Map<String, Object>>() {
					@Override
					public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
						Map<String, Object> resultMap = new HashMap<String, Object>();
						resultMap.put("id", rs.getString("nid"));
						resultMap.put("hostname", rs.getString("hostname"));
						resultMap.put("hostlogo", rs.getString("hostlogo"));
						resultMap.put("hostscore", rs.getInt("hostscore"));
						resultMap.put("type", rs.getString("type"));
						resultMap.put("guestname", rs.getString("guestname"));
						resultMap.put("guestlogo", rs.getString("guestlogo"));
						resultMap.put("guestscore", rs.getInt("guestscore"));
						int status = rs.getInt("status");
						String statusString = (status == 1 || status == 0) ? (status == 0 ? "未开始" : "进行中") : "已结束";
						resultMap.put("status", statusString);
						resultMap.put("date", rs.getString("date"));
						resultMap.put("time", rs.getString("time"));
						resultMap.put("rnd", "");
						resultMap.put("matchurl", rs.getString("matchurl"));
						resultMap.put("package_title", "NBA");
						resultMap.put("package_type", "NBA");
						return resultMap;
					}
				});

		return resultList;
	}

	public List<Map<String, Object>> getFootBalls(Map<String, String> paramMap) {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT * FROM `cms_football` b WHERE (b.guestname=:quote_name OR b.hostname=:quote_name) AND b.season=:season AND date <=CURDATE() ORDER BY date desc LIMIT 2");

		List<Map<String, Object>> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), paramMap,
				new RowMapper<Map<String, Object>>() {
					@Override
					public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
						Map<String, Object> resultMap = new HashMap<String, Object>();
						resultMap.put("id", rs.getString("nid"));
						resultMap.put("hostname", rs.getString("hostname"));
						resultMap.put("hostlogo", rs.getString("hostflag"));
						resultMap.put("hostscore", rs.getInt("hostscore"));
						resultMap.put("type", "");
						resultMap.put("guestname", rs.getString("guestname"));
						resultMap.put("guestlogo", rs.getString("guestflag"));
						resultMap.put("guestscore", rs.getInt("guestscore"));
						int status = rs.getInt("status");
						String statusString = (status == 1 || status == 0) ? (status == 0 ? "未开始" : "进行中") : "已结束";
						resultMap.put("status", statusString);
						resultMap.put("date", rs.getString("date"));
						resultMap.put("time", rs.getString("time"));
						resultMap.put("rnd", rs.getString("rnd_cn"));
						resultMap.put("matchurl", rs.getString("matchurl"));
						resultMap.put("package_title", rs.getString("package_title"));
						resultMap.put("package_type", rs.getString("package_type"));
						return resultMap;
					}
				});

		return resultList;
	}

	// v1
	@Override
	public List<Star> pressInStar() {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT tt.stzs,tc.name,tc.complex,tc.love,tc.job,tc.healthy,tc.discuss,tc.colour,tc.number,tc.date,tc.logo,tc.desc_url as skip_url ,tc.matching FROM `tms_constellation`  tc INNER JOIN tms_todaytomorrow tt ON tt.xz_name=tc.name WHERE tc.date=CURDATE() ");

		List<Star> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(),
				new HashMap<String, String>(), new RowMapper<Star>() {
					@Override
					public Star mapRow(ResultSet rs, int rowNum) throws SQLException {

						Star star = new Star();
						star.setXz_name(rs.getString("name"));
						star.setColour(rs.getString("colour"));
						star.setComplex(rs.getString("complex"));
						star.setDate(rs.getString("date"));
						star.setDiscuss(rs.getString("discuss"));
						star.setHealthy(rs.getString("healthy"));
						star.setJob(rs.getString("job"));
						star.setLogo(rs.getString("logo"));
						star.setLove(rs.getString("love"));
						star.setMatching(rs.getString("matching"));
						star.setNumber(rs.getString("number"));
						star.setSkip_url(rs.getString("skip_url"));
						star.setStzs(rs.getString("stzs"));
						return star;
					}
				});

		return resultList;
	}

	@Override
	public ChanneRelevance getDataType(String xz_name, String channel_code) {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT  cp.tree_id,ca.data_style as data_type  ,ca.alias FROM cps_anchor ca  INNER JOIN cps_scene_anchor csa ON ca.anchor_id = csa.anchor_id INNER JOIN cms_package cp ON cp.source_id =csa.scene_id   WHERE ca.history_id=0 AND csa.history_id=0 AND cp.history_id=0 AND ca.status=1 AND csa.status=1 AND cp.status=1  AND cp.data_type=1 AND cp.tab_id=1 AND  csa.column_type=0  AND cp.channel_code=:channel_code AND ca.alias=:alias LIMIT 1");
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("channel_code", channel_code);
		hashMap.put("alias", xz_name);
		List<ChanneRelevance> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), hashMap,
				new RowMapper<ChanneRelevance>() {
					@Override
					public ChanneRelevance mapRow(ResultSet rs, int rowNum) throws SQLException {

						ChanneRelevance channeRelevance = new ChanneRelevance();

						channeRelevance.setTree_id(rs.getInt("tree_id"));
						channeRelevance.setData_type(rs.getInt("data_type"));
						channeRelevance.setAlias_name(rs.getString("alias"));
						return channeRelevance;
					}
				});

		return resultList.isEmpty() ? null : resultList.get(0);
	}

	@Override
	public List<ControlDrive> pressInControlDrive() {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT tl.skip_url,tl.date,tl.xxweihao,tl.cityname, tl.week,tcdm.control_drive_id  FROM 	tms_limit   tl  INNER JOIN tms_control_drive_map tcdm ON tl.cityname=tcdm.control_drive_name WHERE tl.date    BETWEEN    CURDATE() AND  DATE_ADD( CURDATE(), INTERVAL 6 DAY) ");

		List<ControlDrive> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(),
				new HashMap<String, String>(), new RowMapper<ControlDrive>() {
					@Override
					public ControlDrive mapRow(ResultSet rs, int rowNum) throws SQLException {

						ControlDrive controlDrive = new ControlDrive();
						controlDrive.setCityname(rs.getString("cityname"));
						controlDrive.setControl_drive_id(rs.getString("control_drive_id"));
						controlDrive.setDate(rs.getString("date"));
						controlDrive.setWeek(rs.getString("week"));
						controlDrive.setSkip_url(rs.getString("skip_url"));
						controlDrive.setXxweihao(rs.getString("xxweihao") == null ? "[]" : rs.getString("xxweihao"));
						return controlDrive;
					}
				});

		return resultList;
	}

	@Override
	public List<TodayOnHistory> pressInTodayOnHistory() {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT day,month,year,des,pic,title,skip_url FROM `tms_past_today`  WHERE  source=0  GROUP BY month,day   ");
		HashMap<String, String> hashMap = new HashMap<String, String>();

		List<TodayOnHistory> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), hashMap,
				new RowMapper<TodayOnHistory>() {
					@Override
					public TodayOnHistory mapRow(ResultSet rs, int rowNum) throws SQLException {

						TodayOnHistory th = new TodayOnHistory();
						th.setDay(rs.getString("day"));
						th.setDes(rs.getString("des"));
						th.setMonth(rs.getString("month"));
						th.setPic(rs.getString("pic"));
						th.setSkip_url(rs.getString("skip_url"));
						th.setTitle(rs.getString("title"));
						th.setYear(rs.getString("year"));
						return th;
					}
				});

		return resultList;
	}

	@Override
	public List<FootBasket> pressInFootBasket() {
		List<FootBasket> footBasketList = new ArrayList<FootBasket>();
		pressInFoot(footBasketList);
		pressInBasket(footBasketList);
		return footBasketList;
	}

	private void pressInBasket(List<FootBasket> footBasketList) {

		StringBuilder sql = new StringBuilder();
		// 前10个月,后1
		sql.append(
				"SELECT hostscore,hostname, hostlogo as hostflag,guestname,guestscore, guestlogo as guestflag,matchurl,status,date,time,package_title FROM `tms_sportnba` WHERE date BETWEEN   DATE_ADD(CURDATE(),INTERVAL -10 MONTH )  AND   DATE_ADD(CURDATE(),INTERVAL 1 MONTH ) ");
		HashMap<String, String> hashMap = new HashMap<String, String>();

		List<FootBasket> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), hashMap,
				new RowMapper<FootBasket>() {
					@Override
					public FootBasket mapRow(ResultSet rs, int rowNum) throws SQLException {

						FootBasket fb = new FootBasket();
						fb.setDate(rs.getString("date"));
						fb.setGuestflag(rs.getString("guestflag"));
						fb.setGuestname(rs.getString("guestname"));
						fb.setGuestscore(rs.getString("guestscore"));
						fb.setHostflag(rs.getString("hostflag"));
						fb.setHostname(rs.getString("hostname"));
						fb.setHostscore(rs.getString("hostscore"));
						fb.setMatchurl(rs.getString("matchurl"));
						fb.setPackage_title(rs.getString("package_title"));
						fb.setRnd("");
						fb.setTime(rs.getString("time"));
						fb.setStatus(rs.getString("status").equals("0") ? "未开始"
								: rs.getString("status").equals("1") ? "比赛中" : "已结束");
						return fb;
					}
				});

		footBasketList.addAll(resultList);
	}

	private void pressInFoot(List<FootBasket> footBasketList) {

		StringBuilder sql = new StringBuilder();
		// 前1后1
		sql.append(
				"SELECT rnd,hostscore,hostname,hostflag,guestname,guestscore,guestflag,matchurl,status,date,time,package_title FROM `tms_sportfootball` WHERE date BETWEEN   DATE_ADD(CURDATE(),INTERVAL -1 MONTH )  AND   DATE_ADD(CURDATE(),INTERVAL 1 MONTH ) ");
		HashMap<String, String> hashMap = new HashMap<String, String>();

		List<FootBasket> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), hashMap,
				new RowMapper<FootBasket>() {
					@Override
					public FootBasket mapRow(ResultSet rs, int rowNum) throws SQLException {

						FootBasket fb = new FootBasket();
						fb.setDate(rs.getString("date"));
						fb.setGuestflag(rs.getString("guestflag"));
						fb.setGuestname(rs.getString("guestname"));
						fb.setGuestscore(rs.getString("guestscore"));
						fb.setHostflag(rs.getString("hostflag"));
						fb.setHostname(rs.getString("hostname"));
						fb.setHostscore(rs.getString("hostscore"));
						fb.setMatchurl(rs.getString("matchurl"));
						fb.setPackage_title(rs.getString("package_title"));
						fb.setRnd(rs.getString("rnd"));
						fb.setTime(rs.getString("time"));
						fb.setStatus(rs.getString("status").equals("0") ? "未开始"
								: rs.getString("status").equals("1") ? "比赛中" : "已结束");
						return fb;
					}
				});

		footBasketList.addAll(resultList);

	}

	@Override
	public List<ChanneRelevance> getDataType(String hostname, String guestname, String channel_code) {

		StringBuilder sql = new StringBuilder();

		sql.append(
				"SELECT  cp.tree_id,ca.data_style as data_type  FROM cps_anchor ca  INNER JOIN cps_scene_anchor csa ON ca.anchor_id = csa.anchor_id INNER JOIN cms_package cp ON cp.source_id =csa.scene_id   WHERE ca.history_id=0 AND csa.history_id=0 AND cp.history_id=0 AND ca.status=1 AND csa.status=1 AND cp.status=1  AND cp.data_type=1 AND cp.tab_id=1 AND  csa.column_type=0  AND  cp.channel_code=:channel_code AND ca.alias  in (");

		sql.append("'").append(hostname).append("'").append(",").append("'").append(guestname).append("'").append(" )");

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("channel_code", channel_code);
		List<ChanneRelevance> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), hashMap,
				new RowMapper<ChanneRelevance>() {
					@Override
					public ChanneRelevance mapRow(ResultSet rs, int rowNum) throws SQLException {

						ChanneRelevance channeRelevance = new ChanneRelevance();

						channeRelevance.setTree_id(rs.getInt("tree_id"));
						channeRelevance.setData_type(rs.getInt("data_type"));

						return channeRelevance;
					}
				});
		return resultList;
	}

	@Override
	public List<DataType> pressInDataType(String channel_code) {
		StringBuilder sql = new StringBuilder();

		sql.append(
				"SELECT cp.tree_id,ca.data_type  FROM cms_package  cp  INNER JOIN  cps_scene_anchor  csa ON cp.source_id=csa.scene_id  INNER JOIN cps_anchor ca ON ca.anchor_id=csa.anchor_id  WHERE csa.history_id=0 AND cp.history_id=0  AND ca.history_id=0  AND ca.`status`=1 AND csa.`status`=1 AND cp.`status`=1  AND cp.data_type=1 AND cp.tab_id=1  AND csa.column_type=0  AND cp.channel_code=:channel_code");

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("channel_code", channel_code);
		List<DataType> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), hashMap,
				new RowMapper<DataType>() {
					@Override
					public DataType mapRow(ResultSet rs, int rowNum) throws SQLException {

						DataType dataType = new DataType();
						dataType.setTree_id(rs.getString("tree_id"));
						dataType.setData_type(rs.getString("data_type"));
						return dataType;
					}
				});
		return resultList;
	}

	@Override
	public List<AlmanacZip> pressInAlmanacZip() {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT lunar_year,animals_year,avoid,suit,lunar ,date,skip_url FROM `tms_almanac` WHERE date  BETWEEN   CONCAT(YEAR(CURDATE()),'-01-01')  AND  CONCAT(YEAR(CURDATE()),'-12-31') ");
		HashMap<String, String> hashMap = new HashMap<String, String>();

		List<AlmanacZip> resultList = this.getNamedParameterJdbcTemplate().query(sql.toString(), hashMap,
				new RowMapper<AlmanacZip>() {
					@Override
					public AlmanacZip mapRow(ResultSet rs, int rowNum) throws SQLException {

						AlmanacZip az = new AlmanacZip();
						az.setAnimals_year(rs.getString("animals_year"));
						az.setAvoid(rs.getString("avoid"));
						az.setDate(rs.getString("date"));
						az.setLunar(rs.getString("lunar"));
						az.setLunar_year(rs.getString("lunar_year"));
						az.setSkip_url(rs.getString("skip_url"));
						az.setSuit(rs.getString("suit"));
						return az;
					}
				});

		return resultList;

	}

	@Override
	public void pressInZipUrl(String channel_code, String saveZipUrl, String alias_name, int tree_id) {
		// 删除原数据

		String deleteSql = "DELETE FROM tms_downzip  WHERE channel_code=:channel_code AND alias=:alias ";
		Map<String, Object> deleteMap = new HashMap<String, Object>();
		deleteMap.put("channel_code", channel_code);
		deleteMap.put("alias", alias_name);
		this.getNamedParameterJdbcTemplate().update(deleteSql, deleteMap);

		// 存入新数据
		StringBuilder sb = new StringBuilder();
		sb.append("insert into tms_downzip (channel_code, downzip_url, alias,aid) values(?, ?, ?, ?)");
		Object[] objs = new Object[] { channel_code, saveZipUrl, alias_name, tree_id };
		this.getJdbcTemplate().update(sb.toString(), objs);

	}

}