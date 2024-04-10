package com.example.wandukong.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.MiniHome.MiniHomeBoard;
import com.example.wandukong.domain.MiniHome.MiniHomePost;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.MiniHome.MiniHomePostDto;
import com.example.wandukong.exception.CustomException.BoardNotFoundException;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.repository.miniHome.MiniHomeBoardRepository;
import com.example.wandukong.repository.miniHome.MiniHomePostRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MiniHomePostServiceImpl implements MiniHomePostService {

        @Autowired
        MiniHomePostRepository miniHomePostRepository;

        @Autowired
        MiniHomeBoardRepository miniHomeBoardRepository;

        @Autowired
        ApiResponse apiResponse;

        @Override
        public MiniHomePostDto getPost(Long postID) throws PostNotFoundException {

                MiniHomePost minihomePost = miniHomePostRepository.findById(postID)
                                .orElseThrow(() -> new PostNotFoundException());

                MiniHomePostDto miniHomePostDto = MiniHomePostDto.builder()
                                .postID(minihomePost.getPostID())
                                .boardID(minihomePost.getMiniHomeBoard().getBoardID())
                                .userID(minihomePost.getUserDo().getUserID())
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

                if (minihomePost.getUserDo().getUserID() != userID) {
                        throw new PermissionDeniedException();
                }
                miniHomePostRepository.deleteById(postID);
        }

        @Transactional
        public ApiResponse putPost(MiniHomePostDto miniHomePostDto) throws BoardNotFoundException {

                System.out.println(miniHomePostDto.getBoardID());
                MiniHomeBoard miniHomeBoard = miniHomeBoardRepository.findById(miniHomePostDto.getBoardID())
                                .orElseThrow(() -> new BoardNotFoundException());

                Optional<MiniHomePost> optionalMiniHomePost = miniHomePostRepository.findById(
                                miniHomePostDto.getPostID());

                if (optionalMiniHomePost.isPresent()) {
                        log.info("게시글이 이미 있습니다. 게시글 수정을 시작합니다.");
                        optionalMiniHomePost.get()
                                        .updatePost(miniHomeBoard, miniHomePostDto.getTitle(),
                                                        miniHomePostDto.getContent());

                        return ApiResponse.builder()
                                        .message("게시글 수정이 완료되었습니다.")
                                        .status(HttpStatus.OK)
                                        .build();
                } else {
                        log.info("게시글이 없습니다. 게시글 등록을 시작합니다.");
                        MiniHomePost newPost = MiniHomePost.builder()
                                        .userDo(UserDo.builder().userID(miniHomePostDto.getUserID()).build())
                                        .hpID(miniHomePostDto.getHpID())
                                        .miniHomeBoard(miniHomeBoard)
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

        @Override
        public List<MiniHomePostDto> getPostList(PageRequestDto pageRequestDto) {

                Page<MiniHomePost> result = miniHomePostRepository.search(pageRequestDto);
                throw new UnsupportedOperationException("Unimplemented method 'getPostList'");
        }
}