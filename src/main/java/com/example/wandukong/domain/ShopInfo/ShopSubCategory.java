package com.example.wandukong.domain.ShopInfo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "ShopSubCategory")
public class ShopSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcategoryId", unique = true)
    private Long subcategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category category;

    @Column
    private String subcategoryName;

    @OneToMany(mappedBy = "shopSubcategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shop> shop;

    @Builder
    public ShopSubCategory(Long subcategoryId, Category category, String subcategoryName) {
        this.subcategoryId = subcategoryId;
        this.category = category;
        this.subcategoryName = subcategoryName;
    }
}
