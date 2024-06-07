package com.example.wandukong.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.wandukong.domain.Avatar;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.dto.AvatarDto;
import com.example.wandukong.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;

    @Override
    public void patchAvatar(Long userId, AvatarDto avatarDto) {

        Optional<Avatar> avatar = avatarRepository.findById(userId);

        if (avatar.isPresent()) {
            if (avatarDto.getHead() != null) {
                avatar.get().setHaed(BuyItem.builder().itemBuyId(toLong(avatarDto.getHead())).build());
            }
            if (avatarDto.getEye() != null) {
                avatar.get().setEye(BuyItem.builder().itemBuyId(toLong(avatarDto.getEye())).build());
            }
            if (avatarDto.getMouse() != null) {
                avatar.get().setHaed(BuyItem.builder().itemBuyId(toLong(avatarDto.getMouse())).build());
            }
            if (avatarDto.getCloth() != null) {
                avatar.get().setHaed(BuyItem.builder().itemBuyId(toLong(avatarDto.getCloth())).build());
            }
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
