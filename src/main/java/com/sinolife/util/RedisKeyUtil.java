package com.sinolife.util;

/**
 * 缓存key工具类
 * @author Flystar
 *
 */
public class RedisKeyUtil {
	private static String SPLIT=":";
	private static String PUB_DETAILS="PUB_DETAILS";
    
	public static String getPublishDetailsKey(int entityType,int entityId) {
		return PUB_DETAILS + SPLIT + String.valueOf(entityType) + SPLIT +String.valueOf(entityId);
	}
	
}
