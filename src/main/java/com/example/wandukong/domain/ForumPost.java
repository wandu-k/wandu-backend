package com.example.wandukong.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ForumPost")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    private ForumBoard forumBoard;

    @ManyToOne
    private UserDo userDo;

    private String title;

    private String content;

    private LocalDate writeDate;

    private int state;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeWriteDate(LocalDate writeDate) {
        this.writeDate = writeDate;
    }

    public void changeState(int state) {
        this.state = state;
    }

}
