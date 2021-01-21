package be.exam.race.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PositionVO {

    @Id
    private Long id;
    private Ranking ranking;
    private Long driverId;

}
