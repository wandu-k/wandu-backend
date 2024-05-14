package com.example.wandukong.domain.ShopInfo;

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
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "fileName")
    private String fileName;

    @Builder
    public ItemFile(String fileName, String uuid, Long itemId) {
        this.itemId = itemId;
        this.fileName = fileName;
        this.uuid = uuid;
    }
}