package ras.adlrr.RASBet.service.interfaces.sports;

import java.util.List;

import ras.adlrr.RASBet.model.Game;

public interface IGameService {
    public List<Game> getGames();

    public List<Game> getGamesSorted();

    public List<Game> getOngoingGames();

    public List<Game> getGamesFromSport(String sport);

    public List<Game> updateGames() throws Exception;

    public Game getGame(int id);

    public Game addGame(Game newGame) throws Exception;

    public void addGames(List<Game> games) throws Exception;

    public void removeGame(int id) throws Exception;

    public void closeGame(int id) throws Exception;

    public void changeGameState(int id, int state) throws Exception;

    public boolean gameExistsById(int id);
}
