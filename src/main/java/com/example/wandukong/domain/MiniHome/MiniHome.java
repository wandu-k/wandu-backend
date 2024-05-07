package com.example.wandukong.domain.MiniHome;

import java.util.ArrayList;
import java.util.List;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.Playlist;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "MiniHome")
public class MiniHome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hpId", unique = true)
    private Long hpId;

    @Column(name = "statusM")
    private String statusM;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "hpToday")
    private int hpToday;

    @Column(name = "allVisit")
    private int allVisit;

    @Column(name = "hpOpen")
    private int hpOpen;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserDo userDo;

    @OneToOne
    @JoinColumn(name = "playlistId", referencedColumnName = "playlistId")
    private Playlist playlist;

    @OneToMany(mappedBy = "miniHome", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MiniHomePost> miniHomePost = new ArrayList<>();

    @Builder
    public MiniHome(Long hpId, String statusM, String introduction, int hpToday, int allVisit, int hpOpen,
            UserDo userDo, List<MiniHomePost> miniHomePost) {
        this.hpId = hpId;
        this.statusM = statusM;
        this.introduction = introduction;
        this.hpToday = hpToday;
        this.allVisit = allVisit;
        this.hpOpen = hpOpen;
        this.userDo = userDo;
        this.miniHomePost = miniHomePost;
    }

    public void viewCount(int allVisit, int hpToday) {
        this.hpToday = hpToday;
        this.allVisit = allVisit;
    }

}