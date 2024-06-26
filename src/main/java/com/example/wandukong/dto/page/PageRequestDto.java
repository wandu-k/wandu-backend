package com.example.wandukong.dto.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

  private Long boardId;

  @Builder.Default
  private int page = 1;

  @Builder.Default
  private int size = 10;

  public Pageable of() {

    return PageRequest.of(page, size);
  }

  public int getOffset() {
    return (page - 1) * size;
  }
}
