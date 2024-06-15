package com.example.wandukong.service;

import com.example.wandukong.domain.Chat;
import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.dto.ChatMessageDto;
import com.example.wandukong.exception.CustomException;
import com.example.wandukong.repository.ChatRepository;
import com.example.wandukong.repository.miniHome.MiniHomeRepository;
import com.example.wandukong.repository.user.UserRepository;
import com.example.wandukong.util.S3Util;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MiniHomeRepository miniHomeRepository;
    private final S3Util s3Util;

    @Override
    public ChatMessageDto createChat(Long hpId, ChatMessageDto chatMessageDto) {

        UserDo userDo = userRepository.findById(chatMessageDto.getUserId()).orElseThrow(() ->
                new EntityNotFoundException("User not found"));
        
        MiniHome miniHome = miniHomeRepository.getReferenceById(hpId);

        Chat chat = Chat.builder()
                .miniHome(miniHome)
                .userDo(userDo)
                .message(chatMessageDto.getMessage())
                .build();

        chat = chatRepository.save(chat);

        ChatMessageDto dto = ChatMessageDto.builder()
                .userId(userDo.getUserId())
                .nickname(userDo.getNickname())
                .profileImage(s3Util.getUrl(userDo.getProfileImage()))
                .message(chat.getMessage())
                .build();

        return dto;
    }
}
