package be.exam.race.web;

import be.exam.race.service.RaceService;
import be.exam.race.service.dto.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RaceResource {

    @Autowired
    private RaceService raceService;

    @GetMapping("/generate-races")
    public ResponseEntity<List<Race>> generateCalendar(){
        return new ResponseEntity<>(raceService.generateRaces(), HttpStatus.OK);
    }

    @GetMapping("/calendars")
    public ResponseEntity<List<Race>> getAll(){
        return new ResponseEntity<>(raceService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/calendar/{id}")
    public ResponseEntity<Race> getById(@PathVariable Long id){
        return new ResponseEntity<>(raceService.getById(id), HttpStatus.OK);
    }
}
