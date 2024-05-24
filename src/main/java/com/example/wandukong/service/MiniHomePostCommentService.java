package com.example.wandukong.service;

import com.example.wandukong.dto.MiniHome.MiniHomeDto;

public interface MiniHomePostCommentService {

    MiniHomeDto getComment(Long postId, Long userId);

}
