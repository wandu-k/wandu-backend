package com.example.wandukong.domain.MiniHome;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "MiniHomeBoard")
public class MiniHomeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardID", unique = true)
    private Long boardID;

    @Column(name = "boardName")
    private String boardName;

    @OneToMany(mappedBy = "miniHomeBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MiniHomePost> miniHomePost = new ArrayList<>();

    @Builder
    public MiniHomeBoard(Long boardID, String boardName, List<MiniHomePost> miniHomePost) {
        this.boardID = boardID;
        this.boardName = boardName;
        this.miniHomePost = miniHomePost;
    }

}
