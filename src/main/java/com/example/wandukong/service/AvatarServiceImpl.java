package com.example.wandukong.service;

import org.springframework.stereotype.Service;

import com.example.wandukong.domain.Avatar;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.dto.AvatarDto;
import com.example.wandukong.repository.AvatarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;

    @Override
    public void putAvatar(Long userId, AvatarDto avatarDto) {

        Avatar avatar = avatarRepository.findById(userId).orElseGet(() -> {
            Avatar newavatar = Avatar.builder()
                    .userDo(UserDo.builder().userId(userId).build())
                    .haed(BuyItem.builder().itemBuyId(Long.valueOf(avatarDto.getHead())).build())
                    .eye(BuyItem.builder().itemBuyId(Long.valueOf(avatarDto.getEye())).build())
                    .mouse(BuyItem.builder().itemBuyId(Long.valueOf(avatarDto.getMouse())).build())
                    .cloth(BuyItem.builder().itemBuyId(Long.valueOf(avatarDto.getCloth())).build())
                    .build();
            return avatarRepository.save(newavatar);
        });

        avatar.AvatarUpdate(Long.valueOf(avatarDto.getHead()), Long.valueOf(avatarDto.getEye()),
                Long.valueOf(avatarDto.getMouse()), Long.valueOf(avatarDto.getCloth()));

    }

}
