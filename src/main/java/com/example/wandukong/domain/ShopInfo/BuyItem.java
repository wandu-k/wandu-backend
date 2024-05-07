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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "BuyItem")
public class BuyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemBuyId", unique = true)
    private Long itemBuyId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserDo userDo;

    @ManyToOne
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "categoryID", referencedColumnName = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "buyItem", cascade = CascadeType.ALL)
    private List<BgmList> bgmList;
}