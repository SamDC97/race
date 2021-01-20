package be.exam.race.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Position {

    private Long driverId;
    private Integer rank;
    private Integer points;
}
