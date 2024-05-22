package com.example.wandukong.dto.ScrollDto;

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
public class SliceRequestDto {

  private Long lastId;

  @Builder.Default
  private int page = 1;

  @Builder.Default
  private int limit = 30;

  public Pageable of() {

    return PageRequest.of(page, limit);
  }

  public int getOffset() {
    return (page - 1) * limit;
  }

}
