package com.example.wandukong.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ForumBoard")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForumBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardID;

    private String boardName;

    @OneToMany(mappedBy = "forumBoard")
    private List<ForumPost> boards;

    public void changeBoardName(String boardName) {
        this.boardName = boardName;
    }
    
}
