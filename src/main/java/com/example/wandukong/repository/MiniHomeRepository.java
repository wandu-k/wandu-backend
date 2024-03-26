package com.example.wandukong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.dto.MiniHomeDto;

@Repository
public interface MiniHomeRepository extends JpaRepository<MiniHome, Long> {

    MiniHome findByUserDo_UserID(Long userID);

}