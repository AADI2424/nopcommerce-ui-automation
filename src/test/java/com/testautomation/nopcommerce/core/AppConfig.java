package com.testautomation.nopcommerce.core;

public final class AppConfig {
    private AppConfig() {}

    private static String normalize(String url) {
        if (url == null || url.isBlank()) return "https://demo.nopcommerce.com/";
        return url.endsWith("/") ? url : url + "/";
    }

    // Allow -DbaseUrl=... etc., but ALWAYS end with a slash
    public static final String BASE_URL =
        normalize(System.getProperty("baseUrl", "https://demo.nopcommerce.com/"));

    public static final int EXPLICIT_WAIT_SEC =
        Integer.parseInt(System.getProperty("explicitWait", "10"));

    public static final int IMPLICIT_WAIT_SEC =
        Integer.parseInt(System.getProperty("implicitWait", "0"));

    public static final int PAGELOAD_TIMEOUT_SEC =
        Integer.parseInt(System.getProperty("pageLoadTimeout", "45"));
}
