
package com.example.wandukong.dto.ShopInfo;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.Category;
import com.example.wandukong.domain.ShopInfo.Shop;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class ShopDto {
    private Long userId;
    private Long itemId;
    private String itemName;
    private Long categoryId;

    @Builder
    public ShopDto(Long userId, Long itemId, String itemName, Long categoryId) {
        this.userId = userId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.categoryId = categoryId;
    }

    /* 상점 */
    public Shop toEntity() {
        return Shop.builder()
                .itemId(itemId)
                .itemName(itemName)
                .userDo(UserDo.builder().userId(userId).build())
                .category(Category.builder().categoryId(categoryId).build())
                .build();
    }
}