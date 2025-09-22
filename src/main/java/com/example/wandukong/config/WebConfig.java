package com.example.wandukong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("https://localhost:3000", "https://wookportfolio.duckdns.org:81")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("Authorization", "Content-Type", "X-Requested-With")
                .exposedHeaders("Authorization"); // ★ 프론트에서 읽을 수 있도록 노출
    }
}

// 같은 서버에 프론트, 백엔드가 같이 있으면 상관없는데 주소가 다를 경우에 한 곳에서 작동되도록 허용하겠다고 선언하는것
