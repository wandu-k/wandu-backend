package com.example.wandukong.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.wandukong.domain.Avatar;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.dto.AvatarDto;
import com.example.wandukong.repository.AvatarRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;

    @Override
    public void putAvatar(Long userId, AvatarDto avatarDto) {

        Optional<Avatar> avatar = avatarRepository.findById(userId);

        if (avatar.isPresent()) {
            avatar.get().AvatarUpdate(toLong(avatarDto.getHead()), toLong(avatarDto.getEye()),
                    toLong(avatarDto.getMouse()), toLong(avatarDto.getCloth()));
        } else {
            Avatar newavatar = Avatar.builder()
                    .userId(userId)
                    .build();
            avatarRepository.save(newavatar);
        }

    }

    @Override
    public AvatarDto getAvatar(Long userId) {

        Avatar avatar = avatarRepository.findById(userId).orElse(null);

        AvatarDto avatarDto = avatar.toDto();

        return avatarDto;

    }

    private Long toLong(String string) {
        if (string == null) {
            return null;
        }
        return Long.valueOf(string);
    }

}
