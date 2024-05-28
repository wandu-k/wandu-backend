package com.example.wandukong.domain.guest;

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
    @JoinColumn(name = "userId")
    private UserDo userDo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private GuestRoom guestRoom;

    @Column(name = "commentContent")
    private String commentContent;

    @Column(name = "commentWriteDate")
    @CreationTimestamp
    private LocalDate writeDate;

    @Builder
    public GuestComment(Long commentId, UserDo userDo, GuestRoom guestRoom, String commentContent, LocalDate writeDate) {
        this.commentId = commentId;
        this.userDo = userDo;
        this.guestRoom = guestRoom;
        this.commentContent = commentContent;
        this.writeDate = writeDate;
    }

    public void changeComment(String commentContent) {
        this.commentContent = commentContent;
    }
}
