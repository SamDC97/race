package be.exam.race.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Race {

    private Long id;
    private Long calendarId;
    private List<Long> position;
    private List<Long> driverId;
    private List<Integer> points;

    private GP gp;
    private List<Driver> drivers;
    private Calendar calendar;
}
