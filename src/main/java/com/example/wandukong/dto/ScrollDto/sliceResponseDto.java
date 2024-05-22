package com.example.wandukong.dto.ScrollDto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class SliceResponseDto<E> {

  private List<E> dtoList;
  private boolean hasMoreData;
  private SliceRequestDto sliceRequestDto;

  @Builder(builderMethodName = "withAll")
  public SliceResponseDto(List<E> dtoList, boolean hasMoreData, SliceRequestDto sliceRequestDto) {
    this.dtoList = dtoList;
    this.hasMoreData = hasMoreData;
    this.sliceRequestDto=sliceRequestDto;
  }
}