package com.testautomation.nopcommerce.core;

import java.util.Random;

public final class DataFactory {
    private static final Random R = new Random();

    private DataFactory(){}

    public static String randomEmail() {
        return "auto_" + System.currentTimeMillis() + "@example.com";
    }

    public static String randomName() {
        return "AutoUser" + R.nextInt(10_000);
    }

    public static int randomNumber(int min, int max) {
        return R.nextInt((max - min) + 1) + min;
    }
}
