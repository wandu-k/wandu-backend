package com.example.wandukong.domain;

import java.util.Date;
import java.util.List;

import com.example.wandukong.domain.ask.AdminComment;
import com.example.wandukong.domain.ask.Ask;
import com.example.wandukong.domain.forum.ForumPost;
import com.example.wandukong.domain.forum.ForumPostReview;
import com.example.wandukong.domain.guest.GuestRoom;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.example.wandukong.domain.MiniHome.MiniHomePost;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.Playlist;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.AccountDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    @Column(name = "userId", unique = true)
    private Long userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profileImage")
    private String profileImage;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "intro")
    private String intro;

    @Column(name = "phone")
    private String phone;

    @Column(name = "point")
    private int point;

    @CreationTimestamp
    @Column(name = "signupDay")
    private Date signupDay;

    @ColumnDefault("'ROLE_USER'")
    @Column(name = "role")
    private String role = "ROLE_USER";

    @OneToMany(mappedBy = "userDo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MiniHomePost> minihomePost;

    @OneToMany(mappedBy = "userDo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Playlist> playlist;

    @OneToMany(mappedBy = "userDo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Shop> shop;

    @OneToMany(mappedBy = "userDo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BuyItem> buyItem;

    @OneToMany(mappedBy = "userDo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ForumPost> forumPosts;

    @OneToMany(mappedBy = "userDo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ForumPostReview> forumPostReviews;

    @OneToMany(mappedBy = "userDo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Ask> asks;

    @OneToMany(mappedBy = "userDo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AdminComment> adminComments;

    @OneToMany(mappedBy = "userDo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GuestRoom> guestRooms;

    @Builder
    public UserDo(Long userId, String email, String password, String nickname, String profileImage, Date birthday,
            String intro, String phone, int point, Date signupDay, String role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.intro = intro;
        this.phone = phone;
        this.point = point;
        this.signupDay = signupDay;
        this.role = role;
    }

    public AccountDto toDto() {
        AccountDto accountDtoDto = AccountDto.builder()
                .userId(userId)
                .nickname(nickname)
                .profileImage(profileImage)
                .birthday(birthday)
                .signupDay(signupDay)
                .role(role)
                .point(point)
                .intro(intro)
                .build();
        return accountDtoDto;
    }

    public void updateProfile(String nickname, Date birthday, String intro) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.intro = intro;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateUserPoint(int updatedPoint) {
        this.point = updatedPoint;
    }
}