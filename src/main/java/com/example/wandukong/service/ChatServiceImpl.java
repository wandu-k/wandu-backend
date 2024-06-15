package com.example.wandukong.service;

import com.example.wandukong.domain.Chat;
import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.dto.ChatMessageDto;
import com.example.wandukong.repository.ChatRepository;
import com.example.wandukong.repository.miniHome.MiniHomeRepository;
import com.example.wandukong.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MiniHomeRepository miniHomeRepository;

    @Override
    public void createChat(Long hpId, ChatMessageDto chatMessageDto) {

        UserDo userDo = userRepository.getReferenceById(chatMessageDto.getUserId());
        MiniHome miniHome = miniHomeRepository.getReferenceById(hpId);

        Chat chat = Chat.builder()
                .miniHome(miniHome)
                .userDo(userDo)
                .message(chatMessageDto.getMessage())
                .build();

        chatRepository.save(chat);
    }
}
