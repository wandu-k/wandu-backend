package com.example.wandukong.dto.MiniHome;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class MiniHomePostCommentDto {

    private Long commentId;
    private Long postId;
    private Long userId;
    private String comment;
    private LocalDate writeDate;

    @Builder
    public MiniHomePostCommentDto(Long commentId, Long postId, Long userId, String comment, LocalDate writeDate) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.comment = comment;
        this.writeDate = writeDate;
    }
}
