package com.example.wandukong.domain.ask;

import com.example.wandukong.domain.UserDo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "AdminComment")
public class AdminComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId", unique = true)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserDo userDo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "askId")
    private Ask ask;

    @Column(name = "commentContent")
    private String commentContent;

    @CreationTimestamp
    @Column(name = "writeDate")
    private LocalDate writeDate;

    @Builder
    public AdminComment(Long commentId, UserDo userDo, Ask ask, String commentContent, LocalDate writeDate) {
        this.commentId = commentId;
        this.userDo = userDo;
        this.ask = ask;
        this.commentContent = commentContent;
        this.writeDate = writeDate;
    }

    public void changeContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
