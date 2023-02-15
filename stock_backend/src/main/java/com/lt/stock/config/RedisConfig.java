package com.lt.stock.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/2/14 20:19
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    // 获取RedisTemplate  需要导入Redis的连接工厂 对象
    public RedisTemplate<Object, Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 获取RedisTemplate对象
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        // 进行序列化设置 (默认的序列化 是jdk格式的  我们在Linux中使用的是String/hash/list/set/zset格式的)
        // 更改序列化设置 支持String格式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 更改序列化设置 支持Hash结构
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
