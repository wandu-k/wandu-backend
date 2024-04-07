package com.example.wandukong.model;

import lombok.Getter;

@Getter
public class ApiResponse {

    private Object object;
    private int code;
    private String message;

    public ApiResponse(Object object, int code, String message) {
        this.object = object;
        this.code = code;
        this.message = message;
    }
}
