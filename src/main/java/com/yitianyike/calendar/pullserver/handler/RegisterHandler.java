package com.yitianyike.calendar.pullserver.handler;


import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.Map;

import org.apache.log4j.Logger;

import com.yitianyike.calendar.pullserver.common.EnumConstants;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.dao.impl.RedisDAOImpl;
import com.yitianyike.calendar.pullserver.model.AuthAccount;
import com.yitianyike.calendar.pullserver.service.DBContextHolder;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;
import com.yitianyike.calendar.pullserver.util.PropertiesUtil;

/**
 * 用户登录请求
 * @author xujinbo
 *
 */
public class RegisterHandler {
  /*  private static Logger logger = Logger.getLogger(RegisterHandler.class);
    private UserBO userBO = (UserBO) DataAccessFactory.dataHolder().get("userBO");

    private Map<String, String> parmMap;
    private ChannelHandlerContext ctx;
    private String content;
    
    public RegisterHandler(ChannelHandlerContext ctx, Map<String, String> map, String content) {
    	this.parmMap = map;
    	this.ctx = ctx;
    	this.content = content;
    }  

    void process() {
    	logger.info(EnumConstants.COLOR_RED + "register handler content : " + content + EnumConstants.COLOR_NONE);
        
    	userBO.processRegister(parmMap, content);
    	
    	String response = "thank you!";
		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1,io.netty.handler.codec.http.HttpResponseStatus.OK, Unpooled.wrappedBuffer(response.getBytes()));
		ResponseGenerator.sendHttpResponse(ctx,  res);
		
		logger.info(EnumConstants.COLOR_RED + "register handler resp : " + response + EnumConstants.COLOR_NONE);
		
    }*/
    
}
