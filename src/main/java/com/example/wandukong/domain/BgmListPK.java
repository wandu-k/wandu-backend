package com.example.wandukong.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
public class BgmListPK implements Serializable {

    private Long playlistId;
    private Long buyItemId;

    public BgmListPK() {
    }

}
