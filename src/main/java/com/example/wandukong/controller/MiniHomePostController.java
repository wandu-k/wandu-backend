package com.example.wandukong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.PostListRequestDto;
import com.example.wandukong.dto.MiniHome.MiniHomePostDto;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.service.MiniHomePostService;

import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/minihome/post")
@RestController
public class MiniHomePostController {

    @Autowired
    MiniHomePostService miniHomePostService;

    @GetMapping
    public ResponseEntity<?> getPost(@RequestParam Long postID) throws PostNotFoundException {

        MiniHomePostDto minihomePostDto = miniHomePostService.getPost(postID);

        return new ResponseEntity<>(minihomePostDto, HttpStatus.OK);
    }

}
