package ras.adlrr.RASBet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ras.adlrr.RASBet.api.auxiliar.ResponseEntityBadRequest;
import ras.adlrr.RASBet.model.Bet;
import ras.adlrr.RASBet.service.interfaces.bets.IBetService;

import java.util.List;

@RequestMapping("/api/bets")
@RestController
@CrossOrigin
public class BetController {
    private final IBetService betService;

    @Autowired
    public BetController(@Qualifier("betGameFacade") IBetService betService) {
        this.betService = betService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Bet> getBet(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(betService.getBet(id));
    }

    @PostMapping
    public ResponseEntity<Bet> addBet(@RequestBody Bet bet) {
        try{
            return ResponseEntity.ok().body(betService.addBet(bet));
        }
        catch (Exception e){
            return new ResponseEntityBadRequest<Bet>().createBadRequest(e.getMessage());
        }
    }

    @PutMapping(path = "/withdraw")
    public ResponseEntity<Float> withdrawBetWinnings(@RequestParam("bet_id") int bet_id){
        try {
            return ResponseEntity.ok().body(betService.closeBet(bet_id));
        }catch (Exception e){
            return new ResponseEntityBadRequest<Float>().createBadRequest(e.getMessage());
        }
    }

    @GetMapping(path = "/gambler/{id}")
    public ResponseEntity<List<Bet>> getGamblerBets(@PathVariable("id") int userID) {
        try{ return ResponseEntity.ok().body(betService.getGamblerBets(userID)); }
        catch (Exception e){
            return new ResponseEntityBadRequest<List<Bet>>().createBadRequest(e.getMessage());
        }
    }

    @GetMapping(path = "/gambler/{id}/{direction}")
    public ResponseEntity<List<Bet>> getGamblerBets(@PathVariable("id") int gambler_id, @PathVariable("direction") String direction) {
        try {
            Sort.Direction sortDirection;
            try {
                sortDirection = Sort.Direction.valueOf(direction);
            }catch (IllegalArgumentException iae){
                throw new Exception("Sort direction must be \"ASC\" or \"DESC\"");
            }
            return ResponseEntity.ok().body(betService.getGamblerBets(gambler_id, sortDirection));
        }catch (Exception e){
            return new ResponseEntityBadRequest<List<Bet>>().createBadRequest(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity removeBet(@PathVariable("id") int betID) {
        try {
            betService.removeBet(betID);
            return new ResponseEntity(HttpStatus.OK); }
        catch (Exception e){
            return new ResponseEntityBadRequest().createBadRequest(e.getMessage());
        }
    }
}