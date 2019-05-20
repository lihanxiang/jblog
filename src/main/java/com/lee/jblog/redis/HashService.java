package com.lee.jblog.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class HashService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public HashService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void put(String key, Object value, Class<?> entityClass){
        HashOperations<String, Object, Object> vo = redisTemplate.opsForHash();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field :
                fields) {
            field.setAccessible(true);
            try {
                vo.put(key, field.getName(), field.get(value));
            } catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }

    public Object get(String key, Object field){
        HashOperations<String, Object, Object> vo = redisTemplate.opsForHash();
        return vo.get(key, field);
    }

    public Object hashGetAll(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    public void delete(String key, Class<?> entityClass){
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field :
                fields) {
            redisTemplate.opsForHash().delete(key, field.getName());
        }
    }

    public Long hashIncrement(String key, Object field, long increment){
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    public boolean hasKey(String key){
        return redisTemplate.opsForHash().getOperations().hasKey(key);
    }
}
