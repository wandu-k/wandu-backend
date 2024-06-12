package com.example.wandukong.service;

import org.springframework.stereotype.Service;

import com.example.wandukong.domain.Avatar;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.dto.RequestAvatarDto;
import com.example.wandukong.dto.ResponseAvatarDto;
import com.example.wandukong.repository.AvatarRepository;
import com.example.wandukong.repository.ShopInfo.BuyItemRepository;
import com.example.wandukong.util.S3Util;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final BuyItemRepository buyItemRepository;
    private final S3Util s3Util;

    @Override
    public ResponseAvatarDto getAvatar(Long userId) {

        Avatar avatar = avatarRepository.findById(userId).orElse(null);

        ResponseAvatarDto responseAvatarDto = ResponseAvatarDto.builder()
                .userId(userId)
                .head(avatar.getHead() != null
                        ? s3Util.getUrl(avatar.getHead().getShop().getItemFile().getFileName())
                        : null)
                .eye(avatar.getEye() != null
                        ? s3Util.getUrl(avatar.getEye().getShop().getItemFile().getFileName())
                        : null)
                .mouse(avatar.getMouse() != null
                        ? s3Util.getUrl(avatar.getMouse().getShop().getItemFile().getFileName())
                        : null)
                .cloth(avatar.getCloth() != null
                        ? s3Util.getUrl(avatar.getCloth().getShop().getItemFile().getFileName())
                        : null)
                .build();

        return responseAvatarDto;

    }

    @Override
    public void putAvatar(Long userId, Long itemId, RequestAvatarDto requestAvatarDto) {

        Avatar avatar = avatarRepository.findById(userId).orElseGet(() -> {
            Avatar newAvatar = Avatar.builder()
                    .userId(userId)
                    .build();
            return avatarRepository.save(newAvatar);
        });

        Long subcategoryId = requestAvatarDto.getSubcategoryId();
        BuyItem buyItem = buyItemRepository.getReferenceByBuyItemIdAndUserDo_UserId(itemId, userId);

        if (subcategoryId == 1) {

            avatar.setHead(buyItem);

        } else if (subcategoryId == 2) {

            avatar.setEye(buyItem);
        } else if (subcategoryId == 3) {

            avatar.setMouse(buyItem);

        } else if (subcategoryId == 4) {

            avatar.setCloth(buyItem);

        } else {
            throw new IllegalArgumentException("Invalid subcategory ID: " +
                    subcategoryId);
        }
        avatarRepository.save(avatar);
    }
}
