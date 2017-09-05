package com.yitianyike.calendar.pullserver.handler;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.yitianyike.calendar.pullserver.bo.CompleteBO;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;

public class CompleteHandler {
	private CompleteBO completeBO = (CompleteBO) DataAccessFactory.dataHolder()
			.get("completeBO");

	private Map<String, String> parmMap;
	private ChannelHandlerContext ctx;
	private String content;

	public CompleteHandler(ChannelHandlerContext ctx, Map<String, String> map,
			String content) {
		this.parmMap = map;
		this.ctx = ctx;
		this.content = content;
	}

	void process() {
		List<Map<String, Object>> complete = completeBO.getComplete(
				parmMap.get("channel_code"));
		Map<String, Object> response = new HashMap<String, Object>();
		if (!complete.isEmpty()) {
			response.put("mes", "success");
			response.put("code", 1);
		} else {
			response.put("mes", "error");
			response.put("code", 0);
		}

		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1,
				io.netty.handler.codec.http.HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSONObject.fromObject(response)
						.toString().getBytes()));
		ResponseGenerator.sendHttpResponse(ctx, res);

	}
}
