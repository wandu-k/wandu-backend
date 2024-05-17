package com.example.wandukong.dto.ScrollDto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class sliceResponseDto<E> {

  private List<E> dtoList;
  private boolean hasMoreData;

  @Builder
  public sliceResponseDto(List<E> dtoList, boolean hasMoreData) {
    this.dtoList = dtoList;
    this.hasMoreData = hasMoreData;
  }
}