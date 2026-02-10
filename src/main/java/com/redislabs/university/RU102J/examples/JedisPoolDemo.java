package com.redislabs.university.RU102J.examples;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolDemo {
  public static void main(String[] args) {
    final JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(64); // 連線總數上限，負值則表示不限制，預設值為：8
    poolConfig.setMaxIdle(64); // 最大閒置連線數，預設值為：8
    JedisPool jedisPool = new JedisPool(poolConfig, "redis.enterprise", 6379);
  }
}
