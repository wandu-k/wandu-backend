package com.example.wandukong.repository.ask;

import com.example.wandukong.domain.ask.Ask;
import com.example.wandukong.repository.ask.search.AskSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AskRepository extends JpaRepository<Ask, Long>, AskSearch {
}
