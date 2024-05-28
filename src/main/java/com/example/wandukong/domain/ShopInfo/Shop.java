package com.example.wandukong.domain.ShopInfo;

import java.util.List;

import com.example.wandukong.domain.UserDo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
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

    @Column(name = "artist")
    private String artist;

    @Column(name = "price")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserDo userDo;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BuyItem> buyItem;

    @OneToOne(mappedBy = "shop")
    private ItemFile itemFile;

    // 정보 수정
    public void updateItem(String itemName) {
        this.itemName = itemName;
    }

}