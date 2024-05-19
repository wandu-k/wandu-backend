package com.example.wandukong.service;

import com.example.wandukong.domain.ForumBoard;
import com.example.wandukong.dto.ForumBoardDto;
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.repository.ForumBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumBoardServiceImpl implements ForumBoardService{

    private final ForumBoardRepository forumBoardRepository;

    @Override
    public ApiResponse modifyBoard(ForumBoardDto forumBoardDto) {
        Optional<ForumBoard> forumBoard = forumBoardRepository.findById(forumBoardDto.getBoardId());
        if (forumBoard.isPresent()) {

            return ApiResponse.builder()
                    .message("카테고리 수정이 완료되었습니다.")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            ForumBoard newForumBoard = forumBoardDto.toEntity();
            forumBoardRepository.save(newForumBoard);

            return ApiResponse.builder()
                    .message("카테고리 등록이 완료되었습니다.")
                    .status(HttpStatus.CREATED)
                    .build();
        }
    }

    @Override
    public void deleteBoard(Long boardId) {
        forumBoardRepository.deleteById(boardId);
    }
}
