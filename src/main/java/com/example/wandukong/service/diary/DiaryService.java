package com.example.wandukong.service.diary;

import java.util.List;

import com.example.wandukong.dto.SearchDiaryDto;
import com.example.wandukong.dto.MiniHome.DiaryDto;
import com.example.wandukong.model.ApiResponseDto;

public interface DiaryService {

    ApiResponseDto putPost(DiaryDto miniHomeDiaryDto);

    List<DiaryDto> getList(SearchDiaryDto searchDiaryDto);

    DiaryDto getPost(Long postId);

}
