package com.example.wandukong.domain.forum;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ForumPostFile")
public class ForumPostFile {

    @Id
    @Column(name = "postId", unique = true)
    private Long postId;

    @MapsId(value = "postId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private ForumPost forumPost;

    @Column(name = "fileName")
    private String fileName;

    @Builder
    public ForumPostFile(Long postId, String fileName) {
        this.postId = postId;
        this.fileName = fileName;
    }

    public void changeFileName(String fileName) {
        this.fileName = fileName;
    }
}
