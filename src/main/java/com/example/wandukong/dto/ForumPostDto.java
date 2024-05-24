package com.example.wandukong.dto;

import java.time.LocalDate;

import lombok.*;

@Getter
@NoArgsConstructor
public class ForumPostDto {

  private Long postId;

  private Long boardId;

  private Long userId;

  private String title;

  private String content;

  private LocalDate writeDate;

  private int state;

  private int count;

  @Builder
  public ForumPostDto(Long postId, Long boardId, Long userId, String title, String content, LocalDate writeDate, int state, int count) {
    this.postId = postId;
    this.boardId = boardId;
    this.userId = userId;
    this.title = title;
    this.content = content;
    this.writeDate = writeDate;
    this.state = state;
    this.count = count;
  }
}
