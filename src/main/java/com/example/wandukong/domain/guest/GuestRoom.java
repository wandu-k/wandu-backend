package com.example.wandukong.domain.guest;

import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.domain.UserDo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GuestRoom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId", unique = true)
    private Long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserDo userDo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hpId")
    private MiniHome miniHome;

    @Column(name = "mainContent")
    private String mainContent;

    @Column(name = "roomWriteDate")
    @CreationTimestamp
    private LocalDate writeDate;

    @Builder
    public GuestRoom(Long roomId, UserDo userDo, MiniHome miniHome, String mainContent, LocalDate writeDate) {
        this.roomId = roomId;
        this.userDo = userDo;
        this.miniHome = miniHome;
        this.mainContent = mainContent;
        this.writeDate = writeDate;
    }

    public void changeContent(String mainContent) {
        this.mainContent = mainContent;
    }
}
