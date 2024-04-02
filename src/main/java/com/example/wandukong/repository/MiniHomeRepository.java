package com.example.wandukong.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.MiniHome.MiniHome;

@Repository
public interface MiniHomeRepository extends JpaRepository<MiniHome, Long> {

    Optional<MiniHome> findByUserDo_UserID(Long userID);

}