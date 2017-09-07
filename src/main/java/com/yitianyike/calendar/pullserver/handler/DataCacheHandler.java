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

	private TabsListBO tabsListBO = (TabsListBO) DataAccessFactory.dataHolder().get("tabsListBO");

	//
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
		String reString = "";
		try {
			parmMap.put("version", PropertiesUtil.version);
			List<Bcolumn> subscribedList = subscribedListBO.subscribedList(parmMap);

			for (Bcolumn bcolumn : subscribedList) {
				reString += bcolumn.getSave_value() + ",";
			}
			if (reString.length() > 0) {
				String substring = reString.substring(0, reString.length() - 1);
				reString = "[" + substring + "]";
			}
		} catch (Exception e) {
		}

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(reString.getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	public void organizeRecommendSubscribeList() {
		logger.info(EnumConstants.COLOR_RED + "register handler content : " + content + EnumConstants.COLOR_NONE);
		String recommendSubscribeListJson = "";
		try {
			recommendSubscribeListJson = recommendSubscribeListBO.organizeRecommendSubscribeList(parmMap);
			
		} catch (Exception e) {
		}

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(recommendSubscribeListJson.getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

//	public void flushSportCardData() {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		try {
//			List<Map<String, Object>> organizeCardData = sportCardDataBO.organizeCardData(parmMap);
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
//
//	}

	public void organizeTabsList() {
		logger.info(EnumConstants.COLOR_RED + "tabs handler content : " + content + EnumConstants.COLOR_NONE);
		String tabs = "";
		try {
			parmMap.put("version", PropertiesUtil.version);
			tabs = tabsListBO.tabsList(parmMap);

		} catch (Exception e) {
		}

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(tabs.getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	// public void flushLowerPageList() {
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	// try {
	// List<Map<String, Object>> organizeCardData =
	// sportCardDataBO.organizeCardData(parmMap);
	//
	//
	//
	// parmMap.put("version", PropertiesUtil.version);
	// dataCacheBO.insertCardList(organizeCardData, parmMap);
	// resultMap.put("code", 1);
	// resultMap.put("msg", "success");
	// } catch (Exception e) {
	// resultMap.put("code", 0);
	// resultMap.put("msg", "error");
	// }
	// FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1,
	// io.netty.handler.codec.http.HttpResponseStatus.OK,
	// Unpooled.wrappedBuffer(JSONObject.fromObject(resultMap).toString().getBytes()));
	// ResponseGenerator.sendHttpResponse(ctx, res);
	// }

}
