package com.example.wandukong.domain;

import com.example.wandukong.domain.ShopInfo.BuyItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "Avatar")
public class Avatar {

    @Id
    @Column(name = "userId", unique = true)
    private Long userId;

    @MapsId(value = "userId")
    @OneToOne
    @JoinColumn(name = "userId")
    private UserDo userDo;

    @OneToOne
    @JoinColumn(name = "haedId")
    private BuyItem haed;

    @OneToOne
    @JoinColumn(name = "eyeId")
    private BuyItem eye;

    @OneToOne
    @JoinColumn(name = "mouseId")
    private BuyItem mouse;

    @OneToOne
    @JoinColumn(name = "clothId")
    private BuyItem cloth;

    @Builder
    public Avatar(Long userId, UserDo userDo, BuyItem haed, BuyItem eye, BuyItem mouse, BuyItem cloth) {
        this.userId = userId;
        this.userDo = userDo;
        this.haed = haed;
        this.eye = eye;
        this.mouse = mouse;
        this.cloth = cloth;
    }

    public void AvatarUpdate(Long haed, Long eye, Long mouse, Long cloth) {
        this.haed = BuyItem.builder().itemBuyId(haed).build();
        this.eye = BuyItem.builder().itemBuyId(eye).build();
        this.mouse = BuyItem.builder().itemBuyId(mouse).build();
        this.cloth = BuyItem.builder().itemBuyId(cloth).build();
    }
}
