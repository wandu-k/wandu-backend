package com.example.wandukong.repository;

import com.example.wandukong.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByMiniHome_HpId(Long hpId);
}
