package com.lee.jblog.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class StringService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout){
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public boolean expire(String key, long timeout){
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }

    public String getAndSet(String key, Object value){
        return (String) redisTemplate.opsForValue().getAndSet(key, value);
    }
}
