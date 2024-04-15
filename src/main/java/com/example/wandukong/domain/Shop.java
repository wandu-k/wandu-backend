package com.example.wandukong.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@Table(name = "Shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemID", unique = true)
    private Long itemID;

    @Column(name = "itemName")
    private String itemName;

    @OneToMany
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UserDo userDo;

    @OneToMany
    @JoinColumn(name = "categoryID", referencedColumnName = "categoryID")
    private Category category;
}