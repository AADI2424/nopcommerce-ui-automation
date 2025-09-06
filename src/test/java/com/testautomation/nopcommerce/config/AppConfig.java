package com.testautomation.nopcommerce.config;

public final class AppConfig {
  private AppConfig() {}

  // Change default if needed; can be overridden in Jenkins with -DbaseUrl=...
  public static final String BASE_URL =
      System.getProperty("baseUrl", "https://demo.nopcommerce.com");

  public static final int EXPLICIT_WAIT_SEC =
      Integer.parseInt(System.getProperty("explicitWait", "30"));
}
