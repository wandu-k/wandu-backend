package com.example.wandukong.service;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.Avatar;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.ShopSubCategory;
import com.example.wandukong.dto.RequestAvatarDto;
import com.example.wandukong.dto.ResponseAvatarDto;
import com.example.wandukong.repository.AvatarRepository;
import com.example.wandukong.util.S3Util;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private S3Util s3Util;

    @Override
    public ResponseAvatarDto getAvatar(Long userId) {

        Avatar avatar = avatarRepository.findById(userId).orElse(null);

        ResponseAvatarDto responseAvatarDto = ResponseAvatarDto.builder()
                .userId(userId)
                .head(s3Util.getUrl(avatar.getHead().getShop().getItemFile().getFileName()))
                .eye(s3Util.getUrl(avatar.getEye().getShop().getItemFile().getFileName()))
                .mouse(s3Util.getUrl(avatar.getMouse().getShop().getItemFile().getFileName()))
                .cloth(s3Util.getUrl(avatar.getCloth().getShop().getItemFile().getFileName()))
                .build();

        return responseAvatarDto;

    }

    @Override
    public void putAvatar(Long userId, RequestAvatarDto requestAvatarDto) {

        Avatar avatar = avatarRepository.findById(userId).orElseGet(() -> {
            Avatar newAvatar = Avatar.builder()
                    .userId(userId)
                    .build();
            return avatarRepository.save(newAvatar);
        });

        BuyItem item = BuyItem.builder().itemId(requestAvatarDto.getItemId()).build();

        Long subcategoryId = requestAvatarDto.getSubcategoryId();

        if (subcategoryId == 1) {
            avatar.setHead(item);
        } else if (subcategoryId == 2) {
            avatar.setEye(item);
        } else if (subcategoryId == 3) {
            avatar.setMouse(item);
        } else if (subcategoryId == 4) {
            avatar.setCloth(item);
        } else {
            throw new IllegalArgumentException("Invalid subcategory ID: " + subcategoryId);
        }

        avatarRepository.save(avatar);
    }
}
