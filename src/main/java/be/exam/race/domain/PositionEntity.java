package be.exam.race.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Ranking ranking;
    private Long driverId;

}
