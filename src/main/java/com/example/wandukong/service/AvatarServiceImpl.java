package com.example.wandukong.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.Avatar;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.dto.RequestAvatarDto;
import com.example.wandukong.dto.ResponseAvatarDto;
import com.example.wandukong.repository.AvatarRepository;
import com.example.wandukong.repository.ShopInfo.BuyItemRepository;
import com.example.wandukong.util.S3Util;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final BuyItemRepository buyItemRepository;
    private final S3Util s3Util;

    @Override
    public ResponseAvatarDto getAvatar(Long userId) {

        Avatar avatar = avatarRepository.findById(userId).orElse(null);

        ResponseAvatarDto.ResponseAvatarDtoBuilder responseAvatarDtoBuilder = ResponseAvatarDto
                .builder()
                .userId(userId);

        if (avatar != null) {
            responseAvatarDtoBuilder.head(avatar.getHead() != null
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
                            : null);
        }

        ResponseAvatarDto responseAvatarDto = responseAvatarDtoBuilder.build();

        return responseAvatarDto;
    }

    @Override
    public void putAvatar(Long userId, Long itemId, RequestAvatarDto requestAvatarDto) {

        log.info("유저아이디 : " + userId);
        log.info("아이템아이디 : " + itemId);
        log.info("활성화 여부 : " + requestAvatarDto.getEnable());

        // Retrieve or create an Avatar entity for the given userId
        Avatar avatar = avatarRepository.findById(userId).orElseGet(() -> {
            Avatar newAvatar = Avatar.builder()
                    .userId(userId)
                    .build();
            return avatarRepository.save(newAvatar);
        });

        // Extract subcategory ID from the request DTO
        Long subcategoryId = requestAvatarDto.getSubcategoryId();

        // Fetch BuyItem entity based on itemId and userId
        BuyItem buyItem = buyItemRepository.getReferenceByBuyItemIdAndUserDo_UserId(itemId, userId);

        // Handle the case where requestAvatarDto.getEnable() == 0
        if (requestAvatarDto.getEnable() == 1) {
            buyItem = null; // Set buyItem to null
        }

        // Log the buyItem information
        log.info(" 구매한 아이템 " + (buyItem != null ? buyItem.getBuyItemId() : "none"));

        // Assign the appropriate BuyItem to the Avatar based on subcategoryId
        if (subcategoryId == 1) {
            avatar.setHead(buyItem);
        } else if (subcategoryId == 2) {
            avatar.setEye(buyItem);
        } else if (subcategoryId == 3) {
            avatar.setMouse(buyItem);
        } else if (subcategoryId == 4) {
            avatar.setCloth(buyItem);
        } else {
            throw new IllegalArgumentException("Invalid subcategory ID: " + subcategoryId);
        }

        // Save the updated Avatar entity
        avatarRepository.save(avatar);
    }
}
