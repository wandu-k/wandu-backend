package com.example.wandukong.domain.MiniHome;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Builder
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

    @Column(name = "writeDay")
    private Date writeDay;

}
