package com.example.wandukong.domain.ShopInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @Column(name = "itemID")
    private Shop itemID;

    @OneToOne
    @JoinColumn(name = "itemID", referencedColumnName = "itemID", unique = true)
    private Shop shop;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "fileName")
    private String fileName;

    @Builder
    public ItemFile(Shop itemID, String fileName, String uuid) {
        this.itemID = itemID;
        this.fileName = fileName;
        this.uuid = uuid;
    }
}