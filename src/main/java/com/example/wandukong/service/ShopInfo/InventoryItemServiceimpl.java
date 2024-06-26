package com.example.wandukong.service.ShopInfo;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.InventoryItemDto;
import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.exception.CustomException.EntityAlreadyExistsException;
import com.example.wandukong.repository.ShopInfo.BuyItemPageRepository;
import com.example.wandukong.repository.ShopInfo.BuyItemRepository;
import com.example.wandukong.repository.ShopInfo.ShopInfoRepository;
import com.example.wandukong.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryItemServiceimpl implements InventoryItemService {

        private final BuyItemRepository buyItemRepository;
        private final BuyItemPageRepository buyItemPageRepository;
        private final ShopInfoRepository shopInfoRepository;
        private final UserRepository userRepository;

        @Override
        public PageResponseDto<InventoryItemDto> getMybuylist(Long userId, SearchItemDto searchItemDto) {

                // jpa를 사용하여 페이지 별로 사용자가 구매 내역 정보를 가져옴
                Page<InventoryItemDto> buyItemPage = buyItemPageRepository.findByUserDoUserId(userId, searchItemDto);

                // 가져온 구매 내역 정보를 BuyItemDto로 변환
                List<InventoryItemDto> buyitemList = buyItemPage.getContent();

                // 반환된 목록을 포함하는 PageResponseDto를 생성
                PageResponseDto<InventoryItemDto> responseDto = PageResponseDto.<InventoryItemDto>withAll()
                                .dtoList(buyitemList)
                                .pageRequestDto(new PageRequestDto(searchItemDto.getCategoryId(),
                                                searchItemDto.getPage(),
                                                searchItemDto.getSize()))
                                .total(buyItemPage.getTotalElements())
                                .build();
                return responseDto;
        }

        @Override
        public void addItem(BuyItemDto buyItemDto) throws EntityAlreadyExistsException {

                Shop shop = shopInfoRepository.getReferenceById(buyItemDto.getItemId());

                UserDo userDo = userRepository.getReferenceById(buyItemDto.getUserId());

                BuyItem buyItem = new BuyItem(null, null, shop, userDo);

                buyItemRepository.save(buyItem);
        }

}
