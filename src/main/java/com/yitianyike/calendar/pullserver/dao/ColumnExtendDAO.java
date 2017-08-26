package com.yitianyike.calendar.pullserver.dao;

import java.util.List;

import com.yitianyike.calendar.pullserver.model.ColumnExtend;

public interface ColumnExtendDAO {

	/**
	 * 根据栏目id查询
	 * @param id
	 * @return
	 */
	public List<ColumnExtend> getExtendByCid(int id);

}
