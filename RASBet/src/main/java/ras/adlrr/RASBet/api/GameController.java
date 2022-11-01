package ras.adlrr.RASBet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ras.adlrr.RASBet.api.auxiliar.AuxiliarMethods;
import ras.adlrr.RASBet.model.Game;
import ras.adlrr.RASBet.model.Participant;
import ras.adlrr.RASBet.service.GameService;

import java.util.AbstractMap;
import java.util.List;
import java.util.Set;

@RequestMapping("/api/games")
@RestController
public class GameController {
    private final GameService gameService;

    /* **** Game Methods **** */
    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Game> addGame(@RequestBody Game game){
        try{ 
            return ResponseEntity.ok().body(gameService.addGame(game)); 
        }
        catch (Exception e){
            return AuxiliarMethods.createClassBadRequest(e.getMessage(), Game.class);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Game> getGame(@PathVariable("id") int id){
        return ResponseEntity.ok().body(gameService.getGame(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteGame(@PathVariable("id") int id) {
        try {
            gameService.removeGame(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return AuxiliarMethods.createBadRequest(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Game>> getGames() {
        return ResponseEntity.ok().body(gameService.getGames());
    }

    @PutMapping(path = "/{id}/state/{state}")
    public ResponseEntity changeGameState(@PathVariable("id") int id, @PathVariable("state") int state){
        try {
            gameService.changeGameState(id, state);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return AuxiliarMethods.createBadRequest(e.getMessage());
        }
    }

    /* **** Participants Methods **** */
    @GetMapping("/{game_id}/participants")
    public ResponseEntity<Set<Participant>> getGameParticipants(@PathVariable("game_id") int gameID) {
        try{
            return ResponseEntity.ok().body(gameService.getGameParticipants(gameID));
        } catch (Exception e){
            return AuxiliarMethods.createSetBadRequest(e.getMessage(), Participant.class);
        }
    }

    @PutMapping("/{game_id}/participants")
    public ResponseEntity<Participant> addParticipantToGame(@PathVariable("game_id") int gameID, @RequestBody Participant p){
        try{
            gameService.addParticipantToGame(gameID, p);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return AuxiliarMethods.createClassBadRequest(e.getMessage(), Participant.class);
        }
    }

    @DeleteMapping("/participants/{pid}")
    public ResponseEntity deleteParticipant(@PathVariable("pid") int participant_id){
        try{
            gameService.removeParticipant(participant_id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            return AuxiliarMethods.createBadRequest(e.getMessage());
        }
    }

    @GetMapping("/participants/{pid}")
    public ResponseEntity<Participant> getParticipant(@PathVariable("pid") int participant_id){
        return ResponseEntity.ok().body(gameService.getParticipant(participant_id));
    }

    @PutMapping("/participants/{pid}/odd/{odd}")
    public ResponseEntity editOddInParticipant(@PathVariable("pid") int participant_id, @PathVariable("odd") float odd){
        try{
            gameService.editOddInParticipant(participant_id, odd);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            return AuxiliarMethods.createBadRequest(e.getMessage());
        }
    }
}
