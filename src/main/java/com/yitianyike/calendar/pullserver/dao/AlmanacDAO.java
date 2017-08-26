package com.yitianyike.calendar.pullserver.dao;

import java.util.List;

import com.yitianyike.calendar.pullserver.model.Almanac;

public interface AlmanacDAO {

	/**
	 * 查询所有黄历
	 * @param i 
	 * @return
	 */
	public List<Almanac> getAllAlmanac(int i);

}
