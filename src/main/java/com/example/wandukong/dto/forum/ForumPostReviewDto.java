package com.example.wandukong.dto.forum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForumPostReviewDto {

    private Long commentId;

    private Long postId;

    private Long userId;

    private String commentContent;

    private LocalDate writeDate;
}
