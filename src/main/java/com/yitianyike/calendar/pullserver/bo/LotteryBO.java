package com.yitianyike.calendar.pullserver.bo;

import java.util.List;
import java.util.Map;

import com.yitianyike.calendar.pullserver.model.DataCache;

public interface LotteryBO {

	public List<DataCache> pressInLottery(Map<String, String> parmMap);


}
