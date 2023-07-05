package com.rasyid.projectprobation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    /**
     * Set the String key-value pair
     */
    public void put(String key, Object value, long millis) {
        redisTemplate.opsForValue().set(key, value, millis, TimeUnit.MINUTES);
    }
    public void putForHash(String objectKey, String hkey, String value) {
        redisTemplate.opsForHash().put(objectKey, hkey, value);
    }
    public <T> T get(String key, Class<T> type) {
        return (T) redisTemplate.boundValueOps(key).get();
    }
    public void remove(String key) {
        redisTemplate.delete(key);
    }
    public boolean expire(String key, long millis) {
        return redisTemplate.expire(key, millis, TimeUnit.MILLISECONDS);
    }
    public boolean persist(String key) {
        return redisTemplate.hasKey(key);
    }
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
    public Integer getInteger(String key) {
        return (Integer) redisTemplate.opsForValue().get(key);
    }
    public Long getLong(String key) {
        return (Long) redisTemplate.opsForValue().get(key);
    }
    public Date getDate(String key) {
        return (Date) redisTemplate.opsForValue().get(key);
    }

    /**
     * Subtract one from the key value of the specified key
     */
    public Long decrBy(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }
}
