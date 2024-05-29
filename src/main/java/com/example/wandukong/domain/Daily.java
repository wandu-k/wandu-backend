package com.example.wandukong.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Daily")
public class Daily {

    @Id
    @Column(name = "userId", unique = true)
    private Long userId;

    @MapsId(value = "userId")
    @OneToOne
    @JoinColumn(name = "userId")
    private UserDo userDo;

    @Column(name = "date")
    private LocalDate date;

    public Daily(Long userId, UserDo userDo, LocalDate date) {
        this.userId = userId;
        this.userDo = userDo;
        this.date = date;
    }

}
