package com.example.wandukong.dto.ScrollDto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.Getter;

@Getter
public class SliceRequestDto {

  private Long lastId;
  private int page = 1;
  private int limit = 30;

  public Pageable of() {
    return PageRequest.of(page, limit);
  }

  public int getOffset() {
    return (page - 1) * limit;
  }
}
