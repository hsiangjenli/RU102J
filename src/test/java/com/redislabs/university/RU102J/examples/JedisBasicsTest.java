package com.redislabs.university.RU102J.examples;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.redislabs.university.RU102J.HostPort;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.*;
import redis.clients.jedis.Jedis;

public class JedisBasicsTest {

  public static String[] testPlanets = {
    "Mercury", "Mercury", "Venus", "Earth", "Earth", "Mars", "Jupiter", "Saturn", "Uranus",
    "Neptune", "Pluto"
  };

  private Jedis jedis;

  @Before
  public void setUp() {
    this.jedis = new Jedis(HostPort.getRedisHost(), HostPort.getRedisPort());

    if (HostPort.getRedisPassword().length() > 0) {
      jedis.auth(HostPort.getRedisPassword());
    }

    jedis.del("planets_r");
    jedis.del("planets_l");
    jedis.del("earth");
  }

  @After
  public void tearDown() {
    jedis.del("planets_r");
    jedis.del("planets_l");
    jedis.del("earth");
    jedis.close();
  }

  @Test
  public void testRedisList() {

    // rpush 從表尾插入
    jedis.rpush("planets_r", testPlanets);
    List<String> rPlanets = jedis.lrange("planets_r", 0, -1);
    System.out.println("rpush 結果: " + rPlanets);

    // lpush 從表頭插入
    jedis.lpush("planets_l", testPlanets);
    List<String> lPlanets = jedis.lrange("planets_l", 0, -1);
    System.out.println("lpush 結果: " + lPlanets);

    // list 的長度
    Long length = jedis.llen("planets_r");
    assertThat(length, is(11L));

    // lrem (list remove) 從列表中移除指定值的元素
    jedis.lrem("planets_r", 1, "Mercury"); // 從左邊開始刪除
    jedis.lrem("planets_r", 1, "Earth"); // 從左邊開始刪除
    List<String> lremPlanetsR = jedis.lrange("planets_r", 0, -1);
    System.out.println(lremPlanetsR);

    // lpop 刪除第一個值, rpop 刪除最後一個值
    String rpopResult = jedis.rpop("planets_r");
    System.out.println(rpopResult);
    List<String> rpopPlanetsR = jedis.lrange("planets_r", 0, -1);
    System.out.println(rpopPlanetsR);
  }

  @Test
  public void testRedisSet() {

    jedis.sadd("planets", testPlanets);

    // scard(Set Cardinality) 一個 set 裡面有多少個成員
    Long length = jedis.scard("planets");
    System.out.println(length);

    Set<String> planets = jedis.smembers("planets");
    System.out.println(planets);

    // srem(set remove)
    Long sremResult = jedis.srem("planets", "Pluto");
    System.out.println(sremResult);
  }

  @Test
  public void testRedisHash() {

    // 一般的 set 一個 key 對一個 value
    // hashset 把多個欄位 收進同一個 key 裡面，像是一個物件或一張表
    Map<String, String> earthProperties = new HashMap<>();
    earthProperties.put("diameterKM", "12756");
    earthProperties.put("dayLengthHrs", "24");
    earthProperties.put("meanTempC", "15");
    earthProperties.put("moonCount", "1");
    System.out.println(earthProperties);

    for (Map.Entry<String, String> property : earthProperties.entrySet()) {
      System.out.println(property.getKey() + " " + property.getValue());
      jedis.hset(
          "earth",
          property.getKey(),
          property.getValue()); // hmset 可以不用一個一個 set，但是最新版已經可以直接用 hset 輸入整個 hashmap
    }

    Map<String, String> storedProperties = jedis.hgetAll("earth");
    System.out.println(storedProperties);
  }
}
