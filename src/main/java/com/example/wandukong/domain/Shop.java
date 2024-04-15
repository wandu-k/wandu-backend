package com.example.wandukong.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UserDo userDo;

    @ManyToOne
    @JoinColumn(name = "categoryID", referencedColumnName = "categoryID")
    private Category category;

    @OneToMany(mappedBy = "mybgm", cascade = CascadeType.ALL, orphanRemoval = true)
    private MyBgm mybgm;

    @OneToMany(mappedBy = "myavatar", cascade = CascadeType.ALL, orphanRemoval = true)
    private MyAvatar myavatar;
}