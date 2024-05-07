package com.example.wandukong.domain.MiniHome;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.example.wandukong.domain.UserDo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "MiniHomePost")
public class MiniHomePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId", unique = true)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private MiniHomeBoard miniHomeBoard;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserDo userDo;

    @ManyToOne
    @JoinColumn(name = "hpId")
    private MiniHome miniHome;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @CreationTimestamp
    @Column(name = "writeDay")
    private LocalDate writeDay;

    @Builder
    public MiniHomePost(Long postId, MiniHomeBoard miniHomeBoard, UserDo userDo, MiniHome miniHome,
            String title,
            String content,
            LocalDate writeDay) {
        this.postId = postId;
        this.miniHomeBoard = miniHomeBoard;
        this.userDo = userDo;
        this.miniHome = miniHome;
        this.title = title;
        this.content = content;
        this.writeDay = writeDay;
    }

    // 필드 업데이트 메서드
    public void updatePost(MiniHomeBoard miniHomeBoard, String title, String content) {
        this.miniHomeBoard = miniHomeBoard;
        this.title = title;
        this.content = content;
    }

}
