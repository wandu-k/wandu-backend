package com.example.wandukong.service.diary;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.wandukong.domain.diary.Diary;
import com.example.wandukong.dto.SearchDiaryDto;
import com.example.wandukong.dto.MiniHome.DiaryDto;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.repository.diary.DiaryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    @Transactional
    @Override
    public void putPost(Long postId, DiaryDto diaryDto) throws PostNotFoundException {

        Diary diary = diaryRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());

        diary.updatePost(diary.getTitle(), diary.getContent());
    }

    @Override
    public List<DiaryDto> getList(SearchDiaryDto searchDiaryDto) {
        return diaryRepository.getList(searchDiaryDto);
    }

    @Override
    public DiaryDto getPost(Long userId, Long postId) {

        log.info(postId.toString());
        log.info(userId.toString());

        Diary diary = diaryRepository.findByUserDo_UserIdAndPostId(userId, postId).orElse(null);

        return DiaryDto.builder()
                .postId(diary.getPostId())
                .userId(diary.getUserDo().getUserId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .writeDay(diary.getWriteDay())
                .build();
    }

    @Override
    public void addPost(DiaryDto diaryDto) {

        Diary diary = diaryDto.toEntity();

        diaryRepository.save(diary);
    }

    @Override
    public void deletePost(Long postId) {
        diaryRepository.deleteById(postId);
    }

}
