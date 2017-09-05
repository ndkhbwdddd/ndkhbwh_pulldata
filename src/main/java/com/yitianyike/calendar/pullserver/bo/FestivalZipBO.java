package com.yitianyike.calendar.pullserver.bo;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.DataCache;

public interface FestivalZipBO {


	public List<DataCache> pressInFestivalZip(Map<String, String> parmMap);
}
