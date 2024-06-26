package com.example.wandukong.domain.forum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.wandukong.domain.UserDo;
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

    @Column(name = "postWriteDate")
    @CreationTimestamp
    private LocalDate writeDate;

    @Column(name = "state")
    private int state = 1;

    @Column(name = "count", columnDefinition = "int default 0")
    private int count;

    @OneToMany(mappedBy = "forumPost", cascade = CascadeType.ALL)
    private List<ForumPostReview> forumPostReviews = new ArrayList<>();

    @OneToMany(mappedBy = "forumPost", cascade = CascadeType.ALL)
    private List<ForumPostFile> forumPostFiles = new ArrayList<>();

    @Builder
    public ForumPost(Long postId, ForumBoard forumBoard, UserDo userDo, String title, String content, LocalDate writeDate, int state, int count,
                     List<ForumPostReview> forumPostReviews, List<ForumPostFile> forumPostFiles) {
        this.postId = postId;
        this.forumBoard = forumBoard;
        this.userDo = userDo;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
        this.state = state;
        this.count = count;
        this.forumPostReviews = forumPostReviews;
        this.forumPostFiles = forumPostFiles;
    }

    public void changePost(ForumBoard forumBoard, String title, String content, int state) {
        this.forumBoard = forumBoard;
        this.title = title;
        this.content = content;
        this.state = state;
    }

    public void changeCount() {
        this.count++;
    }

}
