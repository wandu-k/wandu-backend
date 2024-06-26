package com.example.wandukong.controller.forum;

import com.example.wandukong.dto.forum.ForumBoardDto;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.service.forum.ForumBoardService;
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

        ApiResponseDto apiResponse = forumBoardService.modifyBoard(forumBoardDto);

        return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
    }

    @DeleteMapping
    public ResponseEntity<?> removeBoard(@RequestParam Long boardId) {

        forumBoardService.removeBoard(boardId);

        return new ResponseEntity<>("삭제가 완료되었습니다.", HttpStatus.OK);
    }
}
