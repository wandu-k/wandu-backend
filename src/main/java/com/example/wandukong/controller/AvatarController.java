package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.ResponseAvatarDto;
import com.example.wandukong.service.AvatarService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class AvatarController {

    private final AvatarService avatarService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{userId}/avatar")
    public ResponseEntity<?> getAvatar(@PathVariable("userId") Long userId) {

        ResponseAvatarDto avatarDto = avatarService.getAvatar(userId);
        return new ResponseEntity<>(avatarDto, HttpStatus.OK);
    }
}
