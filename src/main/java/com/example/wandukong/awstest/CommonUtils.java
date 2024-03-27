package com.example.wandukong.awstest;

public class CommonUtils {
    private static final String FILE_EXTENSION_SEPARATOR = ".";

    public static String buildFileName(String category) {
        String now = String.valueOf(System.currentTimeMillis());

        return category + now;
    }

}
