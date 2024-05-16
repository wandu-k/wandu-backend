package com.example.wandukong.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.MiniHome.MiniHomeBoard;
import com.example.wandukong.dto.MiniHome.MiniHomeBoardDto;
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.repository.miniHome.MiniHomeBoardRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MiniHomeBoardServiceImpl implements MiniHomeBoardService {

    @Autowired
    MiniHomeBoardRepository miniHomeBoardRepository;

    @Override
    public ApiResponse putBoard(MiniHomeBoardDto miniHomeBoardDto) {

        Optional<MiniHomeBoard> miniHomeBoard = miniHomeBoardRepository.findById(miniHomeBoardDto.getBoardId());
        if (miniHomeBoard.isPresent()) {
            log.info("이미 존재하는 게시판 입니다");

            return ApiResponse.builder()
                    .message("게시판 수정이 완료되었습니다.")
                    .status(HttpStatus.OK)
                    .build();

        } else {
            log.info("게시판이 없습니다 추가합니다.");
            MiniHomeBoard newMiniHomeBoard = miniHomeBoardDto.toEntity();
            miniHomeBoardRepository.save(newMiniHomeBoard);

            return ApiResponse.builder()
                    .message("게시판 등록이 완료되었습니다.")
                    .status(HttpStatus.CREATED)
                    .build();
        }
    }

    @Override
    public void deleteMinihomeBoard(Long boardId) {
        miniHomeBoardRepository.deleteById(boardId);
    }
}
