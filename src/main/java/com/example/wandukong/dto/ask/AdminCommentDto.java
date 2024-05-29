package com.example.wandukong.dto.ask;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCommentDto {

    private Long commentId;

    private Long userId;

    private Long askId;

    private String commentContent;

    private LocalDate writeDate;
}
