package com.example.wandukong.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostListRequestDto {

    private Long boardId;
    private int page;
    private int size;

}
