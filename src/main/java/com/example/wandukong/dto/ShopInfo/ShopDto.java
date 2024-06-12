
package com.example.wandukong.dto.ShopInfo;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.domain.ShopInfo.ShopSubCategory;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShopDto {
    private Long userId;
    private Long itemId;
    private String itemName;
    private Long subcategoryId;
    private int price;

    @Builder
    public ShopDto(Long userId, Long itemId, String itemName, Long subcategoryId, int price) {
        this.userId = userId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.subcategoryId = subcategoryId;
        this.price = price;
    }

    

    public ShopDto() {
    }



    /* 상점 */
    public Shop toEntity() {
        return Shop.builder()
                .itemId(itemId)
                .itemName(itemName)
                .price(price)
                .userDo(UserDo.builder().userId(userId).build())
                .shopSubcategory(ShopSubCategory.builder().subcategoryId(subcategoryId).build())
                .build();
    }
}