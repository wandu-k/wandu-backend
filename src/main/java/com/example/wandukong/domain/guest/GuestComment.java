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

@Entity
@Table(name = "GuestComment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId", unique = true)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hpId")
    private MiniHome miniHome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserDo userDo;

    @Column(name = "mainContent")
    private String mainContent;

    @Column(name = "roomWriteDate")
    @CreationTimestamp
    private LocalDate writeDate;

    @Builder
    public GuestComment(MiniHome miniHome, UserDo userDo, String mainContent, LocalDate writeDate) {
        this.miniHome = miniHome;
        this.userDo = userDo;
        this.mainContent = mainContent;
        this.writeDate = writeDate;
    }

    public void changeContent(String mainContent) {
        this.mainContent = mainContent;
    }
}
