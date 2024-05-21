package com.example.wandukong.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    private Long userId;
    private String profileImage;
    private String nickname;
    private String gender;
    private Date signupDay;
    private Date birthday;
    private String role;
    private Long followCount;
    private Long followerCount;

    public UserDto() {
        // TODO Auto-generated constructor stub
    }

    @Builder
    public UserDto(Long userId, String profileImage, String nickname, String gender, Date signupDay, Date birthday,
            String role, Long followCount, Long followerCount) {
        this.userId = userId;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.gender = gender;
        this.signupDay = signupDay;
        this.birthday = birthday;
        this.role = role;
        this.followCount = followCount != null ? followCount : 0L;
        this.followerCount = followerCount != null ? followerCount : 0L;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
