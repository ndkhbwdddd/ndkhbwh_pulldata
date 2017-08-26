package com.yitianyike.calendar.pullserver.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.model.Almanac;
import com.yitianyike.calendar.pullserver.model.PackageInfo;
@Component("almanacDAO")
public class AlmanacDAOImpl extends BaseDAO implements AlmanacDAO {

	private String almanacParam = "id,aid,status,holiday ,avoid ,animals_year  ,alm_desc,weekday ,suit  ,lunar_year,lunar ,date ,"
						+"wuxing,chongsha,xiongshen ,baiji ,jishen,icon  ,pic,pic_url ,create_time,update_time,san_url ,ser_key_id,desc_url";

	
	
	@Override
	public List<Almanac> getAllAlmanac(int aid) {
		Map<String , Object> paramMap = new HashMap<String , Object>();
		String sql = "select " + almanacParam + " from cms_almanac where status = 0 and aid=:aid";
		paramMap.put("aid", aid);
		List<Almanac> almanacList = new ArrayList<Almanac>();
		try {
			almanacList = this.getNamedParameterJdbcTemplate().query(sql, paramMap, new RowMapper<Almanac>(){
				@Override
				public Almanac mapRow(ResultSet rs, int rowNum) throws SQLException {
					Almanac almanac = new Almanac();
					almanac.setAid(rs.getLong("aid"));
					almanac.setAlmDesc(rs.getString("alm_desc"));
					almanac.setAnimalsYear(rs.getString("animals_year"));
					almanac.setAvoid(rs.getString("avoid"));
					almanac.setBaiji(rs.getString("baiji"));
					almanac.setChongsha(rs.getString("chongsha"));
					almanac.setDate(rs.getDate("date").getTime());
					almanac.setDescUrl(rs.getString("desc_url"));
					almanac.setHoliday(rs.getString("holiday"));
					almanac.setJishen(rs.getString("jishen"));
					almanac.setLunar(rs.getString("lunar"));
					almanac.setLunarYear(rs.getString("lunar_year"));
					almanac.setSuit(rs.getString("suit"));
					almanac.setWeekday(rs.getString("weekday"));
					almanac.setWuxing(rs.getString("wuxing"));
					almanac.setXiongshen(rs.getString("xiongshen"));
					return almanac;
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return almanacList;
	}

}
