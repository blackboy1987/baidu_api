package com.bootx.service.impl;

import com.bootx.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author black
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value) {
        try {
            stringRedisTemplate.opsForValue().set(key,value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void set(String key, String value, long duration, TimeUnit timeUnit) {
        try {
            stringRedisTemplate.opsForValue().set(key,value,duration,timeUnit);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String get(String key) {
        try {
           return stringRedisTemplate.opsForValue().get(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String key) {
        try {
            stringRedisTemplate.delete(key);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Boolean hasKey(String key) {
        try {
            return stringRedisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Long increment(String key) {
        try {
            return stringRedisTemplate.opsForValue().increment(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public Set<String> getKeys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }
}
