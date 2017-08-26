package com.yitianyike.calendar.pullserver.handler;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yitianyike.calendar.pullserver.bo.DataCacheBO;
import com.yitianyike.calendar.pullserver.bo.RecommendSubscribeListBO;
import com.yitianyike.calendar.pullserver.bo.SportCardDataBO;
import com.yitianyike.calendar.pullserver.bo.SubscribedListBO;
import com.yitianyike.calendar.pullserver.bo.TabsListBO;
import com.yitianyike.calendar.pullserver.common.EnumConstants;
import com.yitianyike.calendar.pullserver.dao.ColumnDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.model.response.SubscribedInfo;
import com.yitianyike.calendar.pullserver.model.responsedata.Bcolumn;
import com.yitianyike.calendar.pullserver.model.responsedata.Tabs;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import net.sf.json.JSONObject;

public class DataCacheHandler {
	private static Logger logger = Logger.getLogger(RegisterHandler.class);

	private SubscribedListBO subscribedListBO = (SubscribedListBO) DataAccessFactory.dataHolder()
			.get("subscribedListBO");
	private RecommendSubscribeListBO recommendSubscribeListBO = (RecommendSubscribeListBO) DataAccessFactory
			.dataHolder().get("recommendSubscribeListBO");
	
	private TabsListBO tabsListBO = (TabsListBO) DataAccessFactory
			.dataHolder().get("tabsListBO");
	
	
	private DataCacheBO dataCacheBO = (DataCacheBO) DataAccessFactory.dataHolder().get("dataCacheBO");
	private SportCardDataBO sportCardDataBO = (SportCardDataBO) DataAccessFactory.dataHolder().get("sportCardDataBO");

	private Map<String, String> parmMap;
	private ChannelHandlerContext ctx;
	private String content;

	public DataCacheHandler(ChannelHandlerContext ctx, Map<String, String> map, String content) {
		this.parmMap = map;
		this.ctx = ctx;
		this.content = content;
	}

	public void organizeSubscribedList() {
		logger.info(EnumConstants.COLOR_RED + "subscribedList handler content : " + content + EnumConstants.COLOR_NONE);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			parmMap.put("version", PropertiesUtil.version);
			List<Bcolumn> subscribedList = subscribedListBO.subscribedList(parmMap);

			StringBuilder deleteSbParam = new StringBuilder();
			deleteSbParam.append(parmMap.get("channel_code")).append("-").append(parmMap.get("version"))
					.append("-subscribed");
			dataCacheBO.insertSubscribedList(deleteSbParam.toString(), subscribedList);
			resultMap.put("code", 1);
			resultMap.put("msg", "success");
		} catch (Exception e) {
			System.out.println(e);
			resultMap.put("code", 0);
			resultMap.put("msg", "error");
		}

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(resultMap).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	public void organizeRecommendSubscribeList() {
		logger.info(EnumConstants.COLOR_RED + "register handler content : " + content + EnumConstants.COLOR_NONE);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String recommendSubscribeListJson = recommendSubscribeListBO.organizeRecommendSubscribeList(parmMap);
			StringBuilder deleteSbParam = new StringBuilder();
			deleteSbParam.append(parmMap.get("channel_code")).append("-").append(PropertiesUtil.version)
					.append("-recommend");
			dataCacheBO.insertRecommendSubscribeList(deleteSbParam.toString(), recommendSubscribeListJson);
			resultMap.put("code", 1);
			resultMap.put("msg", "success");
		} catch (Exception e) {
			resultMap.put("code", 0);
			resultMap.put("msg", "error");
		}

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(resultMap).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	public void flushSportCardData() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> organizeCardData = sportCardDataBO.organizeCardData(parmMap);
			parmMap.put("version", PropertiesUtil.version);
			dataCacheBO.insertCardList(organizeCardData, parmMap);
			resultMap.put("code", 1);
			resultMap.put("msg", "success");
		} catch (Exception e) {
			resultMap.put("code", 0);
			resultMap.put("msg", "error");
		}
		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(resultMap).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	public void organizeTabsList() {
		logger.info(EnumConstants.COLOR_RED + "tabs handler content : " + content + EnumConstants.COLOR_NONE);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			parmMap.put("version", PropertiesUtil.version);
			 String tabs = tabsListBO.tabsList(parmMap);

			StringBuilder deleteSbParam = new StringBuilder();
			deleteSbParam.append(parmMap.get("channel_code")).append("-").append(parmMap.get("version"))
					.append("-tabs");
			dataCacheBO.insertTabsList(deleteSbParam.toString(), tabs);
			resultMap.put("code", 1);
			resultMap.put("msg", "success");
		} catch (Exception e) {
			System.out.println(e);
			resultMap.put("code", 0);
			resultMap.put("msg", "error");
		}

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(resultMap).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

//	public void flushLowerPageList() {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		try {
//			List<Map<String, Object>> organizeCardData = sportCardDataBO.organizeCardData(parmMap);
//			
//			
//			
//			parmMap.put("version", PropertiesUtil.version);
//			dataCacheBO.insertCardList(organizeCardData, parmMap);
//			resultMap.put("code", 1);
//			resultMap.put("msg", "success");
//		} catch (Exception e) {
//			resultMap.put("code", 0);
//			resultMap.put("msg", "error");
//		}
//		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
//				Unpooled.wrappedBuffer(JSONObject.fromObject(resultMap).toString().getBytes()));
//		ResponseGenerator.sendHttpResponse(ctx, res);
//	}

}
