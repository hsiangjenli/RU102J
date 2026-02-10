package com.redislabs.university.RU102J.dao;

import com.redislabs.university.RU102J.api.Site;
import java.util.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SiteDaoRedisImpl implements SiteDao {
  private final JedisPool jedisPool;

  public SiteDaoRedisImpl(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }

  // When we insert a site, we set all of its values into a single hash.
  // We then store the site's id in a set for easy access.
  @Override
  public void insert(Site site) {
    try (Jedis jedis = jedisPool.getResource()) { // 從連接池中取得一個 Redis 連線
      String hashKey = RedisSchema.getSiteHashKey(site.getId()); // 生成 Redis 的 Hash key 名稱
      String siteIdKey = RedisSchema.getSiteIDsKey(); // 存放所有站點 key 的 Set key 名稱（固定）
      jedis.hmset(hashKey, site.toMap()); // 將完整資訊存入 hash 結構
      jedis.sadd(siteIdKey, hashKey); // 把新產生的 key 加入 set
    }
  }

  @Override
  public Site findById(long id) {
    try (Jedis jedis = jedisPool.getResource()) {
      String key = RedisSchema.getSiteHashKey(id);
      Map<String, String> fields = jedis.hgetAll(key);
      if (fields == null || fields.isEmpty()) {
        return null;
      } else {
        return new Site(fields);
      }
    }
  }

  // Challenge #1
  @Override
  public Set<Site> findAll() {
    // START Challenge #1
    Set<Site> sites = new HashSet<Site>();
    try (Jedis jedis = jedisPool.getResource()) {
      String siteIdKey = RedisSchema.getSiteIDsKey();
      Set<String> siteHashKeys = jedis.smembers(siteIdKey);
      for (String hashKey : siteHashKeys) {
        Map<String, String> siteData = jedis.hgetAll(hashKey);
        // 確認 siteData 不是空值，避免錯誤
        if (!siteData.isEmpty()) {
          Site site = new Site();
          sites.add(site);
        }
      }
      return sites;
    } catch (Exception e) {
      e.printStackTrace();
      return Collections.emptySet();
    }
    // END Challenge #1
  }
}
