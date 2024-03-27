package com.example.wandukong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.MiniHome.MiniHome;

import com.example.wandukong.dto.UserDto;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.repository.AccountRepository;
import com.example.wandukong.repository.MiniHomeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MiniHomeRepository miniHpRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    // 회원가입
    @Transactional
    @Override
    public int register(UserDto userDto) {
        String encodedPw = passwordEncoder.encode(userDto.getPassword());
        // Check for duplicate username before saving
        if (accountRepository.findByEmail(userDto.getEmail()) == null) {
            userDto.setPassword(encodedPw);
            UserDo user = userDto.toEntity();
            user = accountRepository.save(user);

            log.info("회원가입된 유저 아이디" + user.getUserID());

            // 회원가입이 완료되면 그 유저아이디로 미니홈도 생성
            MiniHomeDto miniHomeDto = new MiniHomeDto();
            miniHomeDto.setUserID(user.getUserID());
            MiniHome miniHome = miniHomeDto.toEntity();

            log.info("홈피 유저 아이디 : " + miniHome.getUserDo().getUserID());

            miniHpRepository.save(miniHome);
            return 0;
        }
        return 1;
    }

    @Override
    public void deleteAccount(Long userID) {
        accountRepository.deleteById(userID);
    }

    // 필요없는 부분

    // @Override
    // public void login(UserDto userDto, HttpServletRequest request) {
    // log.info(userDto.getEmail());
    // log.info(userDto.getPassword());

    // // 인증 토큰 생성
    // UsernamePasswordAuthenticationToken token = new
    // UsernamePasswordAuthenticationToken(userDto.getEmail(),
    // userDto.getPassword());

    // // 토큰에 요청정보 등록
    // token.setDetails(new WebAuthenticationDetails(request));

    // // 토큰을 이용하여 인증 요청 로그인
    // Authentication authentication = authenticationManager.authenticate(token);
    // log.info("인증 정보 : " + authentication.isAuthenticated());

    // User authUser = (User) authentication.getPrincipal();
    // log.info("인증된 사용자 : " + authUser.getUsername());

    // }
}
