package com.example.wandukong.dto.MiniHome;

import java.time.LocalDate;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.diary.Diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class DiaryDto {

    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private LocalDate writeDay;

    public Diary toEntity() {

        Diary diary = Diary.builder()
                .userDo(UserDo.builder().userId(userId).build())
                .title(title)
                .content(content)
                .build();
        return diary;
    }

    public DiaryDto() {
    }

    
}
