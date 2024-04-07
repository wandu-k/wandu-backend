package com.example.wandukong.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.MiniHome.MiniHomePost;
import com.example.wandukong.dto.MiniHome.MiniHomePostDto;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.repository.MiniHomePostRepository;

import jakarta.transaction.Transactional;

@Service
public class MiniHomePostServiceImpl implements MiniHomePostService {

    @Autowired
    MiniHomePostRepository miniHomePostRepository;

    @Autowired
    ApiResponse apiResponse;

    @Override
    public MiniHomePostDto getPost(Long postID) throws PostNotFoundException {

        MiniHomePost minihomePost = miniHomePostRepository.findById(postID)
                .orElseThrow(() -> new PostNotFoundException());

        MiniHomePostDto miniHomePostDto = MiniHomePostDto.builder()
                .postID(minihomePost.getPostID())
                .boardID(minihomePost.getBoardID())
                .userID(minihomePost.getUserID())
                .hpID(minihomePost.getHpID())
                .title(minihomePost.getTitle())
                .content(minihomePost.getContent())
                .writeDay(minihomePost.getWriteDay()).build();

        return miniHomePostDto;
    }

    @Override
    public void deletePost(Long userID, Long postID) throws PostNotFoundException, PermissionDeniedException {
        MiniHomePost minihomePost = miniHomePostRepository.findById(postID)
                .orElseThrow(() -> new PostNotFoundException());

        if (minihomePost.getUserID() != userID) {
            throw new PermissionDeniedException();
        }
        miniHomePostRepository.deleteById(postID);
    }

    @Transactional
    public ApiResponse putPost(MiniHomePostDto miniHomePostDto) {

        Optional<MiniHomePost> optionalMiniHomePost = miniHomePostRepository.findByPostIDAndUserID(
                miniHomePostDto.getPostID(),
                miniHomePostDto.getUserID());

        if (optionalMiniHomePost.isPresent()) {
            optionalMiniHomePost.get().updatePost(miniHomePostDto.getBoardID(), miniHomePostDto.getTitle(),
                    miniHomePostDto.getContent());

            return ApiResponse.builder()
                    .message("게시글 수정이 완료되었습니다.")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            MiniHomePost newPost = MiniHomePost.builder()
                    .userID(miniHomePostDto.getUserID())
                    .hpID(miniHomePostDto.getHpID())
                    .boardID(miniHomePostDto.getBoardID())
                    .title(miniHomePostDto.getTitle())
                    .content(miniHomePostDto.getContent())
                    .build();

            miniHomePostRepository.save(newPost);

            return ApiResponse.builder()
                    .message("게시글 등록이 완료되었습니다.")
                    .status(HttpStatus.CREATED)
                    .build();
        }
    }
}