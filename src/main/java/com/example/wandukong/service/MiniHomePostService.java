package com.example.wandukong.service;

import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.MiniHome.MiniHomePostDto;
import com.example.wandukong.exception.CustomException.BoardNotFoundException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponse;

public interface MiniHomePostService {

    MiniHomePostDto getPost(Long postId) throws PostNotFoundException;

    void deletePost(MiniHomePostDto miniHomePostDto) throws PostNotFoundException;

    ApiResponse putPost(MiniHomePostDto miniHomePostDto) throws BoardNotFoundException;

    PageResponseDto<MiniHomePostDto> getPostList(PageRequestDto pageRequestDto);

}
