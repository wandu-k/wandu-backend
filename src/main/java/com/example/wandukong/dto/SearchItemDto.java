package com.example.wandukong.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SearchItemDto {

    private Long userId;
    private Long itemId;
    private String nickname;
    private String filename;
    private Long categoryId;
    private String categoryName;
    private Long subcategoryId;
    private String subcategoryName;
    private int page = 1;
    private int size = 10;

    public SearchItemDto() {
    }

    public Pageable of() {
        return PageRequest.of(page, size);
    }

    public int getOffset() {
        return (page - 1) * size;
    }

}
