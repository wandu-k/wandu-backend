package com.example.wandukong.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ForumBoard")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId", unique = true)
    private Long boardId;

    @Column(name = "boardName")
    private String boardName;

    @OneToMany(mappedBy = "forumBoard", cascade = CascadeType.ALL)
    private List<ForumPost> forumPosts = new ArrayList<>();

    @Builder
    public ForumBoard(Long boardId, String boardName, List<ForumPost> forumPosts) {
        this.boardId = boardId;
        this.boardName = boardName;
        this.forumPosts = forumPosts;
    }

    public void changeBoardName(String boardName) {
        this.boardName = boardName;
    }

}
