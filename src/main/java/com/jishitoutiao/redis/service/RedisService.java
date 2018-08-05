package com.jishitoutiao.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component(value = "redisService")
public class RedisService {
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    private Logger logger = LoggerFactory.getLogger(RedisService.class);

    /**
     * 判断key是否存在
     * @param key key
     * @return true存在，false不存在
     */
    public boolean existKey(String key) {
        logger.info("existKey(String key) method run, key: " + key);
        if (key == null || key == "") {
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除key
     * @param key key
     * @return true删除成功，false删除不成功
     */
    public boolean delKey(String key) {
        logger.info("delKey(String key) method run, key: " + key);
        if (key == null || key == "") {
            return false;
        }
        return redisTemplate.delete(key);
    }

    /**
     * 返回所有的key
     * @param pattern 正则字符串
     * @return 保存所有key的set集合
     */
    public Set<String> allKeys(String pattern) {
        logger.info("allKeys(String pattern) method run, pattern: " + pattern);
        if (pattern == null) {
            pattern = "*";		// 默认查找全部
        }
        return redisTemplate.keys(pattern);
    }

    /**
     * 设置string
     * @param key key
     * @param value 值
     * @return true设置成功，false设置失败
     */
    public boolean setStr(String key, String value) {
        logger.info("setStr(String key, String value) method run, key: " + key + ", value: " + value);
        if (key == null || key == "" || value == null) {
            return false;
        }
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    /**
     * 通过key获取string
     * @param key key
     * @return key对应的值
     */
    public String getStr(String key) {
        logger.info("getStr(String key) method run, key: " + key);
        if (key == null) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 对string的值做+操作
     * @param key key
     * @param count 加的值
     * @return 增加后的值,-1代表操作失败
     */
    public long incrByStr(String key, long count) {
        logger.info("incrByStr(String key, long count) method run, key: " + key + ", count: " + count);
        if (key == null || key == "") {
            return -1;
        }

        return redisTemplate.opsForValue().increment(key, count);
    }

    /**
     * 设置hash
     * @param key key
     * @param field 字段
     * @param value 取值
     * @return true设置成功，false设置失败
     */
    public boolean setHash(String key, String field, String value) {
        logger.info("setHash(String key, String field, String value) method run, key: " + key + ", field: " + field + ", value: " + value);
        if (key == null || key == "" || field == null || field == "" || value == null) {
            return false;
        }
        redisTemplate.opsForHash().put(key, field, value);
        return true;
    }

    /**
     * 批量设置hash
     * @param key key
     * @param map 保存field和value的map
     * @return true代表操作成功,false代表操作失败
     */
    public boolean setHashByMap(String key, Map<String,Object> map) {
        if (key == null || key == "") {
            return false;
        }
        for (Map.Entry<String, Object> entry: map.entrySet()) {
            logger.info("setHashByMap(String key, Map<String,Object> map) method run, key: " + key + ", entry.getKey(): " + entry.getKey() + ", entry.getValue(): " + entry.getValue());
        }
        redisTemplate.opsForHash().putAll(key, map);
        return true;
    }

    /**
     * 获取hash的所有field
     * @param key key
     * @return field集合,返回null代表key不存在或为空
     */
    public Set<Object> getAllHashField(String key) {
        logger.info("getAllHashField(String key) method run, key: " + key);
        if (key == null || key == "") {
            return null;
        }
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取指定的hash field的值
     * @param key key
     * @param field 字段
     * @return 取值,返回null代表key/field不存在或为空
     */
    public String getHashValue(String key, String field) {
        logger.info("getHashValue(String key, String field)  method run, key: " + key + ", field: " + field);
        if (key == null || key == "" || field == null || field == "") {
            return null;
        }
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 返回hash指定key的所有filed和value
     * @return map集合，key是hash的field，值是value,返回null代表key不存在或为空
     */
    public Map<Object, Object> getAllHash(String key) {
        logger.info("etAllHash(String key) method run, key: " + key);
        if (key == null || key == "") {
            return null;
        }
        return redisTemplate.opsForHash().entries(key);
    }
}