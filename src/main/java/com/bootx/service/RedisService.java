package com.bootx.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author black
 */
public interface RedisService {

    void set(String key,String value);

    void set(String key, String value, long duration, TimeUnit timeUnit);

    String get(String key);

    void delete(String key);

    Boolean hasKey(String key);

    Long increment(String key);


    Set<String> getKeys(String pattern);
}
