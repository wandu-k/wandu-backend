package com.example.wandukong.repository.diary;

import java.util.List;

import com.example.wandukong.dto.SearchDiaryDto;
import com.example.wandukong.dto.MiniHome.DiaryDto;

public interface DiaryRepositoryCustom {

    List<DiaryDto> getList(SearchDiaryDto searchDiaryDto);

}