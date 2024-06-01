package com.example.wandukong.dto.forum;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ForumPostFileDto {

    private Long postId;

    private String fileName;

    @Builder
    public ForumPostFileDto(Long postId, String fileName) {
        this.postId = postId;
        this.fileName = fileName;
    }
}
