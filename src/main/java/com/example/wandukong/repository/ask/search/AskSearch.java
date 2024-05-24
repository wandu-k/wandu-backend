package com.example.wandukong.repository.ask.search;

import com.example.wandukong.domain.Ask;
import com.example.wandukong.dto.PageRequestDto;
import org.springframework.data.domain.Page;

public interface AskSearch {
    Page<Ask> search(PageRequestDto pageRequestDto);
}
