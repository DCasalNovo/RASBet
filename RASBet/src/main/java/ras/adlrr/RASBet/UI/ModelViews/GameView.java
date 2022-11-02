package ras.adlrr.RASBet.UI.ModelViews;

import ras.adlrr.RASBet.model.Game;
import ras.adlrr.RASBet.model.Participant;

import java.util.Set;

public class GameView {
    private Game game;

    public GameView(Game game) {
        this.game = game;
    }

    public String toString(){
        String s =  game.getTitle() + "\n";
        s += "Data :" + game.getDate() + "\n";
        s += "Desporto :" + game.getSport().getName() + "\n";
        switch (game.getState()){
            case 1 -> {s+="Estado :Suspenso";}
            case 2 -> {s+="Estado :Fechado";}
            case 3 -> {s+="Estado :Aberto";}
        }
        return s;
    }

    public String toStringExtended(Set<Participant> participants){
        String s = this.toString();

        for (ParticipantView participant: participants.stream().map(ParticipantView::new).toList()){
            s+= participant.toString() + "\n";
        }

        return s;
    }
}
