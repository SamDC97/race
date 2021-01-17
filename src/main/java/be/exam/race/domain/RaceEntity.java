package be.exam.race.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceEntity {

    @Id
    private Long id;
    private Long calendarId;
    private List<Long> position;
    private List<Long> driverId;
    private List<Integer> points;
}
