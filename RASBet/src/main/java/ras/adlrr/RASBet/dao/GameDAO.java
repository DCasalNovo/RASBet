package ras.adlrr.RASBet.dao;

import ras.adlrr.RASBet.model.Game;
import java.time.LocalDateTime;
import java.util.List;

public interface GameDAO {
    List<Game> getGames();
    Game getGame(int id);
    int addGame(Game g);
    int suspendGame(int id);
    int resumeGame(int id);
    int changeGameDate(int id, LocalDateTime date);
}
