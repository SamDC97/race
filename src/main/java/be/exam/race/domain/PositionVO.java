package be.exam.race.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
public class PositionVO {

    private Ranking ranking;
    private Long driverId;

}
