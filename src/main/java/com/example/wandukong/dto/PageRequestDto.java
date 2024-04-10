package com.example.wandukong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto {

  private Long boardID;

  @Builder.Default
  private int page = 1;

  @Builder.Default
  private int size = 10;
}
