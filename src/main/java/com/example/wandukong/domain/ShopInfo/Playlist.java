package com.example.wandukong.domain.ShopInfo;

import java.util.List;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.example.wandukong.domain.UserDo;

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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "Playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlistId", unique = true)
    private Long playlistId;

    @Column(name = "plName")
    private String plName;

    @CreationTimestamp
    @Column(name = "plDate")
    private LocalDate plDate;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BgmList> bgmList;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserDo userDo;

    @Builder
    public Playlist(Long playlistId, LocalDate plDate, String plName, UserDo userDo, List<BgmList> bgmList) {
        this.playlistId = playlistId;
        this.plName = plName;
        this.plDate = plDate;
        this.userDo = userDo;
        this.bgmList = bgmList;
    }

    public void updatePlaylist(String plName) {
        this.plName = plName;
    }

}