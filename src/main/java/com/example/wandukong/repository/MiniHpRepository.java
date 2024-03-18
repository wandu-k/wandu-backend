package com.example.wandukong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.MiniHome;

@Repository
public interface MiniHpRepository extends JpaRepository<MiniHome, Long> {

}