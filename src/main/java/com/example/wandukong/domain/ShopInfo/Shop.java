package com.example.wandukong.domain.ShopInfo;

import java.util.List;

import com.example.wandukong.domain.UserDo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "Shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId", unique = true)
    private Long itemId;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserDo userDo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategoryId", referencedColumnName = "subcategoryId")
    private ShopSubCategory shopSubcategory;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BuyItem> buyItem;

    @OneToOne(mappedBy = "shop", fetch = FetchType.LAZY)
    private ItemFile itemFile;

    // 정보 수정
    public void updateItem(String itemName, int price) {
        this.itemName = itemName;
        this.price = price;
    }

    @Builder
    public Shop(Long itemId, String itemName, int price, UserDo userDo, ShopSubCategory shopSubcategory,
            List<BuyItem> buyItem, ItemFile itemFile) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.userDo = userDo;
        this.shopSubcategory = shopSubcategory;
        this.buyItem = buyItem;
        this.itemFile = itemFile;
    }

}