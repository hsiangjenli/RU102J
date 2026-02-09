package com.redislabs.university.RU102J;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class RediSolarConfiguration extends Configuration {

  private String defaultName = "Bob";
  private RedisConfig redisConfig;

  @JsonProperty
  public String getDefaultName() {
    return defaultName;
  }

  @JsonProperty
  public void setDefaultName(String defaultName) {
    this.defaultName = defaultName;
  }

  @JsonProperty("redis")
  public void setRedisConfig(RedisConfig config) {
    this.redisConfig = config;
  }

  @JsonProperty("redis")
  public RedisConfig getRedisConfig() {
    return redisConfig;
  }
}
