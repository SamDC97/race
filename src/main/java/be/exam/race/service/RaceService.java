package be.exam.race.service;

import be.exam.race.domain.RaceEntity;
import be.exam.race.domain.repository.RaceRepository;
import be.exam.race.service.dto.Calendar;
import be.exam.race.service.dto.Driver;
import be.exam.race.service.dto.GP;
import be.exam.race.service.dto.Race;
import be.exam.race.service.mapper.RaceMapper;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RaceService {

    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private RaceMapper raceMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.driver}")
    private String driverURL;
    @Value("${url.calendar}")
    private String calendarURL;

    public List<Race> generateRaces(){
        List<Race> races = new ArrayList<>();
        List<Driver> driverList = new ArrayList<>();
        List<Calendar> calendarList = new ArrayList<>();
        List<Long> positions = new ArrayList<>();
        List<Integer> points = Arrays.asList(25, 18, 15, 12, 10, 8, 7, 4, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        try {
            calendarList = getRESTCalendars(calendarList);

            driverList = getRESTDrivers();

            for (int i = 0; i < calendarList.size(); i++){
                Long index = Long.valueOf(i);
                Collections.shuffle(driverList);
                List<Long> driverIds = StreamSupport.stream(driverList.spliterator(), false)
                        .map(d -> d.getId())
                        .collect(Collectors.toList());
                races.add(new Race(index, calendarList.get(i).getId(), positions, driverIds, points, calendarList.get(i).getGp(), driverList, calendarList.get(i)));
            }

            return races;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Calendar> getRESTCalendars(List<Calendar> calendarList) throws URISyntaxException {
        URI calendarURI = new URI(calendarURL);

        ResponseEntity<List<Calendar>> calendarListEntity = restTemplate.exchange(calendarURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Calendar>>() {}, Collections.emptyMap() ) ;
        if (calendarListEntity.getStatusCode() == HttpStatus.OK) {
            calendarList = calendarListEntity.getBody();
        }
        return calendarList;
    }

    public List<Race> getAll(){
        List<Race> raceList = StreamSupport.stream(raceRepository.findAll().spliterator(), false)
                .map(r -> raceMapper.toDTO(r))
                .collect(Collectors.toList());
        for (Race race : raceList){
            race.setCalendar(getRESTCalendar(race.getCalendarId()));
            race.setGp(new GP(race.getCalendar().getGpId(), race.getCalendar().getGp().getName()));
            race.setDrivers(getRESTDrivers());
        }
        return raceList;
    }

    public Race getById(Long id){
        Optional<RaceEntity> optionalRaceEntity = raceRepository.findById(id);
        if (optionalRaceEntity.isPresent()){
            Race race = raceMapper.toDTO(optionalRaceEntity.get());
            race.setCalendar(getRESTCalendar(race.getCalendarId()));
            race.setGp(new GP(race.getCalendar().getGpId(), race.getCalendar().getGp().getName()));
            race.setDrivers(getRESTDrivers());
            return race;
        }
        return null;
    }

    private Calendar getRESTCalendar(Long id){
        try{
            URI calendarURI = new URI(calendarURL);
            return restTemplate.getForObject(calendarURI, Calendar.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Driver> getRESTDrivers(){
        try{
            URI driverURI = new URI(driverURL);

            ResponseEntity<List<Driver>> driverListEntity = restTemplate.exchange(driverURL, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Driver>>() {}, Collections.emptyMap() ) ;
            if (driverListEntity.getStatusCode() == HttpStatus.OK) {
                return driverListEntity.getBody();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
