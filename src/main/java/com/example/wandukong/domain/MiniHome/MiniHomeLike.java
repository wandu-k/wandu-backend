package com.example.wandukong.domain.MiniHome;

import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.Playlist;
import com.example.wandukong.domain.UserDo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "MiniHomeLike")
public class MiniHomeLike {

    @EmbeddedId
    private MiniHomeLikePK miniHomeLikePK;

    @MapsId("hpId")
    @ManyToOne(fetch = FetchType.LAZY)
    private MiniHome miniHome;

    @MapsId("userId") // 복합 키를 사용하는 경우에만 @MapsId를 사용합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    private UserDo userDo;

    @CreationTimestamp
    @Column(name = "date")
    private LocalDate date;

    @Builder
    public MiniHomeLike(MiniHomeLikePK miniHomeLikePK, MiniHome miniHome, UserDo userDo, LocalDate date) {
        this.miniHomeLikePK = miniHomeLikePK;
        this.miniHome = miniHome;
        this.userDo = userDo;
        this.date = date;
    }
}
