package com.yitianyike.calendar.pullserver.bo;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

public interface SportCardDataBO {

	List<Map<String, Object>> organizeCardData(Map<String, String> paramMap);

	List<Map<String, Object>> flushSportCardData(String channel_code);

}
