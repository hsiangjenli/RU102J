package com.redislabs.university.RU102J;

import com.redislabs.university.RU102J.core.KeyHelper;
import java.util.Set;
import redis.clients.jedis.Jedis;

// Provides a consistent key prefix for tests and
// a method for cleaning up these keys.
public class TestKeyManager {

  private String prefix;

  public TestKeyManager(String prefix) {
    KeyHelper.setPrefix(prefix);
    this.prefix = prefix;
  }

  public void deleteKeys(Jedis jedis) {
    Set<String> keysToDelete = jedis.keys(getKeyPattern());
    for (String key : keysToDelete) {
      jedis.del(key);
    }
  }

  private String getKeyPattern() {
    return prefix + ":*";
  }
}
