package com.example.wandukong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumDto {

    private Long albumId;
    private Long userId;
    private String intro;
    private String file;


}
