package com.example.wandukong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.MiniHome.MiniHomePost;
import com.example.wandukong.dto.MiniHome.MiniHomePostDto;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.repository.MiniHomePostRepository;

@Service
public class MiniHomePostServiceImpl implements MiniHomePostService {

    @Autowired
    MiniHomePostRepository miniHomePostRepository;

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
}
