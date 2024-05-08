package com.example.wandukong.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.wandukong.domain.ForumPost;
import com.example.wandukong.dto.ForumPostDto;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;
import com.example.wandukong.repository.ForumPostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ForumPostServiceImpl implements ForumPostService {

  private final ForumPostRepository forumPostRepository;

  @Override
  public ForumPostDto get(Long postId) {

    Optional<ForumPost> result = forumPostRepository.findById(postId);

    ForumPost forumPost = result.orElseThrow();

    return entityToDto(forumPost);
  }

  @Override
  public Long register(ForumPostDto forumPostDto) {
    ForumPost forumPost = dtoToEntity(forumPostDto);

    ForumPost result = forumPostRepository.save(forumPost);

    return result.getPostId();
  }

  @Override
  public void modify(ForumPostDto forumPostDto) {
    Optional<ForumPost> result = forumPostRepository.findById(forumPostDto.getPostId());

    ForumPost forumPost = result.orElseThrow();

    forumPost.changeTitle(forumPostDto.getTitle());
    forumPost.changeContent(forumPostDto.getContent());
    forumPost.changeState(forumPostDto.getState());

    forumPostRepository.save(forumPost);
  }

  @Override
  public void remove(Long postId) {

    forumPostRepository.deleteById(postId);
  }

  @Override
  public PageResponseDto<ForumPostDto> getList(PageRequestDto pageRequestDto) {

    // JPA
    Page<ForumPost> result = forumPostRepository.search(pageRequestDto);

    List<ForumPostDto> dtoList = result.get()
        .map(ForumPost -> entityToDto(ForumPost)).collect(Collectors.toList());

    PageResponseDto<ForumPostDto> responseDto = PageResponseDto.<ForumPostDto>withAll()
        .dtoList(dtoList)
        .pageRequestDto(pageRequestDto)
        .total(result.getTotalElements())
        .build();

    return responseDto;
  }

}
