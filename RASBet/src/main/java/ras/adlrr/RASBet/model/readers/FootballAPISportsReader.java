package ras.adlrr.RASBet.model.readers;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import ras.adlrr.RASBet.model.APIGameReader;
import ras.adlrr.RASBet.model.Game;
import ras.adlrr.RASBet.model.Participant;

public class FootballAPISportsReader implements APIGameReader{
    private int sport_id;
    private JSONArray games;

    public FootballAPISportsReader(String games, int sport_id){
        this.games = (JSONArray) (new JSONObject(games).get("response"));
    }

    public String getGameExternalId(JSONObject game) {
        JSONObject fixture = (JSONObject) game.get("fixture");
        return fixture.get("id").toString();
    }

    public LocalDateTime getGameDate(JSONObject game) {
        JSONObject fixture = (JSONObject) game.get("fixture");
        String date = (String) fixture.get("date");

        ZonedDateTime zdt = ZonedDateTime.parse(date);
        LocalDateTime dateTime = zdt.toLocalDateTime();
        return dateTime;
    }

    public List<Float> getOdds(JSONObject game){
        String url = "https://v3.football.api-sports.io/odds?season=2022&bet=1&fixture=" + getGameExternalId(game) + "&league=94";
        HttpResponse<String> response = Unirest.get(url)
                                            .header("x-rapidapi-key", "b68a93e4291b512a0f3179eb9ee1bc2b")
                                            .header("x-rapidapi-host", "v3.football.api-sports.io").asString();

        List<Float> res = new ArrayList<>();

        JSONArray oddsFixture = (JSONArray) (new JSONObject(response.getBody()).get("response"));

        if(oddsFixture.length() > 0){
            JSONObject obj = (JSONObject) oddsFixture.get(0);
            JSONArray bookmakers = obj.getJSONArray("bookmakers");
            JSONObject fstBookmaker = (JSONObject) bookmakers.get(0);
            JSONArray bets = fstBookmaker.getJSONArray("bets");
            JSONObject fstBet = (JSONObject) bets.get(0);
            JSONArray values = (JSONArray) fstBet.get("values");

            for(int i = 0; i < 3; i++){
                JSONObject value = (JSONObject) values.get(i);
                res.add(Float.parseFloat((String) value.get("odd")));
            }
        }
        else{
            float tmp = (float) -1.0;
            res.add(tmp); res.add(tmp); res.add(tmp); 
        }
        return res;
    }

    public Set<Participant> getGameParticipants(JSONObject game) {
        JSONObject teams = (JSONObject) game.get("teams");
        JSONObject home = (JSONObject) teams.get("home");
        JSONObject away = (JSONObject) teams.get("away");
        String homeTeam = (String) home.get("name");
        String awayTeam = (String) away.get("name");


        Set<Participant> ps = new HashSet<>();
        //List<Float> odds = getOdds(game);
        //Participant homeP = new Participant(homeTeam, odds.get(0), 0);
        //Participant drawP = new Participant("draw", odds.get(1), 0);
        //Participant awayP = new Participant(awayTeam, odds.get(2), 0);
        Participant homeP = new Participant(homeTeam, 0, 0);
        Participant drawP = new Participant("draw", 0, 0);
        Participant awayP = new Participant(awayTeam, 0, 0);


        ps.add(homeP); ps.add(drawP); ps.add(awayP);

        return ps;
    }

    public int getSportID() {
        return this.sport_id;
    }

    public int getGameState(JSONObject game) {
        JSONObject fixture = (JSONObject) game.get("fixture");
        JSONObject status = (JSONObject) fixture.get("status");
        String status_short = (String) status.get("short");

        if (status_short.equals("NS") || status_short.equals("1H") || status_short.equals("HT")
                    || status_short.equals("2H") || status_short.equals("ET") || status_short.equals("P"))
            return Game.OPEN;
        else
            return Game.CLOSED;
    }

    public String getName(JSONObject game){
        JSONObject teams = (JSONObject) game.get("teams");
        JSONObject home = (JSONObject) teams.get("home");
        JSONObject away = (JSONObject) teams.get("away");
        String homeTeam = (String) home.get("name");
        String awayTeam = (String) away.get("name");

        return homeTeam + " vs " + awayTeam;
    }

    @Override
    public List<Game> getAPIGames() {
        List<Game> res = new ArrayList<>();

        for(int i = 0; i < games.length(); i++){
            JSONObject obj = (JSONObject) games.get(i);
            JSONObject league = (JSONObject) obj.get("league");

            if (league.get("round").equals("Regular Season - 10")){
<<<<<<< HEAD
                Game g = new Game(getGameExternalId(obj), getGameDate(obj), getGameState(obj), getName(obj), getSportID(), getGameParticipants(obj));
                res.add(g);
=======
                Game g = new Game(getGameExternalId(obj), getGameDate(obj), getGameState(obj), null, getSportID(), getGameParticipants(obj));
                // TODO - RAy
                //gameRepository.addGame(g);
                k++;
>>>>>>> 1c8c7ccc718bdd263f3748eaf1e391dcdf092978
            }
        }
        return res;
    }
    
}
