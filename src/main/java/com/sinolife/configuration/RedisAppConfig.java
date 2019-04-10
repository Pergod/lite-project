package com.sinolife.configuration;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 * @author Flystar
 *
 */
@Configuration
@EnableCaching
public class RedisAppConfig {
	private static final Logger logger = LoggerFactory.getLogger(RedisAppConfig.class);
	
	/**
	 * 集群配置
	 * @return
	 */
//	@Bean
//    public JedisConnectionFactory redisConnectionFactory() {
//		logger.info(("加载cluster环境下的redis client配置"));
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(Arrays.asList(
//                "127.0.0.1:6379"
//        ));
//        // 自适应集群变化
//        return new JedisConnectionFactory(redisClusterConfiguration);
//    }
//	
	/**
	 * 单机配置
	 * @return
	 */
	@Bean
    public LettuceConnectionFactory redisConnectionFactory() {
		logger.info("使用单机版本");
		return new LettuceConnectionFactory("127.0.0.1", 6379);
    }


	
	@Bean
	public RedisTemplate<String, Map<String, Object>> redisTemplate(JedisConnectionFactory jf){
		RedisTemplate<String, Map<String, Object>> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jf);
		return redisTemplate;
	}
	
	
	// 可以配置对象的转换规则，比如使用json格式对object进行存储。
    // Object --> 序列化 --> 二进制流 --> redis-server存储
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }

	
//	 配置Spring Cache注解功能
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
//        return cacheManager;
//    }


}
