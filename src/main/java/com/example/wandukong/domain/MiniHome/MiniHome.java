package com.example.wandukong.domain.MiniHome;

import com.example.wandukong.domain.UserDo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MiniHome")
public class MiniHome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hpID", unique = true)
    private Long hpID;

    @Column(name = "statusM")
    private String statusM;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "hpToday")
    private int hpToday;

    @Column(name = "allVisit")
    private int allVisit;

    @Column(name = "hpOpen")
    private int hpOpen;

    @OneToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UserDo userDo;

}