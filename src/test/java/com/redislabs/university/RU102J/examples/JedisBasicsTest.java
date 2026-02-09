package com.redislabs.university.RU102J.examples;

import com.redislabs.university.RU102J.HostPort;
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

    jedis.del("planets");
    jedis.del("earth");
  }

  @After
  public void tearDown() {
    jedis.del("planets");
    jedis.del("earth");
    jedis.close();
  }

  @Test
  public void testRedisList() {}

  @Test
  public void testRedisSet() {}

  @Test
  public void testRedisHash() {}
}
