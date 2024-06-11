package com.example.wandukong.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.Playlist;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BgmListPK implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyItemId", referencedColumnName = "buyItemId")
    private BuyItem buyItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlistId", referencedColumnName = "playlistId")
    private Playlist playlist;

    public BgmListPK(BuyItem buyItem, Playlist playlist) {
        this.buyItem = buyItem;
        this.playlist = playlist;
    }

}
