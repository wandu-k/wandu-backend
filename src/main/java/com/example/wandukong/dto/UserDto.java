package com.example.wandukong.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long userId;
    private String profileImage;
    private String nickname;
    private String gender;
    private Date signupDay;
    private Date birthday;
    private String role;
    private String intro;
    private Long followCount;
    private Long followerCount;
    private Integer followCheck;
    private Integer followerCheck;

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }



}
