package com.cpp.mscs.cricscore.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 4/3/21
 * Time:  13:36
 */

public class JedisPoolHelper {

    @Value("${spring.redis.host}")
    private String redisHost;

    static JedisPoolConfig poolConfig = buildPoolConfig();
    static JedisPool jedisPool;

    private JedisPoolHelper(){
        if(redisHost == null)
            redisHost = "35.208.89.90";
        jedisPool = new JedisPool(poolConfig, redisHost);
    }

    public static Jedis getResource(){
        if(jedisPool == null){
            poolConfig = buildPoolConfig();
            new JedisPoolHelper();
        }
        return jedisPool.getResource();
    }

    static private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

}