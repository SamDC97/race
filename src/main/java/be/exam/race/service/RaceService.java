package be.exam.race.service;

import be.exam.race.domain.PositionEntity;
import be.exam.race.domain.RaceEntity;
import be.exam.race.domain.repository.PositionEntityRepository;
import be.exam.race.domain.repository.RaceRepository;
import be.exam.race.service.dto.Driver;
import be.exam.race.service.dto.Race;
import be.exam.race.service.mapper.RaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static be.exam.race.domain.Ranking.from;

@Service
public class RaceService {

    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private PositionEntityRepository positionEntityRepository;
    @Autowired
    private RaceMapper raceMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.driver}")
    private String driverURL;

    public List<Race> generateRaces(int numberOfRaces) {
        List<RaceEntity> races = new ArrayList<>();

        for (int i = 1; i <= numberOfRaces; i++) {
            RaceEntity race = generateRace(i);
            raceRepository.save(race);
            races.add(race);
        }
        List<Race> raceResults = raceMapper.toDTO(races);

        return raceResults;
    }

    public List<Race> getAll() {
        List<Race> raceList = StreamSupport.stream(raceRepository.findAll().spliterator(), false)
                .map(r -> raceMapper.toDTO(r))
                .collect(Collectors.toList());
        return raceList;
    }

    public Race getById(Long id) {
        Optional<RaceEntity> optionalRaceEntity = raceRepository.findById(id);
        if (optionalRaceEntity.isPresent()) {
            Race race = raceMapper.toDTO(optionalRaceEntity.get());
            return race;
        }
        return null;
    }

    private RaceEntity generateRace(int raceId) {
        return RaceEntity.builder()
                .positionEntity(generatePositions())
                .build();
    }

    private List<PositionEntity> generatePositions() {
        List<Long> driverIds = getRESTDrivers().stream().map(driver -> driver.getId()).collect(Collectors.toList());
        Collections.shuffle(driverIds);
        List<PositionEntity> positions = new ArrayList<>();
        for (int i = 1; i <= driverIds.size(); i++) {
            PositionEntity positionEntity = PositionEntity.builder()
                    .driverId(driverIds.get(i-1))
                    .ranking(from(i))
                    .build();
            positionEntityRepository.save(positionEntity);
            positions.add(positionEntity);
        }
        return positions;
    }

    private List<Driver> getRESTDrivers() {
        ResponseEntity<List<Driver>> driverListEntity = restTemplate.exchange(driverURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Driver>>() {
                }, Collections.emptyMap());
        if (driverListEntity.getStatusCode() == HttpStatus.OK) {
            return driverListEntity.getBody();
        }
        return null;
    }
}
