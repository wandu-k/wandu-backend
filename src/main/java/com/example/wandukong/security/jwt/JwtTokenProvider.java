package com.example.wandukong.security.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

//JWT 토큰 관련 기능을 제공해주는 클래스

@Slf4j
@Component
public class JwtTokenProvider {

    @Autowired
    JwtProps jwtProps;

    @Autowired
    CustomUserDetails customUserDetails;

    public String createToken(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = Jwts.builder().header()
                .keyId(JwtConstants.TOKEN_TYPE).and()
                .expiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("userId", customUserDetails.getUserDto().getUserId())
                .claim("email", customUserDetails.getUserDto().getUsername())
                .claim("rol", customUserDetails.getUserDto().getRole())
                .signWith(getShaKey(), Jwts.SIG.HS512)
                .compact();

        return accessToken;

    }

    public UsernamePasswordAuthenticationToken getAuthentication(String authHeader) {
        if (authHeader == null || authHeader.length() == 0) {
            log.info("토큰이 없습니다.");
            return null;
        }
        String jwt = authHeader.replace(JwtConstants.TOKEN_PREFIX, "");
        Jws<Claims> parsedToken = Jwts.parser().verifyWith(getShaKey()).build().parseSignedClaims(jwt);

        String email = parsedToken.getPayload().get("email").toString();
        String strUserId = parsedToken.getPayload().get("userId").toString();
        Long userId = Long.valueOf(strUserId);
        String role = parsedToken.getPayload().get("rol").toString();

        log.info("토큰 데이터 추출 완료");

        if (email == null || email.length() == 0) {
            return null;
        }

        UserDto userDto = UserDto.builder()
                .username(email)
                .userId(userId)
                .build();

        // GrantedAuthority 객체로 변환
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);

        // 권한 목록 반환
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(grantedAuthority);

        UserDetails userDetails = new CustomUserDetails(userDto);
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    // 토큰 유효성 검사
    public boolean vaildateToken(String jwt) {
        try {
            Jws<Claims> parsedToken = Jwts.parser().verifyWith(getShaKey()).build().parseSignedClaims(jwt);
            log.info("토큰 만료 기간");
            log.info("" + parsedToken.getPayload().getExpiration());
            Date exp = parsedToken.getPayload().getExpiration();

            return !exp.before(new Date());
        } catch (ExpiredJwtException e) {
            log.error("Token Expired"); // 토큰 만료
            return false;

        } catch (JwtException e) {
            log.error("Token Tampered"); // 토큰 손상
            return false;

        } catch (NullPointerException e) {
            log.error("Token is Null"); // 토큰 없음
            return false;

        }

    }

    private byte[] getSigningKey() {
        return jwtProps.getSecretKey().getBytes();
    }

    private SecretKey getShaKey() {
        return (Keys.hmacShaKeyFor(getSigningKey()));
    }

}
