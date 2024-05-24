package com.example.wandukong.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.wandukong.dto.AccountDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//client -> filter -> server

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl("/api/public/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        ObjectMapper mapper = new ObjectMapper();
        AccountDto accountDto = new AccountDto();

        try {
            accountDto = mapper.readValue(request.getInputStream(), AccountDto.class);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        log.info("Email (username) : " + accountDto.getUsername());
        log.info("Password : " + accountDto.getPassword());

        // 사용자 인증정보 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(accountDto.getUsername(),
                accountDto.getPassword());

        log.info("사용자 인증정보 객체 생성 완료");

        // 사용자 로그인
        authentication = authenticationManager.authenticate(authentication);
        log.info("인증 여부 : " + authentication.isAuthenticated());

        // 인증 성공 여부
        if (!authentication.isAuthenticated()) {
            log.info("회원을 찾을 수 없습니다");
            response.setStatus(401);
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) throws IOException, ServletException {
        log.info("인증 성공...");

        String jwt = jwtTokenProvider.createToken(authentication);

        response.addHeader(JwtConstants.TOKEN_HEADER, JwtConstants.TOKEN_PREFIX + jwt);
        response.setStatus(200);
    }
}
