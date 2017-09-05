package com.yitianyike.calendar.pullserver.handler;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpMethod.POST;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.util.CharsetUtil;

import java.util.Map;

import org.apache.log4j.Logger;

import com.yitianyike.calendar.pullserver.model.DataCache;

/**
 * @author pineapple Handles handshakes and messages
 */
public class BusinessHandler extends SimpleChannelInboundHandler<HttpObject> {
	private static final Logger logger = Logger.getLogger(BusinessHandler.class);

	private volatile int count = 0;

	@Override
	public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest) msg);
		} else {

		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
		// Handle a bad request.
		if (!req.getDecoderResult().isSuccess()) {
			ResponseGenerator.sendHttpResponse(ctx, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
			return;
		}

		// // Allow only GET methods.
		// if (req.getMethod() == POST) {
		// //logger.info("req content:"+req.content().toString());
		// if("/user".equals(req.getUri())){
		// logger.info("req content:"+req.content().toString());
		// FullHttpResponse res = new
		// DefaultFullHttpResponse(HTTP_1_1,io.netty.handler.codec.http.HttpResponseStatus.OK);
		// sendHttpResponse(ctx, req, res);
		// return;
		// }
		//
		// DispatchResult<?> dispatchResult = DispatcherFactory.dispatcher(req);
		// if (dispatchResult != null && !dispatchResult.isInvalid()) {
		// ctx.pipeline().addLast("consultDoctor",
		// dispatchResult.getNextHandler());
		// ctx.fireChannelRead(dispatchResult.getMessage());
		// return;
		// }
		// return;
		// }

		Map<String, String> parmMap = new RequestParser(req).parse(); // 将Header,
																		// GET,
																		// POST所有请求参数转换成Map对象

		System.out.println(parmMap);

		String accessPath = req.getUri();
		if (accessPath.indexOf("?") >= 0) {
			accessPath = accessPath.substring(0, accessPath.indexOf("?"));
		}

		String content = req.content().toString(CharsetUtil.UTF_8);

		// System.out.println(accessPath);

		if (req.getMethod() == GET || req.getMethod() == POST) {
			// Send the demo page and favicon.ico
			// System.out.println("req type:" + req.getMethod() + ", uri:" +
			// req.getUri());
			if ("/user".equals(accessPath)) {
				logger.info("req content:" + req.content().toString());
				FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1,
						io.netty.handler.codec.http.HttpResponseStatus.OK);
				ResponseGenerator.sendHttpResponse(ctx, res);
				return;
			}
			if ("/".equals(accessPath)) {
				logger.info("req content:" + req.content().toString());
				FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1,
						io.netty.handler.codec.http.HttpResponseStatus.OK);
				ResponseGenerator.sendHttpResponse(ctx, res);
				return;
			}
			if ("/favicon.ico".equals(accessPath)) {
				FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
				ResponseGenerator.sendHttpResponse(ctx, res);
				return;
			}

			/*
			 * if ("/test".equals(accessPath)) {
			 * 
			 * new TestHandler(ctx, parmMap, content).process(); return; }
			 * 
			 * else if("/register".equals(accessPath)){ /* if
			 * ("/test".equals(accessPath)) {
			 * 
			 * new TestHandler(ctx, parmMap, content).process(); return; }
			 * 
			 * else if("/register".equals(accessPath)){
			 * 
			 * new RegisterHandler(ctx, parmMap, content).process(); return; }
			 */
			else if ("/subscribedList".equals(accessPath)) {
				new DataCacheHandler(ctx, parmMap, content).organizeSubscribedList();
				return;
			} else if ("/tabsList".equals(accessPath)) {
				new DataCacheHandler(ctx, parmMap, content).organizeTabsList();
				return;
			} else if ("/recommendList".equals(accessPath)) {
				new DataCacheHandler(ctx, parmMap, content).organizeRecommendSubscribeList();
				return;
			} else if ("/completeList".equals(accessPath)) {
				// 全部列表
				new CompleteHandler(ctx, parmMap, content).process();
				return;
			} else if ("/aidtype".equals(accessPath)) {
				// aid 类型
				new CardDataHandler(ctx, parmMap, content).pressInAidType();
				return;
			} else if ("/starList".equals(accessPath)) {
				// 星座卡片数据
				new CardDataHandler(ctx, parmMap, content).pressInStar();
				return;
			} else if ("/controlDriveList".equals(accessPath)) {
				// 限行卡片数据
				new CardDataHandler(ctx, parmMap, content).pressInControlDrive();
				return;
			} else if ("/todayOnHistory".equals(accessPath)) {
				// 历史上的今天卡片数据
				new CardDataHandler(ctx, parmMap, content).pressInTodayOnHistory();
				return;
			} else if ("/footballAndBasketball".equals(accessPath)) {
				// 足球和篮球
				new CardDataHandler(ctx, parmMap, content).pressInFootBasket();
				return;
			} else if ("/almanacZip".equals(accessPath)) {
				// 黄历
				new CardDataHandler(ctx, parmMap, content).pressInAlmanacZip();
				return;
			} else if ("/festivalZip".equals(accessPath)) {
				// 节日
				new CardDataHandler(ctx, parmMap, content).pressInFestivalZip();
				return;
			} else if ("/lottery".equals(accessPath)) {
				// 彩票
				new CardDataHandler(ctx, parmMap, content).pressInLottery();
				return;
			} else if ("/newsList".equals(accessPath)) {
				// 资讯
				new CardDataHandler(ctx, parmMap, content).pressInNews();
				return;
			} else if ("/sceneList".equals(accessPath)) {
				// 场景
				new CardDataHandler(ctx, parmMap, content).pressInScene();
				return;
			} else if ("/filmList".equals(accessPath)) {
				// 电影
				new CardDataHandler(ctx, parmMap, content).pressInFilm();
				return;
			} else if ("/picture".equals(accessPath)) {
				// 一天美图
				new CardDataHandler(ctx, parmMap, content).pressInPicture();
				return;
			}
			// } else if ("/videoList".equals(accessPath)) {
			// 视频卡片数据
			// new CardDataHandler(ctx, parmMap, content).putVideo();
			// return;
			// } else if ("/sportList".equals(accessPath)) {
			// new DataCacheHandler(ctx, parmMap,
			// content).flushSportCardData();
			// 订阅的列表的下级页面
			// }
			// else if ("/lowerPageList".equals(accessPath)) {
			// new DataCacheHandler(ctx, parmMap,
			// content).flushLowerPageList();

			if (count > 0) {
				logger.info("count = " + count);
				ResponseGenerator.sendHttpResponse(ctx, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
				return;
			}
		} else {
			logger.info("req fail!");
		}

		ResponseGenerator.sendHttpResponse(ctx, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
		count++;
	}

	// private static void sendHttpResponse(ChannelHandlerContext ctx,
	// FullHttpRequest req, FullHttpResponse res) {
	// // Generate an error page if response getStatus code is not OK (200).
	// if (res.getStatus().code() == 200) {
	// ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
	// CharsetUtil.UTF_8);
	// res.content().writeBytes(buf);
	// buf.release();
	// setContentLength(res, res.content().readableBytes());
	// }
	//
	// // Send the response and close the connection if necessary.
	// ChannelFuture f = ctx.channel().writeAndFlush(res);
	// if (!isKeepAlive(req) || res.getStatus().code() != 200) {
	// f.addListener(ChannelFutureListener.CLOSE);
	// }
	// }

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(cause.getMessage(), cause);
		ctx.close();
	}

}
