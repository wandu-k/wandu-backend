package com.example.wandukong.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.wandukong.domain.ForumPost;
import com.example.wandukong.dto.ForumPostDto;
import com.example.wandukong.repository.ForumPostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ForumPostServiceImpl implements ForumPostService{

  private final ForumPostRepository forumPostRepository;

  @Override
  public ForumPostDto get(Long postID) {
    
    Optional<ForumPost> result = forumPostRepository.findById(postID);

    ForumPost forumPost = result.orElseThrow();

    return entityToDto(forumPost);
  }

}
