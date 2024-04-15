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
@Table(name = "MyPlaylists")
public class MyPlaylists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "myplID", unique = true)
    private Long myplID;

    @Column(name = "playlistID", unique = true)
    private Long playlistID;

    @ManyToOne
    @JoinColumn(name = "playlistID", referencedColumnName = "playlistID")
    private Playlist playlist;

    @OneToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UserDo userDo;
}