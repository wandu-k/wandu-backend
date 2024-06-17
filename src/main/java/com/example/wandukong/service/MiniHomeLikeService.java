package com.example.wandukong.service;

public interface MiniHomeLikeService {
    void addLike(Long hpId, Long userId);

    void deleteLike(Long hpId, Long userId);
}
