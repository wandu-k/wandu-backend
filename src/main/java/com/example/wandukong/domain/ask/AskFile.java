package com.example.wandukong.domain.ask;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "AskFile")
public class AskFile {

    @Id
    @Column(name = "askId")
    private Long askId;

    @MapsId(value = "askId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "askId")
    private Ask ask;

    @Column(name = "fileName")
    private String fileName;

    @Builder
    public AskFile(Long askId, String fileName) {
        this.askId = askId;
        this.fileName = fileName;
    }

    public void changeFileName(String fileName) {
        this.fileName = fileName;
    }
}
