package com.example.wandukong.domain.ShopInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "ItemFile")
public class ItemFile {

    @Id
    @Column(name = "itemId", unique = true)
    private Long itemId;

    @MapsId(value = "itemId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Shop shop;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Builder
    public ItemFile(Long itemId, Shop shop, String fileName, String thumbnail) {
        this.itemId = itemId;
        this.shop = shop;
        this.fileName = fileName;
        this.thumbnail = thumbnail;
    }

    public void changeFileName(String fileName) {
        this.fileName = fileName;
    }
}