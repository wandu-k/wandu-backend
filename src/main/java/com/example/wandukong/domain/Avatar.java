package com.example.wandukong.domain;

import com.example.wandukong.domain.ShopInfo.BuyItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserDo userDo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head")
    private BuyItem head;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eye")
    private BuyItem eye;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mouse")
    private BuyItem mouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cloth")
    private BuyItem cloth;

    @Builder
    public Avatar(Long userId, UserDo userDo, BuyItem head, BuyItem eye, BuyItem mouse, BuyItem cloth) {
        this.userId = userId;
        this.userDo = userDo;
        this.head = head;
        this.eye = eye;
        this.mouse = mouse;
        this.cloth = cloth;
    }

    public void setHead(BuyItem head) {
        this.head = head;
    }

    public void setEye(BuyItem eye) {
        this.eye = eye;
    }

    public void setMouse(BuyItem buyItem) {
        this.mouse = buyItem;
    }

    public void setCloth(BuyItem cloth) {
        this.cloth = cloth;
    }
}
