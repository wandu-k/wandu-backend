package com.example.wandukong.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountDto {

    private Long userId;
    private String username;
    private String password;
    private String profileImage;
    private String nickname;
    private String name;
    private String phone;
    private String gender;
    private Date signupDay;
    private Date birthday;
    private String role;
    private int point;

    private String intro;

    @Builder
    public AccountDto(Long userId, String username, String password, String profileImage, String nickname, String name,
            String phone, String gender, Date signupDay, Date birthday, String role, int point, String intro) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.signupDay = signupDay;
        this.birthday = birthday;
        this.role = role;
        this.point = point;
        this.intro = intro;
    }

    public AccountDto() {
    }

}
