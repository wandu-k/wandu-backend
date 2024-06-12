package com.example.wandukong.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "AlbumFile")
public class AlbumFile {

    @Id
    @Column(name = "albumId", unique = true)
    private Long albumId;

    @MapsId(value = "albumId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "albumId")
    private Album album;

    @Column(name = "fileName")
    private String fileName;

    public AlbumFile(Long albumId, Album album, String fileName) {
        this.albumId = albumId;
        this.album = album;
        this.fileName = fileName;
    }

}
