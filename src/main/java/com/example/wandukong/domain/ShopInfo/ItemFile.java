package com.example.wandukong.domain.ShopInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "ItemFile")
public class ItemFile {

    @Id
    private Long itemfileId;
    private String shopitemfile;

    @MapsId
    @OneToOne
    @JoinColumn(name = "itemId", referencedColumnName = "itemId", unique = true)
    private Shop shop;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "fileName")
    private String fileName;

    @Builder
    public ItemFile(Shop shop, String fileName, String uuid, Long itemfileId, String shopitemfile) {
        this.itemfileId = itemfileId;
        this.shop = shop;
        this.fileName = fileName;
        this.uuid = uuid;
        this.shopitemfile = shopitemfile;
    }
}