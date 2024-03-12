package com.example.demo.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("com.example.demo")
public class JwtProps {
    private String secretKey;
}
