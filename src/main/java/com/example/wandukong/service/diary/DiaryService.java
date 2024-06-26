package com.example.wandukong.service.diary;

import java.util.List;

import com.example.wandukong.dto.SearchDiaryDto;
import com.example.wandukong.dto.MiniHome.DiaryDto;
import com.example.wandukong.exception.CustomException.PostNotFoundException;

public interface DiaryService {

    void putPost(Long postId, DiaryDto diaryDto) throws PostNotFoundException;

    List<DiaryDto> getList(SearchDiaryDto searchDiaryDto);

    DiaryDto getPost(Long userId, Long postId);

    void addPost(DiaryDto diaryDto);

    void deletePost(Long postId);

}
