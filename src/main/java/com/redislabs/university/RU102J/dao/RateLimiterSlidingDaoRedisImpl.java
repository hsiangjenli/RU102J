package com.redislabs.university.RU102J.dao;


import com.redislabs.university.RU102J.core.KeyHelper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class RateLimiterSlidingDaoRedisImpl implements RateLimiter {

  private final JedisPool jedisPool;
  private final long windowSizeMS;
  private final long maxHits;

  public RateLimiterSlidingDaoRedisImpl(JedisPool pool, long windowSizeMS, long maxHits) {
    this.jedisPool = pool;
    this.windowSizeMS = windowSizeMS;
    this.maxHits = maxHits;
  }

  // Challenge #7
  @Override
  public void hit(String name) throws RateLimitExceededException {
    // START CHALLENGE #7
    // key 的格式 [limiter]:[windowSize]:[name]:[maxHits]
    String keyName = String.format("%s:%d:%s:%d", "limiter", windowSizeMS, name, maxHits);
    String key = KeyHelper.getKey(keyName);

    try (Jedis jedis = jedisPool.getResource()) {
      Transaction t = jedis.multi();

      Long currentTimestamp = System.currentTimeMillis();
      Long startTimestamp = currentTimestamp - windowSizeMS;

      String member = currentTimestamp + "-" + Thread.currentThread().getName();

      t.zadd(key, currentTimestamp, member);
      t.zremrangeByScore(key, 0, startTimestamp);
      Response<Long> count = t.zcard(key);

      t.exec();

      if (count.get() > maxHits) {
        throw new RateLimitExceededException();
      }
    }

    // END CHALLENGE #7
  }
}
