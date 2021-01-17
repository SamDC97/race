package be.exam.race.service.mapper;

import be.exam.race.domain.RaceEntity;
import be.exam.race.service.dto.Calendar;
import be.exam.race.service.dto.Driver;
import be.exam.race.service.dto.GP;
import be.exam.race.service.dto.Race;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RaceMapper {

    public RaceEntity toEntity(Race race){
        return new RaceEntity(race.getId(), race.getCalendarId(), race.getPosition(), race.getDriverId(), race.getPoints());
    }

    public Race toDTO(RaceEntity raceEntity){
        return new Race(raceEntity.getId(), raceEntity.getCalendarId(),raceEntity.getPosition(), raceEntity.getDriverId(), raceEntity.getPoints(),
                new GP(), new ArrayList<Driver>(), new Calendar());
    }
}