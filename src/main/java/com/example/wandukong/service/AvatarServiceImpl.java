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
    private final S3Util s3Util;

    // 추후 JQPL 이나 QueryDsl 로 개선 필요
    @Override
    public ResponseAvatarDto getAvatar(Long userId) {

        Avatar avatar = avatarRepository.findById(userId).orElse(null);

        ResponseAvatarDto responseAvatarDto = ResponseAvatarDto.builder()
                .userId(userId)
                .head(avatar.getHead() != null ? s3Util.getUrl(avatar.getHead().getShop().getItemFile().getFileName())
                        : null)
                .eye(avatar.getEye() != null ? s3Util.getUrl(avatar.getEye().getShop().getItemFile().getFileName())
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
    public void putAvatar(Long userId, RequestAvatarDto requestAvatarDto) {

        Avatar avatar = avatarRepository.findById(userId).orElseGet(() -> {
            Avatar newAvatar = Avatar.builder()
                    .userId(userId)
                    .build();
            return avatarRepository.save(newAvatar);
        });

        Long subcategoryId = requestAvatarDto.getSubcategoryId();
        Long itemId = requestAvatarDto.getItemId();

        if (subcategoryId == 1) {
            if (itemId != null) {
                avatar.setHead(BuyItem.builder().itemId(itemId).build());
            } else {
                avatar.setHead(null);
            }
        } else if (subcategoryId == 2) {
            if (itemId != null) {
                avatar.setEye(BuyItem.builder().itemId(itemId).build());
            } else {
                avatar.setEye(null);
            }
        } else if (subcategoryId == 3) {
            if (itemId != null) {
                avatar.setMouse(BuyItem.builder().itemId(itemId).build());
            } else {
                avatar.setMouse(null);
            }
        } else if (subcategoryId == 4) {
            if (itemId != null) {
                avatar.setCloth(BuyItem.builder().itemId(itemId).build());
            } else {
                avatar.setCloth(null);
            }
        } else {
            throw new IllegalArgumentException("Invalid subcategory ID: " + subcategoryId);
        }
        avatarRepository.save(avatar);
    }
}
