package com.redislabs.university.RU102J;

public class HostPort {
  private static final String defaultHost = "localhost";
  private static final Integer defaultPort = 6379;
  private static final String defaultPassword = "";

  public static String getRedisHost() {
    return defaultHost;
  }

  public static Integer getRedisPort() {
    return defaultPort;
  }

  public static String getRedisPassword() {
    return defaultPassword;
  }
}
