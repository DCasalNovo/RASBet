package ras.adlrr.RASBet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ras.adlrr.RASBet.dao.GameRepository;
import ras.adlrr.RASBet.dao.SportRepository;
import ras.adlrr.RASBet.model.Game;
import ras.adlrr.RASBet.model.Sport;

import java.util.List;

@Service
public class SportService {
    private final SportRepository sportRepository;
    private final GameRepository gameRepository;

    @Autowired
    public SportService(SportRepository sportRepository, GameRepository gameRepository){
        this.sportRepository = sportRepository;
        this.gameRepository = gameRepository;
    }

    public Sport addSport(Sport sport) throws Exception {
        if(sportRepository.existsById(sport.getId()))
            throw new Exception("Sport already exists!");

        sportRepository.save(sport);
        
        return sport;
    }

    public Sport getSport(String id){
        return sportRepository.findById(id).orElse(null);
    }

    public void removeSport(String id) throws Exception {
        if(!sportRepository.existsById(id))
            throw new Exception("Sport needs to exist to be removed!");

        sportRepository.deleteById(id);
    }

    public List<Sport> getListOfSports() {
        return sportRepository.findAll();
    }

    public List<Game> getGamesFromSport(String sport) throws Exception {
        Sport s = sportRepository.findById(sport).orElse(null);
        if(s == null)
            throw new Exception("Sport not found!");

        return gameRepository.findAllBySportId(s.getId());
    }

    public boolean sportExistsById(String id) {
        return sportRepository.existsById(id);
    }

    public Sport findSportById(String id) {
        return sportRepository.findById(id).orElse(null);
    }
}
