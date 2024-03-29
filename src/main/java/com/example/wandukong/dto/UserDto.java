package com.example.wandukong.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.UserDo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class UserDto {

    private Long userID;
    private String email;
    private String password;
    private String profileImage;
    private String nickname;
    private String name;
    private String phone;
    private String gender;
    private Date signupDay;
    private Date birthday;
    private int role;

    /* DTO -> Entity */
    public UserDo toEntity() {
        UserDo user = UserDo.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .name(name)
                .birthday(birthday)
                .phone(phone)
                .gender(gender)
                .build();
        return user;
    }
}
