package com.testautomation.nopcommerce.core;

/**
 * Central place for app-level constants.
 * You can tweak waits or the base URL here if needed.
 */
public final class AppConfig {
    private AppConfig() {}

    // AUT site
    public static final String BASE_URL = "https://demo.nopcommerce.com/";

    // Waits
    public static final long EXPLICIT_WAIT_SEC = 10;
    public static final long IMPLICIT_WAIT_SEC = 0;
}
