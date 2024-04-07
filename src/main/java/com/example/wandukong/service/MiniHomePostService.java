package com.example.wandukong.service;

import com.example.wandukong.dto.MiniHome.MiniHomePostDto;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponse;

public interface MiniHomePostService {

    MiniHomePostDto getPost(Long postID) throws PostNotFoundException;

    void deletePost(Long userID, Long postID) throws PostNotFoundException, PermissionDeniedException;

    ApiResponse putPost(MiniHomePostDto miniHomePostDto);

}