package com.yitianyike.calendar.pullserver.handler;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.yitianyike.calendar.pullserver.bo.AidTypeBO;
import com.yitianyike.calendar.pullserver.bo.AlmanacBO;
import com.yitianyike.calendar.pullserver.bo.AlmanacZipBO;
import com.yitianyike.calendar.pullserver.bo.ControlDriveBO;
import com.yitianyike.calendar.pullserver.bo.FootBasketBO;
import com.yitianyike.calendar.pullserver.bo.StarBO;
import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.bo.VideoBO;
import com.yitianyike.calendar.pullserver.model.DataCache;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;

public class CardDataHandler {
	private AlmanacBO almanacBO = (AlmanacBO) DataAccessFactory.dataHolder().get("almanacBO");
	private VideoBO videoBO = (VideoBO) DataAccessFactory.dataHolder().get("videoBO");

	private StarBO starBO = (StarBO) DataAccessFactory.dataHolder().get("starBO");

	private ControlDriveBO controlDriveBO = (ControlDriveBO) DataAccessFactory.dataHolder().get("controlDriveBO");
	private TodayOnHistoryBO todayOnHistoryBO = (TodayOnHistoryBO) DataAccessFactory.dataHolder()
			.get("todayOnHistoryBO");
	private FootBasketBO footBasketBO = (FootBasketBO) DataAccessFactory.dataHolder().get("footBasketBO");
	private AidTypeBO aidTypeBO = (AidTypeBO) DataAccessFactory.dataHolder().get("aidTypeBO");
	private AlmanacZipBO almanacZipBO = (AlmanacZipBO) DataAccessFactory.dataHolder().get("almanacZipBO");

	private Map<String, String> parmMap;
	private ChannelHandlerContext ctx;
	private String content;

	public CardDataHandler(ChannelHandlerContext ctx, Map<String, String> map, String content) {
		this.parmMap = map;
		this.ctx = ctx;
		this.content = content;
	}

	void putAlmanac() {
		int[] setAlmanac = almanacBO.setAlmanac(parmMap);
		Map<String, Object> response = new HashMap<String, Object>();
		if (setAlmanac.length != 0) {
			response.put("mes", "success");
			response.put("code", 0);
		} else {
			response.put("mes", "error");
			response.put("code", 1);
		}

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(response).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	void putVideo() {
		Map<String, Object> response = new HashMap<String, Object>();
		String setVideo = videoBO.setVideo(parmMap);
		response.put("mes", setVideo);
		response.put("code", 1);
		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(response).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	// 星座
	public void pressInStar() {
		List<DataCache> pressInStar = starBO.pressInStar(parmMap);
		Map<String, Object> response = new HashMap<String, Object>();

		// 显示返回
		List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

		for (DataCache dc : pressInStar) {
			Map<String, String> rMap = new HashMap<String, String>();
			rMap.put("key", dc.getKey());
			rMap.put("field", dc.getField());
			rMap.put("value", dc.getValue());
			responseDatas.add(rMap);
		}
		response.put("mes", "success");
		response.put("code", 0);
		response.put("data", responseDatas);

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(response).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	// 限行
	public void pressInControlDrive() {

		List<DataCache> pressInControlDrive = controlDriveBO.pressInControlDrive(parmMap);
		Map<String, Object> response = new HashMap<String, Object>();

		// 显示返回
		List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

		for (DataCache dc : pressInControlDrive) {
			Map<String, String> rMap = new HashMap<String, String>();
			rMap.put("key", dc.getKey());
			rMap.put("field", dc.getField());
			rMap.put("value", dc.getValue());
			responseDatas.add(rMap);
		}
		response.put("mes", "success");
		response.put("code", 0);
		response.put("data", responseDatas);

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(response).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	// 历史上的今天
	public void pressInTodayOnHistory() {

		List<DataCache> pressInTodayOnHistory = todayOnHistoryBO.pressInTodayOnHistory(parmMap);
		Map<String, Object> response = new HashMap<String, Object>();

		// 显示返回
		List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

		for (DataCache dc : pressInTodayOnHistory) {
			Map<String, String> rMap = new HashMap<String, String>();
			rMap.put("key", dc.getKey());
			rMap.put("field", dc.getField());
			rMap.put("value", dc.getValue());
			responseDatas.add(rMap);
		}
		response.put("mes", "success");
		response.put("code", 0);
		response.put("data", responseDatas);

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(response).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	// 足球和篮球
	public void pressInFootBasket() {

		List<DataCache> pressInTodayOnHistory = footBasketBO.pressInFootBasket(parmMap);
		Map<String, Object> response = new HashMap<String, Object>();

		// 显示返回
		List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

		for (DataCache dc : pressInTodayOnHistory) {
			Map<String, String> rMap = new HashMap<String, String>();
			rMap.put("key", dc.getKey());
			rMap.put("field", dc.getField());
			rMap.put("value", dc.getValue());
			responseDatas.add(rMap);
		}
		response.put("mes", "success");
		response.put("code", 0);
		response.put("data", responseDatas);

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(response).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	// aid非场景类型缓存
	public void pressInAidType() {

		List<DataCache> pressInTodayOnHistory = aidTypeBO.pressInAidType(parmMap);
		Map<String, Object> response = new HashMap<String, Object>();

		// 显示返回
		List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

		for (DataCache dc : pressInTodayOnHistory) {
			Map<String, String> rMap = new HashMap<String, String>();
			rMap.put("key", dc.getKey());
			rMap.put("field", dc.getField());
			rMap.put("value", dc.getValue());
			responseDatas.add(rMap);
		}
		response.put("mes", "success");
		response.put("code", 0);
		response.put("data", responseDatas);

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(response).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	public void pressInAlmanacZip() {

		List<DataCache> pressInTodayOnHistory = almanacZipBO.pressInAlmanacZip(parmMap);
		Map<String, Object> response = new HashMap<String, Object>();

		// 显示返回
		List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

		for (DataCache dc : pressInTodayOnHistory) {
			Map<String, String> rMap = new HashMap<String, String>();
			rMap.put("key", dc.getKey());
			rMap.put("field", dc.getField());
			rMap.put("value", dc.getValue());
			responseDatas.add(rMap);
		}
		response.put("mes", "success");
		response.put("code", 0);
		response.put("data", responseDatas);

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(response).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

	public void pressInFestivalZip() {

		List<DataCache> pressInTodayOnHistory = almanacZipBO.pressInAlmanacZip(parmMap);
		Map<String, Object> response = new HashMap<String, Object>();

		// 显示返回
		List<Map<String, String>> responseDatas = new ArrayList<Map<String, String>>();

		for (DataCache dc : pressInTodayOnHistory) {
			Map<String, String> rMap = new HashMap<String, String>();
			rMap.put("key", dc.getKey());
			rMap.put("field", dc.getField());
			rMap.put("value", dc.getValue());
			responseDatas.add(rMap);
		}
		response.put("mes", "success");
		response.put("code", 0);
		response.put("data", responseDatas);

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(response).toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}

}
