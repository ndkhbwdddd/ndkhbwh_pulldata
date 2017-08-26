package com.yitianyike.calendar.pullserver.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yitianyike.calendar.pullserver.dao.ColumnExtendDAO;
import com.yitianyike.calendar.pullserver.model.ColumnExtend;

@Component("columnExtendDAO")
public class ColumnExtendDAOImpl extends BaseDAO implements ColumnExtendDAO {

	private String coleParams = "c.id,c.column_id,c.anchor_id,c.start_time,c.end_time ,c.create_name  ,c.create_time  ,c.update_name  ,c.update_time";
	
	@Override
	public List<ColumnExtend> getExtendByCid(int id) {
		Map<String , Object> paramMap = new HashMap<String , Object>();
		String sql  = "select "+coleParams+ " , a.card_style,a.quote_name,la.little_type,"
	    +"la.type,a.frequence,a.exp_date_time from cms_column_extend c , cms_anchor a ,	cms_layout la where c.column_id =:columnId AND c.anchor_id = a.anchor_id AND la.l_id = a.layout_id AND a.`status` = 1";
		paramMap.put("columnId", id);
		List<ColumnExtend> ceList = new ArrayList<ColumnExtend>();
		try {
			ceList = this.getNamedParameterJdbcTemplate().query(sql, paramMap, new RowMapper<ColumnExtend>() {
				@Override
				public ColumnExtend mapRow(ResultSet rs, int rowNum) throws SQLException {
					ColumnExtend columnExtend = new ColumnExtend();
					columnExtend.setAnchorId(rs.getInt("anchor_id"));
					columnExtend.setStartTime(rs.getLong("start_time"));
					columnExtend.setEndTime(rs.getLong("end_time"));
					columnExtend.setCardStyle(rs.getInt("card_style"));
					columnExtend.setQuoteName(rs.getString("quote_name"));
					columnExtend.setLittleType(rs.getInt("little_type"));
					columnExtend.setType(rs.getInt("type"));
					columnExtend.setFrequence(rs.getString("frequence"));
					columnExtend.setExpDateTime(rs.getLong("exp_date_time"));
					return columnExtend;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return ceList;
	}

}
