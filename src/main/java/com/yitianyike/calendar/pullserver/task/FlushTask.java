//package com.yitianyike.calendar.pullserver.task;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.yitianyike.calendar.pullserver.bo.AlmanacBO;
//import com.yitianyike.calendar.pullserver.bo.VideoBO;
//import com.yitianyike.calendar.pullserver.dao.ColumnDAO;
//import com.yitianyike.calendar.pullserver.model.Flush;
//import com.yitianyike.calendar.pullserver.util.PropertiesUtil;
//
//@Component
//public class FlushTask {
//	@Autowired
//	private VideoBO videoBO;
//	@Autowired
//	private ColumnDAO columnDAO;
//	@Autowired
//	private AlmanacBO almanacBO;
//	
//	
//
//	//@Scheduled(cron = "0 0/2 * * * ?")
//	public void flushVideo(){
//		Map<String , String> paramMap = new HashMap<String, String>();
//		//List<Flush> columnList = columnDAO.getColumn( PropertiesUtil.channel_code);
//		paramMap.put("channel_code", PropertiesUtil.channel_code);
////		for (Flush flush : columnList) {
////			paramMap.put("cids", flush.getColumn_id()+"");
////			if(flush.getType() == 4){
////				videoBO.setVideo(paramMap);
////				videoBO.setDetails(paramMap);
////			}
////		}
//	}
//	
//	
//	
//}
