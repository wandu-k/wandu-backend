package com.example.wandukong.security.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public String createToken(Long userID, String email, int role) {

        String jwt = Jwts.builder().header()
                .keyId(JwtConstants.TOKEN_TYPE).and()
                .expiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("userid", userID)
                .claim("email", email)
                .claim("rol", role)
                .signWith(getShaKey(), Jwts.SIG.HS512)
                .compact();
        log.info("jwt : " + jwt);
        return jwt;

    }

    public UsernamePasswordAuthenticationToken getAuthentication(String authHeader) {
        if (authHeader == null || authHeader.length() == 0) {
            return null;
        }
        try {
            String jwt = authHeader.replace(JwtConstants.TOKEN_PREFIX, "");
            Jws<Claims> parsedToken = Jwts.parser().verifyWith(getShaKey()).build().parseSignedClaims(jwt);
            log.info("parsedToken : " + parsedToken);

            String email = parsedToken.getPayload().get("email").toString();
            Long userID = Long.valueOf(Integer.parseInt((parsedToken.getPayload().get("userid").toString())));
            String role = (String) parsedToken.getPayload().get("rol");

            if (email == null || email.length() == 0) {
                return null;
            }

            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setUserID(userID);

            // GrantedAuthority 객체로 변환
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);

            // 권한 목록 반환
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(grantedAuthority);

            UserDetails userDetails = new CustomUserDetails(userDto);
            return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        } catch (Exception e) {

        }
        return null;
    }

    // 토큰 유효성 검사
    public boolean vaildateToken(String jwt) {

        log.info(jwtProps.getSecretKey());
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
