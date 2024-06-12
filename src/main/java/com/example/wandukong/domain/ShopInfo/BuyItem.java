package com.example.wandukong.domain.ShopInfo;

import java.time.LocalDate;
import java.util.List;

import com.example.wandukong.domain.Avatar;
import com.example.wandukong.domain.UserDo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "BuyItem")
public class BuyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buyItemId")
    private Long buyItemId;

    @Column(name = "buyDate")
    private LocalDate buyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserDo userDo;

    @OneToMany(mappedBy = "buyItem", cascade = CascadeType.ALL)
    private List<BgmList> bgmList;

    @OneToMany(mappedBy = "head", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avatar> head;

    @OneToMany(mappedBy = "eye", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avatar> eye;

    @OneToMany(mappedBy = "mouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avatar> mouse;

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avatar> cloth;

    public BuyItem(Long buyItemId, LocalDate buyDate, Shop shop, UserDo userDo) {
        this.buyItemId = buyItemId;
        this.buyDate = buyDate;
        this.shop = shop;
        this.userDo = userDo;
    }

}