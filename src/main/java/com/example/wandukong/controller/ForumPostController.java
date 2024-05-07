package com.example.wandukong.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("/")
  public Map<String, Long> register(@RequestBody ForumPostDto forumPostDto) {

    Long postID = forumPostService.register(forumPostDto);

    return Map.of("postID", postID);
  }

  @PutMapping("/{postID}")
  public Map<String, String> modify(@PathVariable Long postId, @RequestBody ForumPostDto forumPostDto) {

    forumPostDto.setPostId(postId);

    forumPostService.modify(forumPostDto);

    return Map.of("RESULT", "SUCCESS");
  }

  @DeleteMapping("/{postID}")
  public Map<String, String> remove(@PathVariable Long postID) {
    forumPostService.remove(postID);

    return Map.of("RESULT", "SUCCESS");
  }
}
