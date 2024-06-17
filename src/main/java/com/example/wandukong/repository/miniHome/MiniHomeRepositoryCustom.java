package com.example.wandukong.repository.miniHome;


import com.example.wandukong.dto.MiniHome.MiniHomeDto;

public interface MiniHomeRepositoryCustom {
    MiniHomeDto findByUserDo_UserId(Long userId, Long likeUserId);

}