package com.yitianyike.calendar.pullserver.handler;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.yitianyike.calendar.pullserver.bo.AlmanacBO;
import com.yitianyike.calendar.pullserver.bo.CompleteBO;
import com.yitianyike.calendar.pullserver.bo.VideoBO;
import com.yitianyike.calendar.pullserver.common.EnumConstants;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.dao.impl.RedisDAOImpl;
import com.yitianyike.calendar.pullserver.model.AuthAccount;
import com.yitianyike.calendar.pullserver.service.DBContextHolder;
import com.yitianyike.calendar.pullserver.service.DataAccessFactory;

/**
 * 用户登录请求
 * @author xujinbo
 *
 */
public class TestHandler {
	
	 private VideoBO videoBO = (VideoBO) DataAccessFactory.dataHolder().get("videoBO");

	    private Map<String, String> parmMap;
	    private ChannelHandlerContext ctx;
	    private String content;
	    
	    public TestHandler(ChannelHandlerContext ctx, Map<String, String> map, String content) {
	    	this.parmMap = map;
	    	this.ctx = ctx;
	    	this.content = content;
	    }  

	    void process() {
	        
	    	videoBO.setVideo(parmMap);
	    	
	    	String response = "thank you!";
			FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1,io.netty.handler.codec.http.HttpResponseStatus.OK, Unpooled.wrappedBuffer(response.getBytes()));
			ResponseGenerator.sendHttpResponse(ctx,  res);
			
	    }
   /* private static Logger logger = Logger.getLogger(TestHandler.class);
    private UserBO userBO = (UserBO) DataAccessFactory.dataHolder().get("userBO");

    private Map<String, String> parmMap;
    private ChannelHandlerContext ctx;
    private String content;
    
    public TestHandler(ChannelHandlerContext ctx, Map<String, String> map, String content) {
    	this.parmMap = map;
    	this.ctx = ctx;
    	this.content = content;
    }  

    void process() {
    	logger.info(EnumConstants.COLOR_RED + "test handler content : " + content + EnumConstants.COLOR_NONE);
        
        UserDAO userDAO = (UserDAO) DataAccessFactory.dataHolder().get("userDAO");
		
		DBContextHolder.setDBType("dataSource0");
		AuthAccount account = userDAO.getAccountInfo("1");
		
		if(account != null){
			System.out.println("uid:" + account.getUid());
		}
		
		
		
		DBContextHolder.setDBType("dataSource1");
		AuthAccount account2 = userDAO.getAccountInfo("2");
		if(account2 != null){
			System.out.println("uid:" + account.getUid());
		}
		
		RedisDAOImpl.setRedisTemplate("redisTemplate0");
		RedisDAO redisDAO = (RedisDAO) DataAccessFactory.dataHolder().get("redisDAO");
		redisDAO.setKeyValue("name1", "china");
		
		RedisDAOImpl.setRedisTemplate("redisTemplate1");
		redisDAO.setKeyValue("name2", "beijing");
		
        userBO.clientGetUser("userid");
        
		String response = "thank you!";
		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1,io.netty.handler.codec.http.HttpResponseStatus.OK, Unpooled.wrappedBuffer(response.getBytes()));
		ResponseGenerator.sendHttpResponse(ctx,  res);
		
		logger.info(EnumConstants.COLOR_RED + "test handler resp : " + response + EnumConstants.COLOR_NONE);
        
    }
    public static void main(String[] args) throws Exception {
    	
    	String str = "0123";
    	int i = str.charAt(0) - 48;
    	int j = (str.charAt(0) - 48) * 1000 + (str.charAt(1) - 48 ) * 100 + (str.charAt(2) - 48) * 10 + (str.charAt(3) - 48);
    	int k = (-j)%10;
    	k = Math.abs(k);
    	System.out.println("i=" + i + ", j=" + j + ", k=" + k);
    	
    	System.out.println(System.currentTimeMillis());
    	
            UUID uuid = UUID.randomUUID();  
            String stra = uuid.toString();  
            // 去掉"-"符号  
            String temp = stra.substring(0, 8) + stra.substring(9, 13) + stra.substring(14, 18) + stra.substring(19, 23) + stra.substring(24);  
            System.out.println(temp);
    }*/
}
