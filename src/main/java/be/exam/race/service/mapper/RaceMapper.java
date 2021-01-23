package be.exam.race.service.mapper;

import be.exam.race.domain.PositionEntity;
import be.exam.race.domain.RaceEntity;
import be.exam.race.domain.Ranking;
import be.exam.race.service.dto.Position;
import be.exam.race.service.dto.Race;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RaceMapper {

    public RaceEntity toEntity(Race race){
        return RaceEntity.builder()
                .positionEntity(toPositionEntities(race.getPositions()))
                .circuitId(race.getCircuitId())
                .build();
    }

    public Race toDTO(RaceEntity raceEntity){
        return new Race(raceEntity.getId(), toPositionDTOs(raceEntity.getPositionEntity()), raceEntity.getCircuitId());
    }

    private List<Position> toPositionDTOs(List<PositionEntity> positionEntities) {
        List<Position> positions = new ArrayList<>();
        for(PositionEntity positionEntity : positionEntities){
            positions.add(new Position(positionEntity.getDriverId(), positionEntity.getRanking().getRank(), positionEntity.getRanking().getPoints()));
        }
        return positions;
    }

    private List<PositionEntity> toPositionEntities(List<Position> positions) {
        List<PositionEntity> positionEntities = new ArrayList<>();
        for(int i = 0; i <= positions.size()+1; i++){
            positionEntities.add(PositionEntity.builder()
                    .ranking(Ranking.from(positions.get(i).getRank()))
                    .driverId(positions.get(i).getDriverId())
                    .build());
        }
        return positionEntities;
    }

    public List<Race> toDTO(List<RaceEntity> raceEntities) {
        List<Race> dtos = new ArrayList<>();
        for (RaceEntity raceEntity : raceEntities) {
            dtos.add(toDTO(raceEntity));
        }
        return dtos;
    }
}