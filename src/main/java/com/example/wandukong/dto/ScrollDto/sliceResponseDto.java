package com.example.wandukong.dto.ScrollDto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class SliceResponseDto<T> {

  private List<T> dtoList;
  private boolean hasMoreData;
  private SliceRequestDto sliceRequestDto;

  @Builder(builderMethodName = "withAll")
  public SliceResponseDto(List<T> dtoList, boolean hasMoreData, SliceRequestDto sliceRequestDto) {
    this.dtoList = dtoList;
    this.hasMoreData = hasMoreData;
    this.sliceRequestDto = sliceRequestDto;
  }
}