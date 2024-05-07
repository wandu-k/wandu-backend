package com.example.wandukong.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    private Long userId;
    private Long hpId;
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

    @Builder
    public UserDto(Long userId, Long hpId, String email, String password, String profileImage, String nickname,
            String name,
            String phone,
            String gender, Date signupDay, Date birthday, int role) {
        this.userId = userId;
        this.hpId = hpId;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.signupDay = signupDay;
        this.birthday = birthday;
        this.role = role;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
