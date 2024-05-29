package com.example.wandukong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.Daily;

@Repository
public interface DailyCheckRepository extends JpaRepository<Daily, Long> {

}