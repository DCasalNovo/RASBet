package ras.adlrr.RASBet.model.readers;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import ras.adlrr.RASBet.model.APIGameReader;
import ras.adlrr.RASBet.model.Game;
import ras.adlrr.RASBet.model.Participant;

public class FootballAPISportsReader extends APIGameReader{
    private String sport_id;
    private String currentRound;

    public FootballAPISportsReader(String sport_id){
        ReadJSONBehaviour readMethod = new ReadJSONFromExternalAPI();
        super.setReadMethod(readMethod);
        this.sport_id = sport_id;
    }

    @Override
    public List<Game> getAPIGames() {
        String leagueID = "94";
        String season = "2022";
        String url = "https://v3.football.api-sports.io/fixtures/rounds?league=" + leagueID + "&season=" + season + "&current=true";
        String roundResponse = super.readJSON(url, "jsons/current_round_" + leagueID + ".json", "b68a93e4291b512a0f3179eb9ee1bc2b");
        JSONArray round = (JSONArray) (new JSONObject(roundResponse).get("response"));
        this.currentRound = (String) round.get(0);

        url = "https://v3.football.api-sports.io/fixtures?league=" + leagueID + "&season=" + season;
        String response = super.readJSON(url, "jsons/jogos.json", "b68a93e4291b512a0f3179eb9ee1bc2b");
        JSONArray games = (JSONArray) (new JSONObject(response).get("response"));
        List<Game> res = new ArrayList<>();

        for(int i = 0; i < games.length(); i++){
            JSONObject obj = (JSONObject) games.get(i);
            JSONObject league = (JSONObject) obj.get("league");

            if (league.get("round").equals(this.currentRound)){
                Game g = new Game(getGameExternalId(obj), getGameDate(obj), getGameState(obj), getName(obj), getSportID(), getGameParticipants(obj));
                res.add(g);
            }
        }
        
        return res;
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
        String idLeague = "94";
        String url = "https://v3.football.api-sports.io/odds?season=2022&bet=1&fixture=" + getGameExternalId(game) + "&league=" + idLeague;

        String fixtureResponse = super.readJSON(url, "jsons/football/" + idLeague + "_" + getGameExternalId(game), "b68a93e4291b512a0f3179eb9ee1bc2b");
        JSONArray oddsFixture = (JSONArray) (new JSONObject(fixtureResponse).get("response"));
        List<Float> res = new ArrayList<>();

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
            float tmp = 1.1f;
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
        List<Float> odds = getOdds(game);
        Participant homeP = new Participant(homeTeam, odds.get(0), 0);
        Participant drawP = new Participant("draw", odds.get(1), 0);
        Participant awayP = new Participant(awayTeam, odds.get(2), 0);

        ps.add(homeP); ps.add(drawP); ps.add(awayP);

        return ps;
    }

    public String getSportID() {
        return this.sport_id;
    }

    public int getGameState(JSONObject game) {
        JSONObject fixture = (JSONObject) game.get("fixture");
        JSONObject status = (JSONObject) fixture.get("status");
        String status_short = (String) status.get("short");

        if (status_short.equals("NS") || status_short.equals("1H") || status_short.equals("HT")
                    || status_short.equals("2H") || status_short.equals("ET") || status_short.equals("P") || status_short.equals("TBD"))
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
}
