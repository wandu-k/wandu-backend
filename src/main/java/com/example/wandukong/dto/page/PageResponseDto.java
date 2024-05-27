package com.example.wandukong.dto.page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Data;

@Data
public class PageResponseDto<E> {

  private List<E> dtoList;

  private List<Integer> pageNumList;

  private PageRequestDto pageRequestDto;

  private boolean prev, next;

  private int totalCount, prevPage, nextPage, totalPage, current;

  @Builder(builderMethodName = "withAll")
  public PageResponseDto(List<E> dtoList, PageRequestDto pageRequestDto, long total) {

    this.dtoList = dtoList;
    this.pageRequestDto = pageRequestDto;
    this.totalCount = (int) total;

    // 끝 페이지
    int end = (int) (Math.ceil(pageRequestDto.getPage() / 10.0)) * 10;

    int start = end - 9;

    // 진짜 마지막
    int last = (int) (Math.ceil(totalCount / (double) pageRequestDto.getSize()));

    end = Math.min(end, last);

    this.prev = start > 1;

    this.next = totalCount > end * pageRequestDto.getSize();

    this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    this.prevPage = prev ? start - 1 : 0;

    this.nextPage = next ? end + 1 : 0;
  }

}
