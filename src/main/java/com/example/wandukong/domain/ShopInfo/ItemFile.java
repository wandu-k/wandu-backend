package com.example.wandukong.domain.ShopInfo;

import org.checkerframework.checker.units.qual.C;

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
@Table(name = "ItemFile")
public class ItemFile {

    @Id
    @Column(name = "itemId", unique = true)
    private Long itemId;

    @MapsId(value = "itemId")
    @OneToOne
    @JoinColumn(name = "itemId")
    private Shop shop;

    private String name;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "fileName")
    private String fileName;

    @Builder
    public ItemFile(Shop shop, String fileName, String uuid, String name) {
        this.shop = shop;
        this.fileName = fileName;
        this.uuid = uuid;
        this.name = name;
    }
}