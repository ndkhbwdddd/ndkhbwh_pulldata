package com.yitianyike.calendar.pullserver.dao;

import java.util.List;

import com.yitianyike.calendar.pullserver.model.Video;

public interface VideoDAO {

	/**
	 * 根据锚点id查询
	 * @param aids
	 * @return
	 */
	public List<Video> getAllVideoByType(int aids);

	public List<Video> getVideoByType(Integer anchor_id);

}
