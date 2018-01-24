package com.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 设置缓存
     *
     * @param key 键
     * @param value 值
     * @param time 有效时长
     * @param timeUnit 单位
     */
    public void setCache(String key, String value, Long time, TimeUnit timeUnit) {
        adjustSerializer();
        if (null == time || time < 1L) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
        }
    }

    /**
     * 根据key获取缓存值
     *
     * @param key
     * @return
     */
    public String getCache(String key) {
        adjustSerializer();
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key移除缓存
     */
    public void delCache(String key) {
        adjustSerializer();
        redisTemplate.delete(key);
    }

    private void adjustSerializer() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<String>(String.class));
    }
}
