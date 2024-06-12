package com.example.wandukong.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlbumDto {

    private Long albumId;
    private Long userId;
    private String intro;
    private String file;

}
