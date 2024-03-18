package com.example.wandukong.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlistID", unique = true)
    private Long playlistID;

    @Column(name = "plName")
    private String plName;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UserDo userdo;

    @ManyToOne
    @JoinColumn(name = "hpID", referencedColumnName = "hpID")
    private Playlist playlist;
}