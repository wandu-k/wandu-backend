package com.example.wandukong.domain.MiniHome;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "MiniHomePost")
public class MiniHomePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postID", unique = true)
    private Long postID;

    @Column(name = "boardID")
    private Long boardID;

    @Column(name = "userID")
    private Long userID;

    @Column(name = "hpID")
    private Long hpID;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @CreationTimestamp
    @Column(name = "writeDay")
    private Date writeDay;

    @Builder
    public MiniHomePost(Long postID, Long boardID, Long userID, Long hpID, String title, String content,
            Date writeDay) {
        this.postID = postID;
        this.boardID = boardID;
        this.userID = userID;
        this.hpID = hpID;
        this.title = title;
        this.content = content;
        this.writeDay = writeDay;
    }

    // 필드 업데이트 메서드
    public void updatePost(Long boardID, String title, String content) {
        this.boardID = boardID;
        this.title = title;
        this.content = content;
    }

}
