package com.example.wandukong.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "Ask")
public class Ask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "askId", unique = true)
    private Long askId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserDo userDo;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "writeDate")
    @CreationTimestamp
    private LocalDate writeDate;

    @Column(name = "solveState")
    private int solveState;

    @Column(name = "hideState")
    private int hideState;

    @Column(name = "count", columnDefinition = "int default 0")
    private int count;

    @OneToMany(mappedBy = "ask", cascade = CascadeType.ALL)
    private List<AdminComment> adminComments = new ArrayList<>();

    @Builder
    public Ask(Long askId, UserDo userDo, String title, String content, LocalDate writeDate, int solveState,
            int hideState, int count, List<AdminComment> adminComments) {
        this.askId = askId;
        this.userDo = userDo;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
        this.solveState = solveState;
        this.hideState = hideState;
        this.count = count;
        this.adminComments = adminComments;
    }

    public void changeAsk(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void changeState(int solveState, int hideState) {
        this.solveState = solveState;
        this.hideState = hideState;
    }

    public void changeCount(int count) {
        this.count = count++;
    }

}