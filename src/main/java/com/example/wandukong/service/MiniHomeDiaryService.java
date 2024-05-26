package com.example.wandukong.service;

import org.springframework.stereotype.Service;

import com.example.wandukong.dto.MiniHome.MiniHomeDiaryDto;
import com.example.wandukong.model.ApiResponse;

public interface MiniHomeDiaryService {

    ApiResponse putPost(MiniHomeDiaryDto miniHomeDiaryDto);

}
