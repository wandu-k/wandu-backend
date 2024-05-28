package com.example.wandukong.model;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponseDto {

    private String message;
    private HttpStatus status;

    @Builder
    public ApiResponseDto(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
