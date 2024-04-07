package com.example.wandukong.domain;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.example.wandukong.domain.MiniHome.MiniHome;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "Users")
public class UserDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", unique = true)
    private Long userID;

    @Column(name = "hpID")
    private Long hpID;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profileImage")
    private String profileImage;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "phone")
    private String phone;

    @CreationTimestamp
    @Column(name = "signupDay")
    private Date signupDay;

    @Column(name = "gender")
    private String gender;

    @ColumnDefault("0")
    @Column(name = "role")
    private int role;

    @OneToOne(mappedBy = "userDo", cascade = CascadeType.ALL, orphanRemoval = true)
    private MiniHome miniHome;

    @Builder
    public UserDo(Long userID, String email, String password, String name, String nickname, String profileImage,
            Date birthday, String phone, String gender) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.phone = phone;
        this.gender = gender;
    }

    // 필드 업데이트 메서드
    public void updateProfile(String email, String name, String nickname, String profileImage, Date birthday,
            String phone, String gender) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.phone = phone;
        this.gender = gender;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void sethpID(Long hpID) {
        this.hpID = hpID;
    }

}