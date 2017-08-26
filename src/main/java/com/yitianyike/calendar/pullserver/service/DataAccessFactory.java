package com.yitianyike.calendar.pullserver.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yitianyike.calendar.pullserver.bo.AidTypeBO;
import com.yitianyike.calendar.pullserver.bo.AlmanacBO;
import com.yitianyike.calendar.pullserver.bo.AlmanacZipBO;
import com.yitianyike.calendar.pullserver.bo.CompleteBO;
import com.yitianyike.calendar.pullserver.bo.ControlDriveBO;
import com.yitianyike.calendar.pullserver.bo.DataCacheBO;
import com.yitianyike.calendar.pullserver.bo.FootBasketBO;
import com.yitianyike.calendar.pullserver.bo.RecommendSubscribeListBO;
import com.yitianyike.calendar.pullserver.bo.SportCardDataBO;
import com.yitianyike.calendar.pullserver.bo.StarBO;
import com.yitianyike.calendar.pullserver.bo.SubscribedListBO;
import com.yitianyike.calendar.pullserver.bo.TabsListBO;
import com.yitianyike.calendar.pullserver.bo.TodayOnHistoryBO;
import com.yitianyike.calendar.pullserver.bo.VideoBO;
import com.yitianyike.calendar.pullserver.dao.AlmanacDAO;
import com.yitianyike.calendar.pullserver.dao.CardDataDao;
import com.yitianyike.calendar.pullserver.dao.ColumnDAO;
import com.yitianyike.calendar.pullserver.dao.ColumnExtendDAO;
import com.yitianyike.calendar.pullserver.dao.DataCacheDAO;
import com.yitianyike.calendar.pullserver.dao.PackageDAO;
import com.yitianyike.calendar.pullserver.dao.RedisDAO;
import com.yitianyike.calendar.pullserver.dao.VideoDAO;

public class DataAccessFactory {

	private static Logger log = Logger.getLogger(DataAccessFactory.class);

	private static ApplicationContext mysqlDataCtxXml = null;
	private static ApplicationContext mysqlCmsCtxXml = null;
	private static ApplicationContext redisCtxXml = null;
	private static Map<String, Object> dataHolder = new HashMap<String, Object>();

	public static void initDataAccessMysqlDataByXML() {

		log.info("init Data Access Objects[Mysql Data] start...");
		mysqlDataCtxXml = new ClassPathXmlApplicationContext("spring-mysql-data.xml");
		dataHolder.put("dataCacheDAO", (DataCacheDAO) mysqlDataCtxXml.getBean("dataCacheDAO"));
		// TODO
		log.info("init Data Access Objects[Mysql Data] over.");
	}

	public static void initDataAccessMysqlUserByXML() {

		log.info("init Data Access Objects[Mysql User] start...");
		mysqlCmsCtxXml = new ClassPathXmlApplicationContext("spring-mysql-cms.xml");
		dataHolder.put("packageDAO", (PackageDAO) mysqlCmsCtxXml.getBean("packageDAO"));
		dataHolder.put("almanacDAO", (AlmanacDAO) mysqlCmsCtxXml.getBean("almanacDAO"));
		dataHolder.put("columnDAO", (ColumnDAO) mysqlCmsCtxXml.getBean("columnDAO"));
		dataHolder.put("videoDAO", (VideoDAO) mysqlCmsCtxXml.getBean("videoDAO"));
		dataHolder.put("columnExtendDAO", (ColumnExtendDAO) mysqlCmsCtxXml.getBean("columnExtendDAO"));
		dataHolder.put("cardDataDao", (CardDataDao) mysqlCmsCtxXml.getBean("cardDataDao"));
		dataHolder.put("completeBO", (CompleteBO) mysqlCmsCtxXml.getBean("completeBO"));
		dataHolder.put("almanacBO", (AlmanacBO) mysqlCmsCtxXml.getBean("almanacBO"));
		// dataHolder.put("videoBO", (VideoBO)
		// mysqlCmsCtxXml.getBean("videoBO"));
		dataHolder.put("subscribedListBO", (SubscribedListBO) mysqlCmsCtxXml.getBean("subscribedListBO"));
		dataHolder.put("tabsListBO", (TabsListBO) mysqlCmsCtxXml.getBean("tabsListBO"));
		dataHolder.put("dataCacheBO", (DataCacheBO) mysqlCmsCtxXml.getBean("dataCacheBO"));
		dataHolder.put("sportCardDataBO", (SportCardDataBO) mysqlCmsCtxXml.getBean("sportCardDataBO"));
		dataHolder.put("recommendSubscribeListBO",
				(RecommendSubscribeListBO) mysqlCmsCtxXml.getBean("recommendSubscribeListBO"));
		dataHolder.put("starBO", (StarBO) mysqlCmsCtxXml.getBean("starBO"));
		dataHolder.put("controlDriveBO", (ControlDriveBO) mysqlCmsCtxXml.getBean("controlDriveBO"));
		dataHolder.put("todayOnHistoryBO", (TodayOnHistoryBO) mysqlCmsCtxXml.getBean("todayOnHistoryBO"));
		dataHolder.put("footBasketBO", (FootBasketBO) mysqlCmsCtxXml.getBean("footBasketBO"));
		dataHolder.put("aidTypeBO", (AidTypeBO) mysqlCmsCtxXml.getBean("aidTypeBO"));
		dataHolder.put("almanacZipBO", (AlmanacZipBO) mysqlCmsCtxXml.getBean("almanacZipBO"));
		log.info("init Data Access Objects[Mysql User] over.");
	}

	public static void initDataAccessRedisByXML() {

		log.info("init Data Access Objects[Redis] start...");
		redisCtxXml = new ClassPathXmlApplicationContext("spring-redis.xml");
		dataHolder.put("redisDAO", (RedisDAO) redisCtxXml.getBean("redisDAO"));
		// TODO
		log.info("init Data Access Objects[Redis] over.");
	}

	public static ApplicationContext getRedisCtxXml() {
		return redisCtxXml;
	}

	public static Map<String, Object> dataHolder() {
		return dataHolder;
	}


}
