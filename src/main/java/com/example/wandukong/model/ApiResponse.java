package com.example.wandukong.model;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;

@Component
@Getter
public class ApiResponse {

    private HttpStatus status;

    public ApiResponse() {
        // 기본 생성자는 일반적으로 비워둡니다.
    }

    // 생성자
    public ApiResponse(HttpStatus status) {
        this.status = status;
    }

    private String message;

    @Builder
    public ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
