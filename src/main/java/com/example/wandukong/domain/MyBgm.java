package com.example.wandukong.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "MyBgm")
public class MyBgm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicBuyID", unique = true)
    private Long musicBuyID;

    @OneToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UserDo userDo;

    @ManyToOne
    @JoinColumn(name = "itemID", referencedColumnName = "itemID")
    private Shop shop;
}