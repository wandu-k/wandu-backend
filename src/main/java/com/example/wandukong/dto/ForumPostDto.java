package com.example.wandukong.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForumPostDto {

  private Long postId;

  private String title;

  private String content;

  private LocalDate writeDate;

  private int state;
}
