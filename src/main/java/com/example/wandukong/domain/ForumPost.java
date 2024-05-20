package com.example.wandukong.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ForumPost")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId", unique = true)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private ForumBoard forumBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserDo userDo;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "writeDate")
    @CreationTimestamp
    private LocalDate writeDate;

    @Column(name = "state")
    private int state;

    @Builder
    public ForumPost(Long postId, ForumBoard forumBoard, UserDo userDo, String title, String content, LocalDate writeDate, int state) {
        this.postId = postId;
        this.forumBoard = forumBoard;
        this.userDo = userDo;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
        this.state = state;
    }

    public void changePost(ForumBoard forumBoard, String title, String content, int state) {
        this.forumBoard = forumBoard;
        this.title = title;
        this.content = content;
        this.state = state;
    }

}
