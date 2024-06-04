package com.example.wandukong.dto.MiniHome;

import java.time.LocalDate;

import com.example.wandukong.domain.Friend;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.diary.Diary;
import com.example.wandukong.dto.FriendDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryDto {

    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private LocalDate writeDay;

    @Builder
    public DiaryDto(Long postId, Long userId, String title, String content,
            LocalDate writeDay) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.writeDay = writeDay;
    }

    public Diary toEntity() {

        Diary diary = Diary.builder()
                .userDo(UserDo.builder().userId(userId).build())
                .title(title)
                .content(content)
                .build();
        return diary;
    }
}
