package com.example.wandukong.controller;

import com.example.wandukong.service.MiniHomeLikeService;
import com.example.wandukong.service.MiniHomeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/minihome")
public class MiniHomeLikeController {

    private final MiniHomeLikeService miniHomeLikeService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{hpId}/like/{userId}")
    public ResponseEntity<?> addLike(@PathVariable("hpId") Long hpId, @PathVariable("userId") Long userId) {

        miniHomeLikeService.addLike(hpId, userId);

        return new ResponseEntity<>("좋아요 하였습니다.", HttpStatus.OK);

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{hpId}/like/{userId}")
    public ResponseEntity<?> deleteLike(@PathVariable("hpId") Long hpId, @PathVariable("userId") Long userId) {

        miniHomeLikeService.deleteLike(hpId, userId);

        return new ResponseEntity<>("좋아요 취소 하였습니다.", HttpStatus.OK);

    }
}
