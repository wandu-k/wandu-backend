package com.example.wandukong.domain.forum;

import com.example.wandukong.domain.UserDo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "ForumPostReview")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumPostReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId", unique = true)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private ForumPost forumPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserDo userDo;

    @Column(name = "commentContent")
    private String commentContent;

    @Column(name = "reviewWriteDate")
    @CreationTimestamp
    private LocalDate writeDate;

    @Builder
    public ForumPostReview(Long commentId, ForumPost forumPost, UserDo userDo, String commentContent, LocalDate writeDate) {
        this.commentId = commentId;
        this.forumPost = forumPost;
        this.userDo = userDo;
        this.commentContent = commentContent;
        this.writeDate = writeDate;
    }

    public void changeContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
