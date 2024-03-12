package com.example.wandukong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.repository.AccountRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public int register(UserDto userDto) {
        String encodedPw = passwordEncoder.encode(userDto.getPassword());
        // Check for duplicate username before saving
        if (accountRepository.findByEmail(userDto.getEmail()) == null) {
            userDto.setPassword(encodedPw);
            UserDo user = userDto.toEntity();
            accountRepository.save(user);
            return 0;
        }
        return 1;
    }

    @Override
    public void login(UserDto userDto, HttpServletRequest request) {
        log.info(userDto.getEmail());
        log.info(userDto.getPassword());

        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDto.getEmail(),
                userDto.getPassword());

        // 토큰에 요청정보 등록
        token.setDetails(new WebAuthenticationDetails(request));

        // 토큰을 이용하여 인증 요청 로그인
        Authentication authentication = authenticationManager.authenticate(token);
        log.info("인증 정보 : " + authentication.isAuthenticated());

        User authUser = (User) authentication.getPrincipal();
        log.info("인증된 사용자 : " + authUser.getUsername());

    }
}
