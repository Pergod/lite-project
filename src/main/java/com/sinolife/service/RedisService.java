package com.sinolife.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
/**
 * 緩存service
 * @author Flystar
 *
 */
@Service
public class RedisService {
	
	@Autowired
	private RedisTemplate<String, Map<String, Object>> redisTemplate;
	
	public void set(String key, Map<String, Object> result) {
		redisTemplate.opsForValue().set(key, result);
	}
	
	public Map<String, Object> get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	public void remove(String key){
		redisTemplate.delete(key);
	}
	

}
