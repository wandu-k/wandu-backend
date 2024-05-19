package com.example.wandukong.controller;

import com.example.wandukong.dto.ForumBoardDto;
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.service.ForumBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/forum/board")
@RequiredArgsConstructor
public class ForumBoardController {

    private final ForumBoardService forumBoardService;

    @PutMapping
    public ResponseEntity<?> modifyBoard(@RequestBody ForumBoardDto forumBoardDto) {

        ApiResponse apiResponse = forumBoardService.modifyBoard(forumBoardDto);

        return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
    }

    @DeleteMapping
    public ResponseEntity<?> removeBoard(@RequestParam Long boardId) {

        forumBoardService.removeBoard(boardId);

        return new ResponseEntity<>("삭제가 완료되었습니다.", HttpStatus.OK);
    }
}
