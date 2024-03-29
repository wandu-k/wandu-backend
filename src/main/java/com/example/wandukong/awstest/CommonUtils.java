package com.example.wandukong.awstest;

public class CommonUtils {

    public static String buildFileName(String category) {
        String now = String.valueOf(System.currentTimeMillis());

        return category + now;
    }

}
