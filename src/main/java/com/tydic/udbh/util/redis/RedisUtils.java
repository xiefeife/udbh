package com.tydic.udbh.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author BaiGuoyong
 * @since 2020/11/11 17:34
 **/
@Component
public class RedisUtils {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    /**
     * 将字符串值 value 关联到 key 。
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value,long expire) {
        stringRedisTemplate.opsForValue().set(key, value);
        if(expire != NOT_EXPIRE){
            expire(key,expire,TimeUnit.SECONDS);
        }
    }

    /**
     * 返回 key 所关联的字符串值。
     *
     * @param key 键
     * @return 值 string
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 获取键过期时间
     *
     * @param key      键
     * @param timeUnit 时间单位
     * @return the long
     */
    public Long ttl(String key, TimeUnit timeUnit) {
        return stringRedisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 设置键过期时间
     *
     * @param key      键
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return boolean boolean
     */
    public void expire(String key, long timeout, TimeUnit timeUnit) {
         stringRedisTemplate.expire(key, timeout, timeUnit);
    }


    /**
     * 获取旧值并且更新值
     *
     * @param key   键
     * @param value 新值
     * @return 旧值
     */
    public String getSet(String key, String value) {
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 删除
     *
     * @param key 键
     * @return the boolean
     */
    public boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }






}
