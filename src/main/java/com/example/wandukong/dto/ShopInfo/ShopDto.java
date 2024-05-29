
package com.example.wandukong.dto.ShopInfo;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.Category;
import com.example.wandukong.domain.ShopInfo.Shop;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShopDto {
    private Long userId;
    private Long itemId;
    private String itemName;
    private Long categoryId;
    private int price;

    @Builder
    public ShopDto(Long userId, Long itemId, String itemName, Long categoryId, int price) {
        this.userId = userId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.categoryId = categoryId;
        this.price = price;
    }

    /* 상점 */
    public Shop toEntity() {
        return Shop.builder()
                .itemId(itemId)
                .itemName(itemName)
                .price(price)
                .userDo(UserDo.builder().userId(userId).build())
                .category(Category.builder().categoryId(categoryId).build())
                .build();
    }
}