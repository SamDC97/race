package be.exam.race.service.mapper;

import be.exam.race.domain.PositionVO;
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
        return new RaceEntity(race.getId(), toPositionEntities(race.getPositions()));
    }

    public Race toDTO(RaceEntity raceEntity){
        return new Race(raceEntity.getId(), toPositionDTOs(raceEntity.getPositionVO()));
    }

    private List<Position> toPositionDTOs(List<PositionVO> positionVOs) {
        List<Position> positions = new ArrayList<>();
        for(PositionVO positionVO : positionVOs){
            positions.add(new Position(positionVO.getDriverId(), positionVO.getRanking().getRank(), positionVO.getRanking().getPoints()));
        }
        return positions;
    }

    private List<PositionVO> toPositionEntities(List<Position> positions) {
        List<PositionVO> positionVOs = new ArrayList<>();
        for(Position position : positions){
            positionVOs.add(new PositionVO(Ranking.from(position.getRank()), position.getDriverId()));
        }
        return positionVOs;
    }

    public List<Race> toDTO(List<RaceEntity> raceEntities) {
        List<Race> dtos = new ArrayList<>();
        for (RaceEntity raceEntity : raceEntities) {
            dtos.add(toDTO(raceEntity));
        }
        return dtos;
    }
}