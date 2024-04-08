package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.ForumPostDto;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;
import com.example.wandukong.service.ForumPostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/forumpost")
public class ForumPostController {

  private final ForumPostService forumPostService;

  @GetMapping("/{postID}")
  public ForumPostDto get(@PathVariable Long postID) {

    return forumPostService.get(postID);
  }

  @GetMapping("/list")
  public PageResponseDto<ForumPostDto> list(PageRequestDto pageRequestDto) {

    return forumPostService.getList(pageRequestDto);
  }

  
}
