package ras.adlrr.RASBet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ras.adlrr.RASBet.api.auxiliar.ResponseEntityBadRequest;
import ras.adlrr.RASBet.model.Game;
import ras.adlrr.RASBet.model.Sport;
import ras.adlrr.RASBet.service.SportService;

import java.util.List;

@RequestMapping("/api/sports")
@RestController
public class SportController {
    private final SportService sportService;

    @Autowired
    public SportController(SportService sportService){
        this.sportService = sportService;
    }

    @PostMapping
    public ResponseEntity<Sport> addSport(@RequestBody Sport sport){
        try{ 
            return ResponseEntity.ok().body(sportService.addSport(sport));
        }
        catch (Exception e){
            return new ResponseEntityBadRequest<Sport>().createBadRequest(e.getMessage());
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Sport> getSport(@PathVariable("id") int id){
        return new ResponseEntity<>(sportService.getSport(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity removeSport(@PathVariable int id) {
        try {
            sportService.removeSport(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntityBadRequest().createBadRequest(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Sport>> getListOfSports() {
        return new ResponseEntity<>(sportService.getListOfSports(),HttpStatus.OK);
    }

    @GetMapping("/{sport_name}/games")
    public ResponseEntity<List<Game>> getGamesFromSport(@PathVariable("sport_name") String sport_name) {
        try{
            return ResponseEntity.ok().body(sportService.getGamesFromSport(sport_name));
        }
        catch (Exception e){
            return new ResponseEntityBadRequest<List<Game>>().createBadRequest(e.getMessage());
        }
    }

}
