package com.example.wandukong.service;

import com.example.wandukong.dto.MiniHome.MiniHomePostDto;
import com.example.wandukong.exception.CustomException.PostNotFoundException;

public interface MiniHomePostService {

    MiniHomePostDto getPost(Long postID) throws PostNotFoundException;

}
