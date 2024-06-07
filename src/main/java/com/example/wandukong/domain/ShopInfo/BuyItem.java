package com.example.wandukong.domain.ShopInfo;

import java.time.LocalDate;
import java.util.List;

import com.example.wandukong.domain.Avatar;
import com.example.wandukong.domain.UserDo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    @Column(name = "itemId", unique = true)
    private Long itemId;

    @MapsId(value = "itemId")
    @OneToOne
    @JoinColumn(name = "itemId")
    private Shop shop;

    @Column(name = "buyDate")
    private LocalDate buyDate;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserDo userDo;

    @OneToMany(mappedBy = "buyItem", cascade = CascadeType.ALL)
    private List<BgmList> bgmList;
}