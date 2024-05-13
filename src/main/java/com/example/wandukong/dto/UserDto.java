package com.example.wandukong.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    private Long userId;
    private Long hpId;
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

    @Builder
    public UserDto(Long userId, Long hpId, String username, String password, String profileImage, String nickname,
            String name,
            String phone,
            String gender, Date signupDay, Date birthday, String role) {
        this.userId = userId;
        this.hpId = hpId;
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
    }

    public UserDto() {
        // TODO Auto-generated constructor stub
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
