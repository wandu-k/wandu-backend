package com.example.wandukong.service.diary;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.diary.Diary;
import com.example.wandukong.dto.SearchDiaryDto;
import com.example.wandukong.dto.MiniHome.DiaryDto;
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.repository.diary.DiaryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    @Override
    public ApiResponse putPost(DiaryDto miniHomeDiaryDto) {

        Diary miniHomeDiary = Diary.builder()
                .postId(miniHomeDiaryDto.getPostId())
                .userDo(UserDo.builder().userId(miniHomeDiaryDto.getUserId()).build())
                .title(miniHomeDiaryDto.getTitle())
                .content(miniHomeDiaryDto.getContent())
                .build();

        diaryRepository.save(miniHomeDiary);

        if (miniHomeDiaryDto.getPostId() != null) {
            return ApiResponse.builder()
                    .message("게시글 수정이 완료되었습니다.")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            return ApiResponse.builder()
                    .message("게시글 등록이 완료되었습니다.")
                    .status(HttpStatus.CREATED)
                    .build();
        }
    }

    @Override
    public List<DiaryDto> getList(SearchDiaryDto searchDiaryDto) {
        return diaryRepository.getList(searchDiaryDto);
    }

    @Override
    public DiaryDto getPost(Long postId) {
        Diary diary = diaryRepository.findById(postId).orElse(null);

        DiaryDto diarydto = DiaryDto.builder()
                .postId(diary.getPostId())
                .userId(diary.getUserDo().getUserId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .writeDay(diary.getWriteDay())
                .build();

        return diarydto;
    }

}
