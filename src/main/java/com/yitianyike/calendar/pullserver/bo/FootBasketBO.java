package com.yitianyike.calendar.pullserver.bo;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.DataCache;

public interface FootBasketBO {

	public List<DataCache> pressInFootBasket(Map<String, String> parmMap);
}
