package com.example.wandukong.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "Daily")
public class Daily {

    @Id
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserDo userDo;

    @Column(name = "date")
    private LocalDate date;

    public Daily(Long id, UserDo userDo, LocalDate date) {
        this.id = id;
        this.userDo = userDo;
        this.date = date;
    }

}
