package com.example.wandukong.domain.MiniHome;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MiniHomeLikePK implements Serializable {

    private Long hpId;
    private Long userId;
}
