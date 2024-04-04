package com.example.wandukong.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtToken {

    private String accessToken;

}
