package com.example.wandukong.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.wandukong.security.jwt.JwtAuthenticationFilter;
import com.example.wandukong.security.jwt.JwtRequestFilter;
import com.example.wandukong.security.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 원하는 인코더 타입 사용
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationConfiguration configuration)
            throws Exception {

        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("*"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(false);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L); // 1시간
                        return config;
                    }
                }))

                .addFilterAfter(new JwtAuthenticationFilter(configuration.getAuthenticationManager(), jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtRequestFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers("/api/**", "/v3/**", "/swagger-ui/**", "/error").permitAll();
                    requests.requestMatchers(HttpMethod.POST, "/api/articles").authenticated();
                })

                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(customUserDetailService)
                .build();
    }

}