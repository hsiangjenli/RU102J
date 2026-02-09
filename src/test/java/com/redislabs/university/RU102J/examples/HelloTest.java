package com.redislabs.university.RU102J.examples;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.redislabs.university.RU102J.HostPort;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class HelloTest {

  @Test
  public void sayHelloBasic() {
    Jedis jedis = new Jedis(HostPort.getRedisHost(), HostPort.getRedisPort());
    jedis.set("hello", "world");
    String value = jedis.get("hello");
    System.out.println(value);
    assertThat(value, is("world"));
  }

  @Test
  public void sayHello() {}

  @Test
  public void sayHelloThreadSafe() {}
}
