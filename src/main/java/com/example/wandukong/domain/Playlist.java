package com.example.wandukong.domain;

import java.util.List;

import com.example.wandukong.domain.MiniHome.MiniHome;

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
@Table(name = "Playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlistID", unique = true)
    private Long playlistID;

    @Column(name = "plName")
    private String plName;

    // 플리 삭제시에 홈피도 사라질지 테스트필요(자네...아직도 jpa를 믿나..?)
    @OneToOne(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private MiniHome miniHome;

    @ManyToOne
    @JoinColumn(name = "myplID", referencedColumnName = "myplID")
    private MyPlaylists myPlaylists;
}