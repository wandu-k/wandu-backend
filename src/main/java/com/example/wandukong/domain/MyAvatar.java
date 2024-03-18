package com.example.wandukong.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "MyAvatar")
public class MyAvatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemBuyID", unique = true)
    private Long itemBuyID;

    @OneToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UserDo userdo;

    @ManyToOne
    @JoinColumn(name = "itemID", referencedColumnName = "itemID")
    private Shop shop;
}