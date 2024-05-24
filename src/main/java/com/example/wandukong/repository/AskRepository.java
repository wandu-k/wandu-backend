package com.example.wandukong.repository;

import com.example.wandukong.domain.Ask;
import com.example.wandukong.repository.ask.search.AskSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AskRepository extends JpaRepository<Ask, Long>, AskSearch {
}
