package com.example.wandukong.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.dto.AccountDto;
import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.repository.user.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDo userDo = accountRepository.findByEmail(username);

        if (userDo == null) {
            log.info("사용자가 없음");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        log.info("사용자가 있음");
        log.info(userDo.toString());

        AccountDto accountDto = AccountDto.builder()
                .userId(userDo.getUserId())
                .username(userDo.getEmail())
                .password(userDo.getPassword())
                .role(userDo.getRole())
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(accountDto);

        log.info(customUserDetails.toString());

        return customUserDetails;
    }

}
