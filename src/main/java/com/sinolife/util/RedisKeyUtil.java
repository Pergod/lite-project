package com.sinolife.util;

/**
 * 缓存key工具类
 * @author Flystar
 *
 */
public class RedisKeyUtil {
	private static String SPLIT=":";
	//状态Key
	private static String PUB_STATE_DETAILS="PUB_STATE_DETAILS";
	//条线key
	private static String PUB_BUSINESS_DETAILS="PUB_BUSINESS_DETAILS";
    
	public static String getPublishStateDetailsKey(int entityType,int entityId) {
		return PUB_STATE_DETAILS + SPLIT + String.valueOf(entityType) + SPLIT +String.valueOf(entityId);
	}
	
	public static String getPublishBusinessDetailsKey(int entityType,int entityId) {
		return PUB_BUSINESS_DETAILS + SPLIT + String.valueOf(entityType) + SPLIT +String.valueOf(entityId);
	}
	
}
