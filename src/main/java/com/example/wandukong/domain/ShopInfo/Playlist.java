package com.example.wandukong.domain.ShopInfo;

import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.joda.time.LocalDate;

import com.example.wandukong.domain.UserDo;
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

    // 플리 삭제시에 홈피도 사라질지 테스트필요(자네...아직도 jpa를 믿나..?)
    @OneToOne(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private MiniHome miniHome;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BgmList> bgmList;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserDo userDo;

    @Builder
    public Playlist(Long playlistId, LocalDate plDate, String plName, UserDo userDo, List<BgmList> bgmList,
            MiniHome miniHome) {
        this.playlistId = playlistId;
        this.plName = plName;
        this.plDate = plDate;
        this.userDo = userDo;
        this.bgmList = bgmList;
        this.miniHome = miniHome;
    }

    public void updatePlaylist(String plName) {
        this.plName = plName;
    }

}